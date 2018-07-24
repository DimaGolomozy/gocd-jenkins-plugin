package com.dg.gocd.jenkins.task;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dg.gocd.utiils.GoPluginApiUtils.getValueOrDefault;

/**
 * @author dima.golomozy
 */
public class TaskConfig {
    static final String URL_PROPERTY = "url";
    static final String TOKEN_PROPERTY = "token";
    static final String USERNAME_PROPERTY = "username";
    static final String PASSWORD_PROPERTY = "password";
    static final String PARAMS_PROPERTY = "params";

    private final String url;
    private final String token;
    private final String username;
    private final String password;
    private final Map<String, String> params;

    public TaskConfig(Map config) {
        this(
            getValueOrDefault(config, URL_PROPERTY, "value"),
            getValueOrDefault(config, TOKEN_PROPERTY, "value"),
            getValueOrDefault(config, USERNAME_PROPERTY, "value"),
            getValueOrDefault(config, PASSWORD_PROPERTY, "value"),
            Arrays.stream(((String) getValueOrDefault(config, PARAMS_PROPERTY, "value")).split(","))
                .map(s -> s.split("=")).collect(Collectors.toMap(s -> s[0], s -> s[1]))
        );
    }

    TaskConfig(String url, String token, String username, String password, Map<String, String> params) {
        this.url = url;
        this.token = token;
        this.username = username;
        this.password = password;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
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
