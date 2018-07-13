package com.dg.gocd.jenkins;

import java.util.Collections;
import java.util.Map;

/**
 * @author dima.golomozy
 */
public final class PluginSettings {
    private static PluginSettings instance = new PluginSettings();
    public static PluginSettings getInstance() {
        return instance;
    }

    private Map<String, String> settings = Collections.emptyMap();

    private PluginSettings() {
    }

    public void setSettings(Map<String, String> newSettings) {
        this.settings = Collections.unmodifiableMap(newSettings);
    }

    public Map<String, String> getSettings() {
        return settings;
    }
}
