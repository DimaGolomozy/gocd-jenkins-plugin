package com.dg.gocd.jenkins.task;

import java.util.Map;

import static com.dg.gocd.utiils.GoPluginApiUtils.getValueOrDefault;

/**
 * @author dima.golomozy
 */
public class TaskContext {
    private final Map environmentVariables;
    private final String workingDir;

    public TaskContext(Map context) {
        this(
            getValueOrDefault(context, "environmentVariables"),
            getValueOrDefault(context, "workingDirectory")
        );
    }

    TaskContext(Map environmentVariables, String workingDir) {
        this.environmentVariables = environmentVariables;
        this.workingDir = workingDir;
    }

    public Map getEnvironmentVariables() {
        return environmentVariables;
    }

    public String getWorkingDir() {
        return workingDir;
    }
}
