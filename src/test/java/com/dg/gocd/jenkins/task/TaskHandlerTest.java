package com.dg.gocd.jenkins.task;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Ignore;
import org.junit.Test;

import static com.dg.gocd.RequestName.TASK_EXECUTE;
import static com.dg.gocd.TestUtils.*;

/**
 * @author dima.golomozy
 */
public class TaskHandlerTest {
    private static final Gson gson = new GsonBuilder().create();

    private final TaskHandler onTest = new TaskHandler();

    @Test
    public void handleTaskView() {
        GoPluginApiResponse actual = onTest.handleTaskView();
        assertSuccessResponse(actual);
    }

    @Test
    @Ignore
    public void handleTaskExecution() {
        GoPluginApiResponse actual = onTest.handleTaskExecution(newRequest(TASK_EXECUTE));
        assertSuccessResponse(actual);
    }

    @Test
    public void handleValidation() {
        GoPluginApiResponse actual = onTest.handleValidation(emptyRequest());
        assertSuccessResponse(actual);
    }

    @Test
    public void handleGetConfigRequest() {
        GoPluginApiResponse actual = onTest.handleGetConfigRequest();
        assertSuccessResponse(actual);
    }
}