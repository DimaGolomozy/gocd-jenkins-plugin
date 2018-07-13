package com.dg.gocd.utiils;

import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.Map;

import static com.dg.gocd.utiils.JSONUtils.toJSON;

/**
 * @author dima.golomozy
 */
public final class ResponseUtils {
    public static GoPluginApiResponse successResponse(final Map body) {
        return DefaultGoPluginApiResponse.success(toJSON(body));
    }

    public static GoPluginApiResponse errorResponse(final Map body) {
        return DefaultGoPluginApiResponse.error(toJSON(body));
    }

    public static GoPluginApiResponse badRequestResponse(final Map body) {
        return DefaultGoPluginApiResponse.badRequest(toJSON(body));
    }

    public static GoPluginApiResponse createResponse(final int responseCode, final Map body) {
        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(toJSON(body));
        return response;
    }
}
