package com.dg.gocd.jenkins.task;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dg.gocd.utiils.GoPluginApiUtils.getValueOrDefault;

/**
 * @author dima.golomozy
 */
public class TaskConfig {
    public static final String URL_PROPERTY = "url";
    public static final String JOB_PROPERTY = "job";
    public static final String USERNAME_PROPERTY = "username";
    public static final String PASSWORD_PROPERTY = "password";
    public static final String PARAMS_PROPERTY = "params";

    private final String url;
    private final String job;
    private final String username;
    private final String password;
    private final Map<String, String> params;

    public TaskConfig(Map config) {
        this(
            getValueOrDefault(config, URL_PROPERTY, "value"),
            getValueOrDefault(config, JOB_PROPERTY, "value"),
            getValueOrDefault(config, USERNAME_PROPERTY, "value"),
            getValueOrDefault(config, PASSWORD_PROPERTY, "value"),
            Arrays.stream(((String) getValueOrDefault(config, PARAMS_PROPERTY, "value")).split("\\r?\\n"))
                .map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0].trim(), s -> s[1].trim()))
        );
    }

    TaskConfig(String url, String job, String username, String password, Map<String, String> params) {
        this.url = url;
        this.job = job;
        this.username = username;
        this.password = password;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public String getJob() {
        return job;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
