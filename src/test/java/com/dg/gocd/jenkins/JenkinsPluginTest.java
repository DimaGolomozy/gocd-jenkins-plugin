package com.dg.gocd.jenkins;

import com.dg.gocd.jenkins.factories.TaskExecutorFactory;
import com.dg.gocd.jenkins.task.TaskExecutor;
import com.dg.gocd.jenkins.task.TaskResult;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.dg.gocd.RequestName.*;
import static com.dg.gocd.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dima.golomozy
 */
public class JenkinsPluginTest {

    private final TaskExecutor taskExecutor = mock(TaskExecutor.class);
    private final TaskExecutorFactory taskExecutorFactory = mock(TaskExecutorFactory.class);
    private final JenkinsPlugin onTest = new JenkinsPlugin(taskExecutorFactory);

    @Before
    public void setUp() throws Exception {
        when(taskExecutorFactory.getTaskExecutor()).thenReturn(taskExecutor);
    }

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
        setExecutorResult(true);

        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_EXECUTE, getResource("task-execute-request.json")));
        assertSuccessResponse(actual);
    }

    @Test
    @Ignore
    // TODO: 26/07/18 dima.golomozy - enable this
    public void testTaskExecuteRequestNoMandatoryFields() throws Exception {
        setExecutorResult(true);

        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_EXECUTE, getResource("task-execute-request-no-mandatory-fields.json")));
        assertSuccessResponse(actual);
    }

    @Test
    public void testTaskExecuteRequestError() throws Exception {
        setExecutorResult(false);

        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_EXECUTE, getResource("task-execute-request.json")));
        assertErrorResponse(actual);
    }

    @Test
    public void testTaskViewRequest() throws Exception {
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_VIEW));
        assertSuccessResponse(actual);
    }

    @Test(expected = UnhandledRequestTypeException.class)
    public void testUnknownRequest() throws Exception {
        onTest.handle(emptyRequest());
    }

    private void setExecutorResult(boolean success) {
        when(taskExecutor.execute(any(), any())).thenReturn(new TaskResult(success, ""));

    }
}