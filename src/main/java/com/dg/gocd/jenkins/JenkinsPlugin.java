package com.dg.gocd.jenkins;

import com.dg.gocd.RequestName;
import com.dg.gocd.jenkins.handlers.PluginSettingsHandler;
import com.dg.gocd.jenkins.handlers.PluginTaskHandler;
import com.thoughtworks.go.plugin.api.AbstractGoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.Collections;

/**
 * @author dima.golomozy
 */
@Extension
public class JenkinsPlugin extends AbstractGoPlugin {
    private static final Logger logger = Logger.getLoggerFor(JenkinsPlugin.class);
    private static final GoPluginIdentifier GO_PLUGIN_IDENTIFIER = new GoPluginIdentifier("task", Collections.singletonList("1.0"));

    private final PluginSettings settings;
    private final PluginTaskHandler taskHandler;
    private final PluginSettingsHandler settingsHandler;

    public JenkinsPlugin() {
        this(PluginSettings.getInstance(), new PluginTaskHandler(), new PluginSettingsHandler());
    }

    JenkinsPlugin(PluginSettings settings, PluginTaskHandler taskHandler, PluginSettingsHandler settingsHandler) {
        this.settings = settings;
        this.taskHandler = taskHandler;
        this.settingsHandler = settingsHandler;
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest requestMessage) throws UnhandledRequestTypeException {
        final String requestName = requestMessage.requestName();
        logger.debug("Got request [{}]", requestName);
        if (RequestName.CONFIGURATION.equals(requestName)) {
            return taskHandler.handleGetConfigRequest();
        } else if (RequestName.VALIDATE.equals(requestName)) {
            return taskHandler.handleValidation(requestMessage);
        } else if (RequestName.EXECUTE.equals(requestName)) {
            return  taskHandler.handleTaskExecution(requestMessage);
        } else if (RequestName.VIEW.equals(requestName)) {
            return  taskHandler.handleTaskView();
        }

        throw new UnhandledRequestTypeException(requestName);
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return GO_PLUGIN_IDENTIFIER;
    }
}
