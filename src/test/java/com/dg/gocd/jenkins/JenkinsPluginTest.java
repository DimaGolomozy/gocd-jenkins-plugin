package com.dg.gocd.jenkins;

import com.dg.gocd.TestUtils;
import com.dg.gocd.jenkins.factories.TaskExecutorFactory;
import com.dg.gocd.jenkins.task.TaskConfig;
import com.dg.gocd.jenkins.task.TaskExecutor;
import com.dg.gocd.jenkins.task.TaskResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.RequestName.*;
import static com.dg.gocd.TestUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dima.golomozy
 */
public class JenkinsPluginTest {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

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
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_VALIDATE, TestUtils.getResource("validate-request.json")));
        assertSuccessResponse(actual);

        Map errors = (Map) gson.fromJson(actual.responseBody(), Map.class).get("errors");
        assertTrue(errors.isEmpty());
    }

    @Test
    public void testTaskExecuteRequest() throws Exception {
        setExecutorResult(true);

        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_EXECUTE, getResource("task-execute-request.json")));
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

    @Test
    public void testTaskConfigCreation() throws Exception {
        Map config = (Map) gson.fromJson(getResource("task-execute-request.json"), Map.class).get("config");
        Map<String, String> env = new HashMap<>();
        env.put("FIRST_ENV", "first");
        env.put("SECOND_ENV", "second");

        TaskConfig taskConfig = onTest.createTaskConfig(config, env);

        assertEquals("http://www.HelloWorld.com", taskConfig.getUrl());
        assertEquals("job1", taskConfig.getJob());
        assertEquals("user1", taskConfig.getUsername());
        assertEquals("password1", taskConfig.getPassword());
        assertEquals("first", taskConfig.getParams().get("bla"));
        assertEquals("second", taskConfig.getParams().get("foo"));
        assertEquals("123", taskConfig.getParams().get("boo"));
        assertEquals("456", taskConfig.getParams().get("fofo"));
    }

    @Test
    public void testTaskConfigCreationNoRequiredFields() throws Exception {
        Map config = (Map) gson.fromJson(getResource("task-execute-request-no-required.json"), Map.class).get("config");
        Map<String, String> env = new HashMap<>();
        env.put("FIRST_ENV", "first");
        env.put("SECOND_ENV", "second");

        TaskConfig taskConfig = onTest.createTaskConfig(config, env);

        assertEquals("http://www.HelloWorld.com", taskConfig.getUrl());
        assertEquals("job1", taskConfig.getJob());
        assertEquals("user1", taskConfig.getUsername());
        assertEquals("password1", taskConfig.getPassword());
        assertTrue(taskConfig.getParams().isEmpty());
    }

    private void setExecutorResult(boolean success) {
        when(taskExecutor.execute(any(), any())).thenReturn(new TaskResult(success, ""));

    }
}