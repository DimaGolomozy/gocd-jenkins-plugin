package com.dg.gocd.utiils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author dima.golomozy
 */
public final class JSONUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJSON(String json, Class<T> classOfT) {
        try {
            return mapper.readValue(json, classOfT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> String toJSON(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
