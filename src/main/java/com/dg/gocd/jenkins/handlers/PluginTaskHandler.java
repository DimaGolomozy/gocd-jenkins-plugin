package com.dg.gocd.jenkins.handlers;

import com.dg.gocd.jenkins.Config;
import com.dg.gocd.jenkins.PluginSettings;
import com.dg.gocd.jenkins.Result;
import com.dg.gocd.utiils.ResponseUtils;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.utiils.JSONUtils.fromJSON;
import static java.util.Collections.singletonMap;

/**
 * @author dima.golomozy
 */
public class PluginTaskHandler {
    private static final Logger logger = Logger.getLoggerFor(PluginTaskHandler.class);

    private final PluginSettings settings;

    public PluginTaskHandler() {
        this(PluginSettings.getInstance());
    }

    PluginTaskHandler(PluginSettings settings) {
        this.settings = settings;
    }

    public GoPluginApiResponse handleTaskView() {
        Map<String, Object> view = new HashMap<>();
        view.put("displayValue", "Jenkins");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8"));
            return ResponseUtils.successResponse(view);
        } catch (Exception e) {
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            logger.error(errorMessage, e);
            return ResponseUtils.errorResponse(view);
        }
    }

    public GoPluginApiResponse handleTaskExecution(GoPluginApiRequest requestMessage) {
        return ResponseUtils.successResponse(new Result(true, "Hello").toMap());
    }

    public GoPluginApiResponse handleValidation(GoPluginApiRequest requestMessage) {
        Map<String, String> errors = Config.validateConfig(fromJSON(requestMessage.requestBody()));
        return ResponseUtils.successResponse(singletonMap("errors", errors));
    }

    public GoPluginApiResponse handleGetConfigRequest() {
        return ResponseUtils.successResponse(Config.toMap());
    }
}
