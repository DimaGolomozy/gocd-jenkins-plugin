package com.dg.gocd.jenkins.task;

import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;

import static com.dg.gocd.utiils.GoPluginApiUtils.successResponse;

/**
 * @author dima.golomozy
 */
public class TaskExecutor {

    private final JobConsoleLogger console = JobConsoleLogger.getConsoleLogger();
    private final TaskConfig taskConfig;
    private final TaskContext taskContext;

    public TaskExecutor(TaskConfig taskConfig, TaskContext taskContext) {
        this.taskConfig = taskConfig;
        this.taskContext = taskContext;
    }

    public GoPluginApiResponse execute() throws Exception {
        console.printLine("Executing request to url [" + taskConfig.getUrl() + "] with token ["  + taskConfig.getToken() + "]");
        console.printLine("Properties: username [" + taskConfig.getUsername() + "], password [" + taskConfig.getPassword() + "], paramss " + taskConfig.getParams().toString());


        return successResponse(new TaskResult(true, "Hello").toMap());
    }
}
