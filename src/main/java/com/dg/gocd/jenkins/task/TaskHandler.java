package com.dg.gocd.jenkins.task;

import com.dg.gocd.jenkins.settings.PluginSettings;
import com.dg.gocd.jenkins.Result;
import com.dg.gocd.utiils.Utils;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.utiils.ResponseUtils.errorResponse;
import static com.dg.gocd.utiils.ResponseUtils.successResponse;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * @author dima.golomozy
 */
public class TaskHandler {
    private static final Logger logger = Logger.getLoggerFor(TaskHandler.class);
    private static final String URL_PROPERTY = "url";
    private static final String TOKEN_PROPERTY = "token";

    private final PluginSettings pluginSettings;

    public TaskHandler() {
        this(PluginSettings.getInstance());
    }

    TaskHandler(PluginSettings pluginSettings) {
        this.pluginSettings = pluginSettings;
    }

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
        // TODO: 14/07/18 dima.golomozy - Handle request
        return successResponse(new Result(true, "Hello").toMap());
    }

    public GoPluginApiResponse handleValidation(GoPluginApiRequest requestMessage) {
        return successResponse(singletonMap("errors", emptyMap()));
    }

    public GoPluginApiResponse handleGetConfigRequest() {
        final Map<String, Object> configMap = new HashMap<>();
        configMap.put(URL_PROPERTY, Utils.createField(URL_PROPERTY, true, false, "0"));
        configMap.put(TOKEN_PROPERTY, Utils.createField(TOKEN_PROPERTY, true, true, "1"));
        return successResponse(configMap);
    }
}
