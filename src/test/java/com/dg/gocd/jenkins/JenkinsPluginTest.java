package com.dg.gocd.jenkins;

import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.junit.Test;

import static com.dg.gocd.RequestName.*;
import static com.dg.gocd.TestUtils.assertSuccessResponse;
import static com.dg.gocd.TestUtils.newRequest;

/**
 * @author dima.golomozy
 */
public class JenkinsPluginTest {

    private final JenkinsPlugin onTest = new JenkinsPlugin();

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
        GoPluginApiResponse actual = onTest.handle(newRequest(TASK_EXECUTE));
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