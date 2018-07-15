package com.dg.gocd.jenkins.task;

/**
 * @author dima.golomozy
 */
public class TaskConfig {

    private final String url;
    private final String token;

    public TaskConfig(String url, String token) {
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
