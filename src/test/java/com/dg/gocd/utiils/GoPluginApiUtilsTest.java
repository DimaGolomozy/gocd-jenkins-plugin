package com.dg.gocd.utiils;

import org.junit.Test;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.assertEquals;

/**
 * @author dima.golomozy
 */
public class GoPluginApiUtilsTest {

    @Test
    public void createFieldTest() throws Exception {
        Map<String, Object> field = GoPluginApiUtils.createField("name", true, false, "1");
        assertEquals("name", field.get("display-name"));
        assertEquals(true, field.get("required"));
        assertEquals(false, field.get("secure"));
        assertEquals("1", field.get("display-order"));

    }

    @Test
    public void getValueTest() throws Exception {
        Map<String, Object> map = singletonMap("name", singletonMap("value", "expected"));
        String actual = GoPluginApiUtils.getValueOrEmpty(map, "name");

        assertEquals("expected", actual);
    }

    @Test
    public void getValueEmptyTest() throws Exception {
        Map<String, Object> map = singletonMap("name", singletonMap("notValue", "expected"));
        String actual = GoPluginApiUtils.getValueOrEmpty(map, "name");

        assertEquals("", actual);
    }
}