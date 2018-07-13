package com.dg.gocd.utiils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * @author dima.golomozy
 */
public final class JSONUtils {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static Map fromJSON(String json) {
        return gson.fromJson(json, Map.class);
    }

    public static String toJSON(Map map) {
        return gson.toJson(map);
    }
}
