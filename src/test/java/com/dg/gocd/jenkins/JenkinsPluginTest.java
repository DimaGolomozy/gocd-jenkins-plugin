package com.dg.gocd.jenkins;

import com.dg.gocd.jenkins.factories.TaskExecutorFactory;
import com.dg.gocd.jenkins.task.TaskExecutor;
import com.dg.gocd.jenkins.task.TaskResult;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import static com.dg.gocd.RequestName.*;
import static com.dg.gocd.TestUtils.assertSuccessResponse;
import static com.dg.gocd.TestUtils.newRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dima.golomozy
 */
public class JenkinsPluginTest {

    private final TaskExecutorFactory taskExecutorFactory = mock(TaskExecutorFactory.class);
    private final JenkinsPlugin onTest = new JenkinsPlugin(taskExecutorFactory);

    @Test
    public void testTaskConfigurationRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_CONFIGURATION));
        assertSuccessResponse(actual);
    }

    @Test
    public void testTaskValidateRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_VALIDATE));
        assertSuccessResponse(actual);
    }

    @Test
    public void testTaskExecuteRequest() throws Exception {
        TaskExecutor taskExecutor = mock(TaskExecutor.class);
        when(taskExecutor.execute(any(), any())).thenReturn(new TaskResult(true, ""));
        when(taskExecutorFactory.getTaskExecutor()).thenReturn(taskExecutor);

        String requestBody = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("task-execute-request.json"), "UTF-8");
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_EXECUTE, requestBody));
        assertSuccessResponse(actual);
    }

    @Test
    @Ignore
    public void testTaskExecuteRequestNoMandatoryFields() throws Exception {
        TaskExecutor taskExecutor = mock(TaskExecutor.class);
        when(taskExecutor.execute(any(), any())).thenReturn(new TaskResult(true, ""));
        when(taskExecutorFactory.getTaskExecutor()).thenReturn(taskExecutor);

        String requestBody = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("task-execute-request-no-mandatory-fields.json"), "UTF-8");
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_EXECUTE, requestBody));
        assertSuccessResponse(actual);
    }

    @Test
    public void testTaskViewRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_VIEW));
        assertSuccessResponse(actual);
    }

    @Test(expected = UnhandledRequestTypeException.class)
    public void testUnknownRequest() throws Exception {
        onTest.handle(newRequest("bla"));
    }
}