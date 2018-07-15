package com.dg.gocd.jenkins.task;

import com.dg.gocd.jenkins.settings.PluginSettings;
import com.thoughtworks.go.plugin.api.request.DefaultGoPluginApiRequest;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Test;

import static com.dg.gocd.jenkins.TestUtils.assertSuccessResponse;
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
        GoPluginApiRequest request = new DefaultGoPluginApiRequest("", "", "");

        GoPluginApiResponse actual = onTest.handleTaskExecution(request);
        assertSuccessResponse(actual);
    }

    @Test
    public void handleValidation() {
        GoPluginApiRequest request = new DefaultGoPluginApiRequest("", "", "");

        GoPluginApiResponse actual = onTest.handleValidation(request);
        assertSuccessResponse(actual);
    }

    @Test
    public void handleGetConfigRequest() {
        GoPluginApiResponse actual = onTest.handleGetConfigRequest();
        assertSuccessResponse(actual);
    }
}