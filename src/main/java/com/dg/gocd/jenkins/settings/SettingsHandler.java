package com.dg.gocd.jenkins.settings;

import com.dg.gocd.utiils.ResponseUtils;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.jenkins.settings.PluginSettings.PASSWORD;
import static com.dg.gocd.jenkins.settings.PluginSettings.USERNAME;
import static com.dg.gocd.utiils.Utils.createField;
import static java.util.Collections.emptyMap;

/**
 * @author dima.golomozy
 */
public class SettingsHandler {
    private static final Logger logger = Logger.getLoggerFor(SettingsHandler.class);

    private final PluginSettings pluginSettings;

    public SettingsHandler() {
        this(PluginSettings.getInstance());
    }

    SettingsHandler(PluginSettings pluginSettings) {
        this.pluginSettings = pluginSettings;
    }

    public GoPluginApiResponse handleViewRequest() {
        Map<String, Object> view = new HashMap<>();
        view.put("displayValue", "Jenkins");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/pluginSettings.template.html"), "UTF-8"));
            return ResponseUtils.successResponse(view);
        } catch (Exception e) {
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            logger.error(errorMessage, e);
            return ResponseUtils.errorResponse(view);
        }
    }

    public GoPluginApiResponse handleValidationRequest(GoPluginApiRequest requestMessage) {
        return ResponseUtils.successResponse(emptyMap());
    }

    public GoPluginApiResponse handleGetConfigurationRequest(GoPluginApiRequest requestMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put(USERNAME, createField("Hello", false, false, "0"));
        response.put(PASSWORD, createField("To YOU", false, false, "1"));
        return ResponseUtils.successResponse(response);
    }
}
