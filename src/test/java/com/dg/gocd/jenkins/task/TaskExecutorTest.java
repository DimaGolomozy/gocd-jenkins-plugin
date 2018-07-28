package com.dg.gocd.jenkins.task;

import com.dg.gocd.jenkins.factories.JenkinsServerFactory;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import static com.offbytwo.jenkins.model.BuildResult.SUCCESS;
import static java.util.Collections.emptyMap;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dima.golomozy
 */
public class TaskExecutorTest {

    private static final String JENKINS_URL = "http://www.realsite.com";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";
    private static final String JOB_NAME = "jobjobjob";
    private static final TaskContext TASK_CONTEXT = new TaskContext(emptyMap(), "");
    private static final TaskConfig TASK_CONFIG = new TaskConfig(JENKINS_URL, JOB_NAME, USERNAME, PASSWORD, Collections.<String, String>emptyMap());

    private final JenkinsServer jenkinsServer = mock(JenkinsServer.class);
    private final JobConsoleLogger console = mock(JobConsoleLogger.class);
    private final JenkinsServerFactory jenkinsServerFactory = mock(JenkinsServerFactory.class);

    private final TaskExecutor onTest = new TaskExecutor(jenkinsServerFactory, console);

    @Before
    public void setUp() throws Exception {
        when(jenkinsServerFactory.getJenkinsServer(eq(new URI(JENKINS_URL)), eq(USERNAME), eq(PASSWORD))).thenReturn(jenkinsServer);
    }

    @Test
    public void executeWithBadURL() throws Exception {
        TaskConfig taskConfig = new TaskConfig("oh no", "", "", "", Collections.<String, String>emptyMap());

        TaskResult actualResult = onTest.execute(taskConfig, TASK_CONTEXT);
        assertFailedResult(actualResult);
    }

    @Test
    public void executeExceptionInJenkinsServer() throws Exception {
        when(jenkinsServer.getJob(eq(JOB_NAME))).thenThrow(IOException.class);

        TaskResult actualResult = onTest.execute(TASK_CONFIG, TASK_CONTEXT);
        assertFailedResult(actualResult);
    }

    @Test
    public void executeSuccess() throws Exception {
        BuildWithDetails buildWithDetails = mock(BuildWithDetails.class);
        when(buildWithDetails.isBuilding()).thenReturn(false);
        when(buildWithDetails.getResult()).thenReturn(SUCCESS);

        Build build = mock(Build.class);
        when(build.details()).thenReturn(buildWithDetails);

        Executable executable = mock(Executable.class);
        QueueItem queueItem = mock(QueueItem.class);
        when(queueItem.getExecutable()).thenReturn(executable);
        when(jenkinsServer.getBuild(queueItem)).thenReturn(build);

        QueueReference queueReference = mock(QueueReference.class);
        when(jenkinsServer.getQueueItem(eq(queueReference))).thenReturn(queueItem);

        JobWithDetails jobWithDetails = mock(JobWithDetails.class);
        when(jobWithDetails.build()).thenReturn(queueReference);

        when(jenkinsServer.getJob(eq(JOB_NAME))).thenReturn(jobWithDetails);

        TaskResult actualResult = onTest.execute(TASK_CONFIG, TASK_CONTEXT);
        assertSuccessResult(actualResult);
    }

    private void assertFailedResult(TaskResult actualResult) {
        assertFalse(actualResult.isSuccess());
    }

    private void assertSuccessResult(TaskResult actualResult) {
        assertTrue(actualResult.isSuccess());
    }

}