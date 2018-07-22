package com.dg.gocd.jenkins.task;

import com.dg.gocd.utiils.Utils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import com.thoughtworks.go.plugin.api.task.JobConsoleLogger;
import org.apache.commons.io.IOUtils;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.jenkins.task.TaskConfig.TOKEN_PROPERTY;
import static com.dg.gocd.jenkins.task.TaskConfig.URL_PROPERTY;
import static com.dg.gocd.utiils.JSONUtils.fromJSON;
import static com.dg.gocd.utiils.ResponseUtils.errorResponse;
import static com.dg.gocd.utiils.ResponseUtils.successResponse;
import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

/**
 * @author dima.golomozy
 */
public class TaskHandler {
    private static final Logger logger = Logger.getLoggerFor(TaskHandler.class);

    public GoPluginApiResponse handleTaskView() {
        Map<String, Object> view = new HashMap<>();
        view.put("displayValue", "Jenkins");
        try {
            view.put("template", IOUtils.toString(getClass().getResourceAsStream("/views/task.template.html"), "UTF-8"));
            return successResponse(view);
        } catch (Exception e) {
            String errorMessage = "Failed to find template: " + e.getMessage();
            view.put("exception", errorMessage);
            logger.error(errorMessage, e);
            return errorResponse(view);
        }
    }

    public GoPluginApiResponse handleTaskExecution(GoPluginApiRequest requestMessage) {
        JobConsoleLogger console = JobConsoleLogger.getConsoleLogger();

        console.printLine("Got request: " + requestMessage.requestBody());
        Map request = fromJSON(requestMessage.requestBody(), Map.class);
        TaskConfig taskConfig = new TaskConfig((Map) request.get("config"));
//        TaskContext taskContext = new TaskContext((Map) request.get("context"));

        console.printLine("Executing request to url: " + taskConfig.getUrl() + " with token: "  + taskConfig.getToken());
        HttpRequestWithBody httpRequest = Unirest.post(taskConfig.getUrl()).queryString("token", taskConfig.getToken());
        console.printLine("HttpRequest: " + httpRequest.toString());

        return sendAndWait(httpRequest);
    }

    private GoPluginApiResponse sendAndWait(HttpRequestWithBody httpRequest) {
//        try {
//            HttpResponse<Map> httpResponse = sendRequest(httpRequest);
//        } catch (UnirestException ue) {
//            String errorMessage = "Failed with request: " + ue.getMessage();
//            logger.error(errorMessage, ue);
//            return ResponseUtils.errorResponse(Collections.singletonMap("exception", errorMessage));
//        }
        return successResponse(new TaskResult(true, "Hello").toMap());
    }

    HttpResponse<Map> sendRequest(HttpRequest httpRequest) throws UnirestException {
        return httpRequest.asObject(Map.class);
    }

    public GoPluginApiResponse handleValidation(GoPluginApiRequest requestMessage) {
        return successResponse(singletonMap("errors", emptyMap()));
    }

    public GoPluginApiResponse handleGetConfigRequest() {
        final Map<String, Object> configMap = new HashMap<>();
        configMap.put(URL_PROPERTY, Utils.createField(URL_PROPERTY, false, false, "0"));
        configMap.put(TOKEN_PROPERTY, Utils.createField(TOKEN_PROPERTY, false, true, "1"));
        return successResponse(configMap);
    }
}
