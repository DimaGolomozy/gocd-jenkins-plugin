package com.dg.gocd.jenkins.task;

import com.dg.gocd.jenkins.settings.PluginSettings;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Test;

import static com.dg.gocd.TestUtils.assertSuccessResponse;
import static com.dg.gocd.TestUtils.emptyRequest;
import static org.mockito.Mockito.mock;

/**
 * @author dima.golomozy
 */
public class TaskHandlerTest {

    private final PluginSettings pluginSettings = mock(PluginSettings.class);
    private final TaskHandler onTest = new TaskHandler(pluginSettings);

    @Test
    public void handleTaskView() {
        GoPluginApiResponse actual = onTest.handleTaskView();
        assertSuccessResponse(actual);
    }

    @Test
    public void handleTaskExecution() {
        GoPluginApiResponse actual = onTest.handleTaskExecution(emptyRequest());
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