package com.dg.gocd.jenkins;

import com.dg.gocd.RequestName;
import com.dg.gocd.jenkins.factories.TaskExecutorFactory;
import com.dg.gocd.jenkins.task.TaskConfig;
import com.dg.gocd.jenkins.task.TaskContext;
import com.dg.gocd.jenkins.task.TaskResult;
import com.thoughtworks.go.plugin.api.AbstractGoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.dg.gocd.jenkins.task.TaskConfig.*;
import static com.dg.gocd.utiils.GoPluginApiUtils.*;
import static com.dg.gocd.utiils.JSONUtils.fromJSON;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * @author dima.golomozy
 */
@Extension
public class JenkinsPlugin extends AbstractGoPlugin {
    private static final Logger logger = Logger.getLoggerFor(JenkinsPlugin.class);
    private static final Pattern PARAMS_PATTERN = Pattern.compile("\\w+=(\\$(\\{\\w+}|\\w+)|\\w+)([,(\\r?\\n)]\\w+=(\\$(\\{\\w+}|\\w+)|\\w+))*");

    private final TaskExecutorFactory taskExecutorFactory;

    public JenkinsPlugin() {
        this(TaskExecutorFactory.getFactory());
    }

    JenkinsPlugin(TaskExecutorFactory taskExecutorFactory) {
        this.taskExecutorFactory = taskExecutorFactory;
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest requestMessage) throws UnhandledRequestTypeException {
        final String requestName = requestMessage.requestName();
        logger.info("Got request [{}], body: {}", requestName, requestMessage.requestBody());
        if (RequestName.TASK_CONFIGURATION.equals(requestName)) {
            return handleGetConfigRequest();
        } else if (RequestName.TASK_VALIDATE.equals(requestName)) {
            return handleValidation(requestMessage);
        } else if (RequestName.TASK_EXECUTE.equals(requestName)) {
            return handleTaskExecution(requestMessage);
        } else if (RequestName.TASK_VIEW.equals(requestName)) {
            return handleTaskView();
        }

        throw new UnhandledRequestTypeException(requestName);
    }

    private GoPluginApiResponse handleGetConfigRequest() {
        final Map<String, Object> configMap = new HashMap<>();
        configMap.put(URL_PROPERTY, createField(URL_PROPERTY, true, false, "0"));
        configMap.put(JOB_PROPERTY, createField(JOB_PROPERTY, true, false, "1"));
        configMap.put(USERNAME_PROPERTY, createField(USERNAME_PROPERTY, true, false, "2"));
        configMap.put(PASSWORD_PROPERTY, createField(PASSWORD_PROPERTY, false, true, "3"));
        configMap.put(PARAMS_PROPERTY, createField(PARAMS_PROPERTY, false, false, "4"));
        return successResponse(configMap);
    }

    private GoPluginApiResponse handleValidation(GoPluginApiRequest requestMessage) {
        Map<String, String> errors = new HashMap<>();
        Map request = fromJSON(requestMessage.requestBody(), Map.class);

        String paramsValue = getValueOrEmpty(request, PARAMS_PROPERTY);
        if (!paramsValue.isEmpty() && !PARAMS_PATTERN.matcher(paramsValue).matches()) {
            errors.put(PARAMS_PROPERTY, "Params syntax is <PARAM>=<VALUE>, with COMMA or NEWLINE delimiter");
        }

        return successResponse(singletonMap("errors", errors));
    }

    private GoPluginApiResponse handleTaskExecution(GoPluginApiRequest requestMessage) {
        try {
            Map request = fromJSON(requestMessage.requestBody(), Map.class);
            TaskContext taskContext = createTaskContext((Map) request.get("context"));
            TaskConfig taskConfig = createTaskConfig((Map) request.get("config"), taskContext.getEnvironmentVariables());

            TaskResult taskResult = taskExecutorFactory.getTaskExecutor().execute(taskConfig, taskContext);
            return successResponse(taskResult.toMap());
        } catch (Exception e) {
            String errorMessage = "Failed task execution: " + e.getMessage();
            logger.error(errorMessage, e);
            return errorResponse(singletonMap("exception", errorMessage));
        }
    }

    private TaskContext createTaskContext(Map context) {
        return new TaskContext(
            (Map) context.get("environmentVariables"),
            (String) context.get("workingDirectory")
        );
    }

    TaskConfig createTaskConfig(Map config, Map<String, String> environmentVariables) {
        String params = getValueOrEmpty(config, PARAMS_PROPERTY);
        return new TaskConfig(
            replaceWithEnv(getValueOrEmpty(config, URL_PROPERTY), environmentVariables),
            replaceWithEnv(getValueOrEmpty(config, JOB_PROPERTY), environmentVariables),
            replaceWithEnv(getValueOrEmpty(config, USERNAME_PROPERTY), environmentVariables),
            replaceWithEnv(getValueOrEmpty(config, PASSWORD_PROPERTY), environmentVariables),
            params.isEmpty() ? emptyMap() : Arrays.stream(replaceWithEnv(params, environmentVariables).split(",|\\r?\\n"))
                .map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()))
        );
    }

    private GoPluginApiResponse handleTaskView() {
        Map<String, Object> view = new HashMap<>();
        view.put("displayValue", "Jenkins");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8"));
            return successResponse(view);
        } catch (Exception e) {
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            logger.error(errorMessage, e);
            return errorResponse(view);
        }
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return new GoPluginIdentifier("task", Collections.singletonList("1.0"));
    }
}
