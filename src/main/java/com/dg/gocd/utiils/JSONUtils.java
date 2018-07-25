package com.dg.gocd.utiils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author dima.golomozy
 */
public final class JSONUtils {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    public static <T> T fromJSON(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> String toJSON(T obj) {
        return gson.toJson(obj);
    }
}
