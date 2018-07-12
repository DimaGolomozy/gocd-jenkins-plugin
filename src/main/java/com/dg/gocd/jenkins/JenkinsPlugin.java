package com.dg.gocd.jenkins;

import com.dg.gocd.RequestName;
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

    private final PluginTaskHandler pluginTaskHandler;
    private final PluginSettingsHandler pluginSettingsHandler;

    public JenkinsPlugin() {
        this(new PluginTaskHandler(), new PluginSettingsHandler());
    }

    JenkinsPlugin(PluginTaskHandler pluginTaskHandler, PluginSettingsHandler pluginSettingsHandler) {
        this.pluginTaskHandler = pluginTaskHandler;
        this.pluginSettingsHandler = pluginSettingsHandler;
    }

    @Override
    public GoPluginApiResponse handle(GoPluginApiRequest requestMessage) throws UnhandledRequestTypeException {
        final String requestName = requestMessage.requestName();
        if (RequestName.CONFIGURATION.equals(requestName)) {
            return handleGetConfigRequest();
        } else if (RequestName.VALIDATE.equals(requestName)) {
            return handleValidation(requestMessage);
        } else if (RequestName.EXECUTE.equals(requestName)) {
            return handleTaskExecution(requestMessage);
        } else if (RequestName.VIEW.equals(requestName)) {
            return handleTaskView();
        }

        throw new UnhandledRequestTypeException(requestName);
    }

    @Override
    public GoPluginIdentifier pluginIdentifier() {
        return GO_PLUGIN_IDENTIFIER;
    }
}
