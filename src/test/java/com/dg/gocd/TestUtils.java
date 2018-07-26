package com.dg.gocd;

import com.thoughtworks.go.plugin.api.request.DefaultGoPluginApiRequest;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

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

    public static GoPluginApiRequest emptyRequest() {
        return newRequest(null);
    }

    public static GoPluginApiRequest newRequest(String requestName) {
        return newRequest(requestName, null);
    }

    public static GoPluginApiRequest newRequest(String requestName, String body) {
        DefaultGoPluginApiRequest request = new DefaultGoPluginApiRequest("", "", requestName);
        if (body != null) request.setRequestBody(body);

        return request;
    }

    public static String getResource(String name) throws IOException {
        return IOUtils.toString(TestUtils.class.getClassLoader().getResourceAsStream(name), "UTF-8");
    }
}
