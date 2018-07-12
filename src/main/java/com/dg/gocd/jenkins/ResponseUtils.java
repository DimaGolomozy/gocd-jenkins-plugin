package com.dg.gocd.jenkins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.Map;

/**
 * @author dima.golomozy
 */
public final class ResponseUtils {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static GoPluginApiResponse success(final Map body) {
        return DefaultGoPluginApiResponse.success(toJson(body));
    }

    public static GoPluginApiResponse error(final Map body) {
        return DefaultGoPluginApiResponse.error(toJson(body));
    }

    public static GoPluginApiResponse badRequest(final Map body) {
        return DefaultGoPluginApiResponse.badRequest(toJson(body));
    }

    public static GoPluginApiResponse createResponse(final int responseCode, final Map body) {
        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(toJson(body));
        return response;
    }

    private static String toJson(final Map body) {
        return gson.toJson(body);
    }
}
