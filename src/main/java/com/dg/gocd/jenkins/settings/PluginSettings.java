package com.dg.gocd.jenkins.settings;

import java.util.Collections;
import java.util.Map;

/**
 * @author dima.golomozy
 */
public class PluginSettings {
    private static PluginSettings instance = new PluginSettings();
    public static PluginSettings getInstance() {
        return instance;
    }

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private Map<String, String> settings = Collections.emptyMap();

    private PluginSettings() {
    }

    void setSettings(Map<String, String> newSettings) {
        this.settings = Collections.unmodifiableMap(newSettings);
    }

    public Map<String, String> getSettings() {
        return settings;
    }
}
