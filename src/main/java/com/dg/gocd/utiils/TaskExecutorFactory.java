package com.dg.gocd.utiils;

import com.dg.gocd.jenkins.task.TaskExecutor;

/**
 * @author dima.golomozy
 */
public class TaskExecutorFactory {
    private static TaskExecutorFactory instance = new TaskExecutorFactory();
    public static TaskExecutorFactory getFactory() {
        return instance;
    }

    private TaskExecutorFactory() {
    }

    public TaskExecutor getTaskExecutor() {
        return new TaskExecutor();
    }
}
