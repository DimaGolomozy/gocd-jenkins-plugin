package com.dg.gocd.jenkins;

import com.dg.gocd.jenkins.task.TaskHandler;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Ignore;
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
    private final TaskHandler taskHandler = mock(TaskHandler.class, invocationOnMock -> EXPECTED_RESULT);

    private final JenkinsPlugin onTest = new JenkinsPlugin(taskHandler);

    @Test
    @Ignore
    public void testTaskConfigurationRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_CONFIGURATION));

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleGetConfigRequest();
    }

    @Test
    @Ignore
    public void testTaskValidateRequest() throws Exception {
        GoPluginApiRequest request = newRequest(TASK_VALIDATE);
        GoPluginApiResponse actual = onTest.handle(request);

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleValidation(request);
    }

    @Test
    @Ignore
    public void testTaskExecuteRequest() throws Exception {
        GoPluginApiRequest request = newRequest(TASK_EXECUTE);
        GoPluginApiResponse actual = onTest.handle(request);

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleTaskExecution(request);
    }

    @Test
    @Ignore
    public void testTaskViewRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_VIEW));

        assertSame(EXPECTED_RESULT, actual);
        verify(taskHandler).handleTaskView();
    }

    @Test(expected = UnhandledRequestTypeException.class)
    @Ignore
    public void testUnknownRequest() throws Exception {
        onTest.handle(newRequest("bla"));
    }
}