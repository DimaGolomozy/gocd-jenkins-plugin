package com.dg.gocd.utiils;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyMap;

/**
 * @author dima.golomozy
 */
public final class Utils {

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
