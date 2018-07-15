package com.dg.gocd;

import com.thoughtworks.go.plugin.api.request.DefaultGoPluginApiRequest;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import static com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse.*;
import static org.junit.Assert.assertEquals;

/**
 * @author dima.golomozy
 */
public class TestUtils {

    public static void assertSuccessResponse(GoPluginApiResponse response) {
        assertEquals(SUCCESS_RESPONSE_CODE, response.responseCode());
    }

    public static void assertErrorResponse(GoPluginApiResponse response) {
        assertEquals(INTERNAL_ERROR, response.responseCode());
    }

    public static void assertBadRequestResponse(GoPluginApiResponse response) {
        assertEquals(BAD_REQUEST, response.responseCode());
    }

    public static GoPluginApiRequest emptyRequest() {
        return newRequest("");
    }

    public static GoPluginApiRequest newRequest(String requestName) {
        return new DefaultGoPluginApiRequest("", "", requestName);
    }
}
