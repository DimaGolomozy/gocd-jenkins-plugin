package com.dg.gocd;

/**
 * @author dima.golomozy
 */
public final class RequestName {
    public static final String CONFIGURATION = "configuration";
    public static final String VIEW = "view";
    public static final String VALIDATE = "validate";
    public static final String EXECUTE = "execute";

    public static final String DISPLAY_NAME_ENVIRONMENT_PATTERN = "Go environment files pattern";
    public static final String DISPLAY_NAME_PIPELINE_PATTERN = "Go pipeline files pattern";

    public static final String PLUGIN_SETTINGS_PIPELINE_PATTERN = "pipeline_pattern";
    public static final String MISSING_DIRECTORY_MESSAGE = "directory property is missing in parse-directory request";
    public static final String EMPTY_REQUEST_BODY_MESSAGE = "Request body cannot be null or empty";

    public static final String PLUGIN_ID = "json.config.plugin";
    public static final String GET_PLUGIN_SETTINGS = "go.processor.plugin-settings.get";
    public static final String PLUGIN_SETTINGS_GET_CONFIGURATION = "go.plugin-settings.get-configuration";
    public static final String PLUGIN_SETTINGS_GET_VIEW = "go.plugin-settings.get-view";
    public static final String PLUGIN_SETTINGS_VALIDATE_CONFIGURATION = "go.plugin-settings.validate-configuration";
    public static final String PLUGIN_SETTINGS_ENVIRONMENT_PATTERN = "environment_pattern";
    public static final String DEFAULT_ENVIRONMENT_PATTERN = "**/*.goenvironment.json";
    public static final String DEFAULT_PIPELINE_PATTERN = "**/*.gopipeline.json";
}
