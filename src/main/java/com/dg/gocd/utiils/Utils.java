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
    public static <T> T getValueOrDefault(Map map, String... pathToField) {
        int i = 0;
        for (; i < pathToField.length - 1; ++i) {
            map = (Map) map.getOrDefault(pathToField[i], emptyMap());
        }
        return (T) map.get(pathToField[i]);
    }
}
