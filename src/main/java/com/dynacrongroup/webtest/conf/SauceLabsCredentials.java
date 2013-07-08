package com.dynacrongroup.webtest.conf;

import com.google.common.annotations.VisibleForTesting;
import com.typesafe.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: yurodivuie
 * Date: 5/27/13
 * Time: 6:07 PM
 */
public class SauceLabsCredentials {

    private static final Config CONFIG = WebtestConfigFactory.getConfig();
    protected static final String SAUCELABS_USER_PATH = "saucelabs.user";
    protected static final String SAUCELABS_KEY_PATH = "saucelabs.key";
    protected static final String SAUCELABS_SERVER_PATH = "saucelabs.server";

    private static final Logger LOG = LoggerFactory
            .getLogger(SauceLabsCredentials.class);

    private SauceLabsCredentials() throws IllegalAccessException {
        throw new IllegalAccessException("SauceLabsCredentials is a static utility class that cannot be constructed");
    }

    public static String getUser() {
        return getUser(CONFIG);
    }

    public static String getKey() {
        return getKey(CONFIG);
    }

    public static String getServer() {
        return getServer(CONFIG);
    }


    /**
     * Gets the appropriate URL string for the configured credentials, as used by RemoteWebDriver.
     *
     * @return
     */
    public static URL getConnectionLocation() {
        return getConnectionLocation(CONFIG);
    }

    @VisibleForTesting
    protected static String getUser(Config config) {
        return safelyGetConfigString(config, SAUCELABS_USER_PATH);
    }

    @VisibleForTesting
    protected static String getKey(Config config) {
        return safelyGetConfigString(config, SAUCELABS_KEY_PATH);
    }

    @VisibleForTesting
    protected static String getServer(Config config) {
        return safelyGetConfigString(config, SAUCELABS_SERVER_PATH);
    }

    @VisibleForTesting
    protected static URL getConnectionLocation(Config config) {
        URL url;
        try {
            String urlString = String.format("http://%s:%s@%s", getUser(config), getKey(config), getServer(config));
            url = new URL( urlString );
        } catch (MalformedURLException e) {
            throw new RuntimeException("Unable to parse remote selenium server connection information", e);
        }
        return url;
    }


    private static String safelyGetConfigString(Config config, String path) {
        if (!config.hasPath(path) || config.getString(path).isEmpty()) {
            String message = String.format("Config value %s missing - required for Sauce Labs connection.", path);
            throw new RuntimeException(message);
        }
        return config.getString(path);
    }
}
