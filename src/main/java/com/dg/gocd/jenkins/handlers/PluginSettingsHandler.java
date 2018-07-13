package com.dg.gocd.jenkins.handlers;

import com.dg.gocd.jenkins.PluginSettings;
import com.dg.gocd.jenkins.Utils;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.jenkins.PluginSettings.PASSWORD;
import static com.dg.gocd.jenkins.PluginSettings.USERNAME;
import static com.dg.gocd.jenkins.Utils.createField;
import static java.util.Collections.emptyMap;

/**
 * @author dima.golomozy
 */
public class PluginSettingsHandler {
    private static final Logger logger = Logger.getLoggerFor(PluginSettingsHandler.class);


    private final PluginSettings settings;

    public PluginSettingsHandler() {
        this(PluginSettings.getInstance());
    }

    PluginSettingsHandler(PluginSettings settings) {
        this.settings = settings;
    }

    public GoPluginApiResponse handleViewRequest() {
        Map<String, Object> view = new HashMap<>();
        view.put("displayValue", "Jenkins");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/settings.template.html"), "UTF-8"));
            return Utils.successResponse(view);
        } catch (Exception e) {
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            logger.error(errorMessage, e);
            return Utils.errorResponse(view);
        }
    }

    public GoPluginApiResponse handleValidationRequest(GoPluginApiRequest requestMessage) {
        return Utils.successResponse(emptyMap());
    }

    public GoPluginApiResponse handleGetConfigurationRequest(GoPluginApiRequest requestMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put(USERNAME, createField("Hello", false, false, "0"));
        response.put(PASSWORD, createField("To YOU", false, false, "1"));
        return Utils.successResponse(response);
    }
}
