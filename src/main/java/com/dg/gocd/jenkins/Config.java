package com.dg.gocd.jenkins;

import java.util.Map;

/**
 * @author dima.golomozy
 */
public class Config {
    static final String URL_PROPERTY = "url";
    static final String TOKEN_PROPERTY = "token";

    private final String url;
    private final String token;

    public Config(Map config) {
        this.url = getValue(config, URL_PROPERTY);
        this.token = getValue(config, TOKEN_PROPERTY);
    }

    private String getValue(Map config, String property) {
        return (String) ((Map) config.get(property)).get("value");
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }
}
