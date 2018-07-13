package com.dg.gocd.jenkins.handlers;

import com.dg.gocd.jenkins.Config;
import com.dg.gocd.jenkins.Result;
import com.dg.gocd.jenkins.Utils;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.jenkins.Utils.toMap;
import static java.util.Collections.singletonMap;

/**
 * @author dima.golomozy
 */
public class PluginTaskHandler {
    private static final Logger logger = Logger.getLoggerFor(PluginTaskHandler.class);

    public GoPluginApiResponse handleTaskView() {
        Map<String, Object> view = new HashMap<>();
        view.put("displayValue", "Jenkins");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8"));
            return Utils.successResponse(view);
        } catch (Exception e) {
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            logger.error(errorMessage, e);
            return Utils.errorResponse(view);
        }
    }

    public GoPluginApiResponse handleTaskExecution(GoPluginApiRequest requestMessage) {
        return Utils.successResponse(new Result(true, "Hello").toMap());
    }

    public GoPluginApiResponse handleValidation(GoPluginApiRequest requestMessage) {
        Map<String, String> errors = Config.validateConfig(toMap(requestMessage.requestBody()));
        return Utils.successResponse(singletonMap("errors", errors));
    }

    public GoPluginApiResponse handleGetConfigRequest() {
        return Utils.successResponse(Config.toMap());
    }
}
