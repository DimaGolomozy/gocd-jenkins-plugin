package com.dg.gocd.utiils;

import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.utiils.JSONUtils.toJSON;
import static java.util.Collections.emptyMap;

/**
 * @author dima.golomozy
 */
public final class GoPluginApiUtils {

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
}
