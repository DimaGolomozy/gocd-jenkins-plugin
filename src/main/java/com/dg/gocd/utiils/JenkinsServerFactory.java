package com.dg.gocd.utiils;

import com.offbytwo.jenkins.JenkinsServer;

import java.net.URI;

/**
 * @author dima.golomozy
 */
public class JenkinsServerFactory {
    private static JenkinsServerFactory instance = new JenkinsServerFactory();
    public static JenkinsServerFactory getFactory() {
        return instance;
    }

    private JenkinsServerFactory() {
    }

    public JenkinsServer getJenkinsServer(URI uri, String username, String password) {
        return new JenkinsServer(uri, username, password);
    }
}
