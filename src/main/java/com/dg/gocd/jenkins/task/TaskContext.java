package com.dg.gocd.jenkins.task;

import com.dg.gocd.utiils.Utils;

import java.util.Map;

/**
 * @author dima.golomozy
 */
public class TaskContext {
    private final Map environmentVariables;
    private final String workingDir;

    public TaskContext(Map context) {
        this(
            Utils.getValueOrDefault(context, "environmentVariables"),
            Utils.getValueOrDefault(context, "workingDirectory")
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
