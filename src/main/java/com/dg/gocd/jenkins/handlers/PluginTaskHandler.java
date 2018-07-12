package com.dg.gocd.jenkins.handlers;

import com.dg.gocd.jenkins.Config;
import com.dg.gocd.jenkins.ResponseUtils;
import com.dg.gocd.jenkins.Result;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dima.golomozy
 */
public class PluginTaskHandler {
    private static final Logger logger = Logger.getLoggerFor(PluginTaskHandler.class);

    public PluginTaskHandler() {
    }

    public GoPluginApiResponse handleTaskView() {
        Map<String, Object> view = new HashMap<>();
        view.put("displayValue", "Curl");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8"));
            return ResponseUtils.success(view);
        } catch (Exception e) {
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            logger.error(errorMessage, e);
            return ResponseUtils.error(view);
        }
    }

    public GoPluginApiResponse handleTaskExecution(GoPluginApiRequest requestMessage) {
        return ResponseUtils.success(new Result(true, "Hello").toMap());
    }

    public GoPluginApiResponse handleValidation(GoPluginApiRequest requestMessage) {
        Map<String, Object> validationResult = new HashMap<>();
        Map configMap = (Map) new GsonBuilder().create().fromJson(requestMessage.requestBody(), Object.class);

        Map<String, String> errorMap = new HashMap<>();
        if (!configMap.containsKey(Config.URL_PROPERTY) || ((Map) configMap.get(Config.URL_PROPERTY)).get("value") == null || ((String) ((Map) configMap.get(Config.URL_PROPERTY)).get("value")).trim().isEmpty()) {
            errorMap.put(Config.URL_PROPERTY, "URL cannot be empty");
        }
        validationResult.put("errors", errorMap);
        return ResponseUtils.success(validationResult);
    }

    public GoPluginApiResponse handleGetConfigRequest() {
        final Map<String, Object> config = new HashMap<>();

        Map<String, Object> url = new HashMap<>();
        url.put("display-order", "0");
        url.put("display-name", "Url");
        url.put("required", true);
        config.put(Config.URL_PROPERTY, url);

        Map<String, Object> secure = new HashMap<>();
        secure.put("display-order", "1");
        secure.put("display-name", "token");
        secure.put("required", true);
        secure.put("secure", true);
        config.put(Config.TOKEN_PROPERTY, secure);

        return ResponseUtils.success(config);
    }
}
