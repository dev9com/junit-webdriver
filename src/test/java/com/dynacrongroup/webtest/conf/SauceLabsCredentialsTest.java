package com.dynacrongroup.webtest.conf;

import com.typesafe.config.Config;
import org.junit.Test;

import java.net.URL;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: yurodivuie
 * Date: 5/30/13
 * Time: 3:57 PM
 */
public class SauceLabsCredentialsTest {

    Config config = WebtestConfigFactory.getConfig(SauceLabsCredentialsTest.class);
    String expectedUser = config.getString(SauceLabsCredentials.SAUCELABS_USER_PATH);
    String expectedKey = config.getString(SauceLabsCredentials.SAUCELABS_KEY_PATH);
    String expectedServer = config.getString(SauceLabsCredentials.SAUCELABS_SERVER_PATH);

    @Test
    public void testGetUser() throws Exception {
        String testUser = SauceLabsCredentials.getUser(config);
        assertThat(testUser).isNotNull();
        assertThat(testUser).isEqualTo(expectedUser);
    }

    @Test
    public void testGetKey() throws Exception {
        String testKey = SauceLabsCredentials.getKey(config);
        assertThat(testKey).isNotNull();
        assertThat(testKey).isEqualTo(expectedKey);
    }

    @Test
    public void testGetServer() throws Exception {
        String testServer = SauceLabsCredentials.getServer(config);
        assertThat(testServer).isNotNull();
        assertThat(testServer).isEqualTo(expectedServer);
    }

    @Test
    public void testGetConnectionUrl() throws Exception {
        URL url = SauceLabsCredentials.getConnectionLocation(config);
        assertThat(url).isNotNull();
        assertThat(url.toString()).
                containsIgnoringCase(expectedServer).
                containsIgnoringCase(expectedUser).
                containsIgnoringCase(expectedKey);
    }

}
