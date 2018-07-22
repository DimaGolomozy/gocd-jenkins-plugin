package com.dg.gocd.utiils;

import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import static com.dg.gocd.utiils.JSONUtils.toJSON;

/**
 * @author dima.golomozy
 */
public final class ResponseUtils {
    public static <T> GoPluginApiResponse successResponse(final T body) {
        return DefaultGoPluginApiResponse.success(toJSON(body));
    }

    public static <T> GoPluginApiResponse errorResponse(final T body) {
        return DefaultGoPluginApiResponse.error(toJSON(body));
    }

    public static <T> GoPluginApiResponse createResponse(final int responseCode, final T body) {
        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(toJSON(body));
        return response;
    }
}
