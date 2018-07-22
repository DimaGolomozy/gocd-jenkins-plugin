package com.dg.gocd.jenkins.task;

import java.util.Map;

import static com.dg.gocd.utiils.Utils.getValueOrDefault;

/**
 * @author dima.golomozy
 */
public class TaskConfig {
    static final String URL_PROPERTY = "url";
    static final String TOKEN_PROPERTY = "token";

    private final String url;
    private final String token;

    public TaskConfig(Map config) {
        this(
            getValueOrDefault(config, URL_PROPERTY, "value"),
            getValueOrDefault(config, TOKEN_PROPERTY, "value")
        );
    }

    TaskConfig(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }
}
