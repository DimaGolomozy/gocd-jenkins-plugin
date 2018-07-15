package com.dg.gocd.jenkins;

import com.dg.gocd.jenkins.settings.PluginSettings;
import com.dg.gocd.jenkins.settings.SettingsHandler;
import com.dg.gocd.jenkins.task.TaskHandler;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Test;

import static com.dg.gocd.RequestName.*;
import static com.dg.gocd.TestUtils.newRequest;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author dima.golomozy
 */
public class JenkinsPluginTest {

    private final GoPluginApiResponse EXPECTED_RESULT = new DefaultGoPluginApiResponse(1, "");
    private final PluginSettings pluginSettings = mock(PluginSettings.class);
    private final TaskHandler taskHandler = mock(TaskHandler.class, invocationOnMock -> EXPECTED_RESULT);
    private final SettingsHandler settingsHandler = mock(SettingsHandler.class, invocationOnMock -> EXPECTED_RESULT);

    private final JenkinsPlugin onTest = new JenkinsPlugin(pluginSettings, taskHandler, settingsHandler);

    @Test
    public void testTaskConfigurationRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_CONFIGURATION));

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleGetConfigRequest();
    }

    @Test
    public void testTaskValidateRequest() throws Exception {
        GoPluginApiRequest request = newRequest(TASK_VALIDATE);
        GoPluginApiResponse actual = onTest.handle(request);

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleValidation(request);
    }

    @Test
    public void testTaskExecuteRequest() throws Exception {
        GoPluginApiRequest request = newRequest(TASK_EXECUTE);
        GoPluginApiResponse actual = onTest.handle(request);

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleTaskExecution(request);
    }

    @Test
    public void testTaskViewRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_VIEW));

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleTaskView();
    }

    @Test
    public void testPluginSettingsGetViewRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(PLUGIN_SETTINGS_GET_VIEW));

        assertSame(EXPECTED_RESULT, actual);
        verify(settingsHandler).handleViewRequest();
    }

    @Test
    public void testPluginSettingsValidateRequest() throws Exception {
        GoPluginApiRequest request = newRequest(PLUGIN_SETTINGS_VALIDATE_CONFIGURATION);
        GoPluginApiResponse actual = onTest.handle(request);

        assertSame(EXPECTED_RESULT, actual);
        verify(settingsHandler).handleValidationRequest(request);
    }

    @Test
    public void testPluginSettingsGetConfigurationRequest() throws Exception {
        GoPluginApiRequest request = newRequest(PLUGIN_SETTINGS_GET_CONFIGURATION);
        GoPluginApiResponse actual = onTest.handle(request);

        assertSame(EXPECTED_RESULT, actual);
        verify(settingsHandler).handleGetConfigurationRequest(request);
    }

    @Test(expected = UnhandledRequestTypeException.class)
    public void testUnknownRequest() throws Exception {
        onTest.handle(newRequest("bla"));
    }
}