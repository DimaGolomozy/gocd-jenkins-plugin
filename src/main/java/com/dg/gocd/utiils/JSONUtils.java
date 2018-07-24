package com.dg.gocd.utiils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author dima.golomozy
 */
public final class JSONUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T fromJSON(String json, Class<T> classOfT) throws IOException {
        return mapper.readValue(json, classOfT);
    }

    public static <T> String toJSON(T obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }
}
