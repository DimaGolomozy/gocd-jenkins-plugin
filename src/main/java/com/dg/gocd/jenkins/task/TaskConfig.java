package com.dg.gocd.jenkins.task;

import java.util.Map;

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

    public TaskConfig(String url, String job, String username, String password, Map<String, String> params) {
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
