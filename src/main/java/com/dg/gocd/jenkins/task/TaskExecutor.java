package com.dg.gocd.jenkins.task;

import com.dg.gocd.jenkins.factories.JenkinsServerFactory;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;

import java.net.URI;

import static com.offbytwo.jenkins.model.BuildResult.SUCCESS;

/**
 * @author dima.golomozy
 */
public class TaskExecutor {

    private final JenkinsServerFactory jenkinsServerFactory;
    private final JobConsoleLogger console;

    public TaskExecutor() {
        this(JenkinsServerFactory.getFactory(), JobConsoleLogger.getConsoleLogger());
    }

    TaskExecutor(JenkinsServerFactory jenkinsServerFactory, JobConsoleLogger console) {
        this.jenkinsServerFactory = jenkinsServerFactory;
        this.console = console;
    }

    public TaskResult execute(TaskConfig taskConfig, TaskContext taskContext) {
        console.printLine("Executing request to url [" + taskConfig.getUrl() + "] job ["  + taskConfig.getJob() + "]");
        console.printEnvironment(taskConfig.getParams());

        try (JenkinsServer jenkinsServer = jenkinsServerFactory.getJenkinsServer(new URI(taskConfig.getUrl()), taskConfig.getUsername(), taskConfig.getPassword())) {
            return exec(jenkinsServer, taskConfig, taskContext);
        } catch (Exception e) {
            return new TaskResult(false, "Failed executing task", e);
        }
    }

    private TaskResult exec(JenkinsServer jenkinsServer, TaskConfig taskConfig, TaskContext taskContext) throws Exception {
        JobWithDetails job = jenkinsServer.getJob(taskConfig.getJob());
        console.printLine("Building job...");
        QueueReference queueReference = taskConfig.getParams().isEmpty() ? job.build() : job.build(taskConfig.getParams());

        QueueItem queueItem = jenkinsServer.getQueueItem(queueReference);
        while (queueItem.getExecutable() == null) {
            console.printLine("Job still in queue" + queueItem.getExecutable());
            Thread.sleep(2000);

            queueItem = jenkinsServer.getQueueItem(queueReference);
        }

        Build build = jenkinsServer.getBuild(queueItem);
        console.printLine("Job is in progress with id [" + build.getNumber() + "] url: " + build.getUrl());
        while (build.details().isBuilding()) {
            console.printLine("Job still running");
            Thread.sleep(10000);
        }

        console.printLine("Job done. Result = " + build.details().getResult());
        console.printLine("Jenkins console output:\n" + build.details().getConsoleOutputText());
        return new TaskResult(
                SUCCESS.equals(build.details().getResult()),
                "Jenkins Console log:\n" + build.details().getConsoleOutputText()
        );
    }
}
