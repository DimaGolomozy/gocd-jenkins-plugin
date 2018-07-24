package com.dg.gocd.jenkins.task;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;

import java.net.URI;

import static com.dg.gocd.utiils.GoPluginApiUtils.successResponse;

/**
 * @author dima.golomozy
 */
public class TaskExecutor {

    private final JobConsoleLogger console;
    private final TaskConfig taskConfig;
    private final TaskContext taskContext;

    public TaskExecutor(TaskConfig taskConfig, TaskContext taskContext) {
        this(taskConfig, taskContext, JobConsoleLogger.getConsoleLogger());
    }

    TaskExecutor(TaskConfig taskConfig, TaskContext taskContext, JobConsoleLogger console) {
        this.taskConfig = taskConfig;
        this.taskContext = taskContext;
        this.console = console;
    }

    public GoPluginApiResponse execute() throws Exception {
        console.printLine("Executing request to url [" + taskConfig.getUrl() + "] with token ["  + taskConfig.getToken() + "]");
        console.printLine("username [" + taskConfig.getUsername() + "], password [" + taskConfig.getPassword() + "], params " + taskConfig.getParams().toString());
        return exec(new JenkinsServer(new URI(taskConfig.getUrl()), "", ""));
    }

    GoPluginApiResponse exec(JenkinsServer jenkinsServer) throws Exception {
        JobWithDetails job = jenkinsServer.getJob("Dima-Test");
        QueueReference queueReference = job.build();

        QueueItem queueItem = jenkinsServer.getQueueItem(queueReference);
        while (queueItem.getExecutable() == null) {
            console.printLine("Job still in queue" + queueItem.getExecutable());
            Thread.sleep(2000);

            queueItem = jenkinsServer.getQueueItem(queueReference);
        } while (queueItem.getExecutable() == null);

        Build build = jenkinsServer.getBuild(queueItem);

        while (build.details().isBuilding()) {
            console.printLine("Job still running");
            Thread.sleep(10000L);
        }

        console.printLine("Result = " + build.details().getResult());
        return successResponse(new TaskResult(true, "Hello").toMap());
    }
}
