package com.dynacrongroup.webtest.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: yurodivuie
 * Date: 5/27/13
 * Time: 6:07 PM
 */
public class WebtestConfigFactory {


    private static final Logger LOG = LoggerFactory.getLogger(WebtestConfigFactory.class);
    private static final String CONF_EXTENSION = ".conf";
    private static final String PROFILE_PATH = "webtest.profile";
    private static final Config DEFAULT_CONFIG = addProfileToConfig(ConfigFactory.defaultOverrides()
            .withFallback(ConfigFactory.load()));
    private static final Map<Class, Config> CLASS_CONFIG = new ConcurrentHashMap<Class, Config>();

    private WebtestConfigFactory() throws IllegalAccessException {
        throw new IllegalAccessException("WebtestConfigFactory is a static utility class that cannot be constructed");
    }

    public static Config getConfig() {
        return DEFAULT_CONFIG;
    }

    public static Config getConfig(Class testClass) {
        if (!CLASS_CONFIG.containsKey(testClass)) {
            buildConfigForClass(testClass);
        }
        return CLASS_CONFIG.get(testClass);
    }

    private static void buildConfigForClass(Class testClass) {
        File classConfigFile = getClassConfigFile(testClass);
        Config classConfig;
        if (classConfigFile != null) {
            classConfig = ConfigFactory.defaultOverrides()
                    .withFallback(ConfigFactory.parseFile(getClassConfigFile(testClass)))
                    .withFallback(ConfigFactory.load());
            classConfig = addProfileToConfig(classConfig);
        }
        else {
            classConfig = getConfig();
        }
        CLASS_CONFIG.put(testClass, classConfig);
    }

    private static File getClassConfigFile(Class testClass) {
        URL classFileUrl = testClass.getResource(testClass.getSimpleName() + CONF_EXTENSION);
        File classFile = null;
        if (classFileUrl != null) {
            try {
                classFile = new File(classFileUrl.toURI());
            }
            catch (URISyntaxException ex) {
                LOG.warn("Failed to open config file for " + testClass.getSimpleName() + ": {}", ex.getMessage());
            }
        }
        return classFile;
    }

    private static Config addProfileToConfig(Config config) {
        String profile = getProfile(config);
        if (profile != null && config != null && config.hasPath(profile)) {
            return config.getConfig(profile).withFallback(config);
        }
        return config;
    }

    private static String getProfile(Config config) {
        String profile = null;
        if ( config != null && config.hasPath(PROFILE_PATH) ) {
            profile = config.getString(PROFILE_PATH);
        }
        return profile;
    }

}
