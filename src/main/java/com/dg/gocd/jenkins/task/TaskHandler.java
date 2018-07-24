package com.dg.gocd.jenkins.task;

import com.dg.gocd.utiils.GoPluginApiUtils;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import org.apache.commons.io.IOUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.jenkins.task.TaskConfig.*;
import static com.dg.gocd.utiils.GoPluginApiUtils.errorResponse;
import static com.dg.gocd.utiils.GoPluginApiUtils.successResponse;
import static com.dg.gocd.utiils.JSONUtils.fromJSON;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * @author dima.golomozy
 */
public class TaskHandler {
    private static final Logger logger = Logger.getLoggerFor(TaskHandler.class);

    public GoPluginApiResponse handleTaskView() {
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

    public GoPluginApiResponse handleTaskExecution(GoPluginApiRequest requestMessage) {
        // TODO: 24/07/18 dima.golomozy - Remove this console
        JobConsoleLogger console = JobConsoleLogger.getConsoleLogger();
        console.printLine("Got request: " + requestMessage.requestBody());
        try {
            Map request = fromJSON(requestMessage.requestBody(), Map.class);
            TaskConfig taskConfig = new TaskConfig((Map) request.get("config"));
            TaskContext taskContext = new TaskContext((Map) request.get("context"));

            TaskExecutor taskExecutor = new TaskExecutor();
            taskExecutor.execute();
        } catch (Exception e) {
            String errorMessage = "Failed executing task: " + e.getMessage();
            logger.error(errorMessage, e);
            return errorResponse(Collections.singletonMap("exception", errorMessage));
        }

        return successResponse(new TaskResult(true, "Hello").toMap());
    }

    public GoPluginApiResponse handleValidation(GoPluginApiRequest requestMessage) {
        // TODO: 24/07/18 dima.golomozy - Check params in the right syntax
        return successResponse(singletonMap("errors", emptyMap()));
    }

    public GoPluginApiResponse handleGetConfigRequest() {
        final Map<String, Object> configMap = new HashMap<>();
        configMap.put(URL_PROPERTY, GoPluginApiUtils.createField(URL_PROPERTY, true, false, "0"));
        configMap.put(JOB_PROPERTY, GoPluginApiUtils.createField(JOB_PROPERTY, true, false, "1"));
        configMap.put(USERNAME_PROPERTY, GoPluginApiUtils.createField(USERNAME_PROPERTY, true, false, "2"));
        configMap.put(PASSWORD_PROPERTY, GoPluginApiUtils.createField(PASSWORD_PROPERTY, false, true, "3"));
        configMap.put(PARAMS_PROPERTY, GoPluginApiUtils.createField(PARAMS_PROPERTY, false, false, "4"));
        return successResponse(configMap);
    }
}
