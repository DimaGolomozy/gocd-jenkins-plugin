package com.dg.gocd.jenkins.task;

import com.thoughtworks.go.plugin.api.response.DefaultGoApiResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dima.golomozy
 */
public class TaskResult {
    private boolean success;
    private String message;
    private Exception exception;

    public TaskResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public TaskResult(boolean success, String message, Exception exception) {
        this(success, message);
        this.exception = exception;
    }

    public Map toMap() {
        final HashMap result = new HashMap();
        result.put("success", success);
        result.put("message", message);
        result.put("exception", exception);
        return result;
    }

    public int responseCode() {
        return success ? DefaultGoApiResponse.SUCCESS_RESPONSE_CODE : DefaultGoApiResponse.INTERNAL_ERROR;
    }
}
