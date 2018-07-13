package com.dg.gocd.jenkins.handlers;

import com.dg.gocd.jenkins.PluginSettings;
import com.thoughtworks.go.plugin.api.logging.Logger;

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
}
