package com.dg.gocd.jenkins.task;

import com.dg.gocd.utiils.JSONUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static com.dg.gocd.RequestName.TASK_EXECUTE;
import static com.dg.gocd.TestUtils.*;
import static org.junit.Assert.assertEquals;

/**
 * @author dima.golomozy
 */
public class TaskHandlerTest {
    private static final Gson gson = new GsonBuilder().create();

    private final TaskHandler onTest = new TaskHandler();

    @Test
    public void handleTaskView() throws Exception {
        GoPluginApiResponse actual = onTest.handleTaskView();

        assertSuccessResponse(actual);

        Map actualBody = JSONUtils.fromJSON(actual.responseBody(), Map.class);
        assertEquals(IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8"), actualBody.get("template"));
    }

    @Test
    @Ignore
    public void handleTaskExecution() throws Exception  {
        GoPluginApiResponse actual = onTest.handleTaskExecution(newRequest(TASK_EXECUTE));
        assertSuccessResponse(actual);
    }

    @Test
    public void handleValidation() throws Exception {
        GoPluginApiResponse actual = onTest.handleValidation(emptyRequest());
        assertSuccessResponse(actual);
    }

    @Test
    public void handleGetConfigRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handleGetConfigRequest();
        assertSuccessResponse(actual);
    }
}