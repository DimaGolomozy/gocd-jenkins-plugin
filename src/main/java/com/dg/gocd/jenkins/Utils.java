package com.dg.gocd.jenkins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * @author dima.golomozy
 */
public final class Utils {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static GoPluginApiResponse successResponse(final Map body) {
        return DefaultGoPluginApiResponse.success(toJson(body));
    }

    public static GoPluginApiResponse errorResponse(final Map body) {
        return DefaultGoPluginApiResponse.error(toJson(body));
    }

    public static GoPluginApiResponse badRequestResponse(final Map body) {
        return DefaultGoPluginApiResponse.badRequest(toJson(body));
    }

    public static GoPluginApiResponse createResponse(final int responseCode, final Map body) {
        final DefaultGoPluginApiResponse response = new DefaultGoPluginApiResponse(responseCode);
        response.setResponseBody(toJson(body));
        return response;
    }

    public static Map<String, Object> createField(String displayName, boolean isRequired, boolean isSecure, String displayOrder) {
        Map<String, Object> field = new HashMap<>();
        field.put("display-name", displayName);
        field.put("required", isRequired);
        field.put("secure", isSecure);
        field.put("display-order", displayOrder);
        return field;
    }

    @SuppressWarnings("unchecked")
    public static String getValueOrEmpty(Map map, String field) {
        return (String) ((Map) map.getOrDefault(field, emptyMap())).getOrDefault("value", "");
    }

    public static String toJson(final Map body) {
        return gson.toJson(body);
    }

    public static Map toMap(final String json) {
        return gson.fromJson(json, Map.class);
    }
}
