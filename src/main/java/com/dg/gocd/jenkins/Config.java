package com.dg.gocd.jenkins;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.dg.gocd.jenkins.Utils.getValueOrEmpty;


/**
 * @author dima.golomozy
 */
public class Config {
    static final String URL_PROPERTY = "url";
    static final String TOKEN_PROPERTY = "token";

    private final String url;
    private final String token;

    private Config(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public static Map<String, String> validateConfig(Map configMap) {
        return Collections.emptyMap();
    }

    public static Config fromMap(Map configMap) {
        return new Config(
            getValueOrEmpty(configMap, URL_PROPERTY),
            getValueOrEmpty(configMap, TOKEN_PROPERTY)
        );
    }

    public static Map<String, Object> toMap() {
        final Map<String, Object> configMap = new HashMap<>();
        configMap.put(URL_PROPERTY, Utils.createField("url", true, false, "0"));
        configMap.put(TOKEN_PROPERTY, Utils.createField("token", true, true, "1"));
        return configMap;
    }
}
