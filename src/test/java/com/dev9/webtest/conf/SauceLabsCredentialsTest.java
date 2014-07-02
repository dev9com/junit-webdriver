package com.dev9.webtest.conf;

import com.typesafe.config.Config;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.net.URL;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.hamcrest.Matchers.*;

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

    private class MissingSauceLabsCredentials {

    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

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

    @Test
    public void missingCredentialPath() {
        Config config = mock(Config.class);
        exception.expect(RuntimeException.class);
        exception.expectMessage(containsString("required for Sauce Labs connection"));
        SauceLabsCredentials.getConnectionLocation(config);
    }


    @Test
    public void missingCredential() {
        Config config = WebtestConfigFactory.getConfig(MissingSauceLabsCredentials.class);
        exception.expect(RuntimeException.class);
        exception.expectMessage(containsString("saucelabs.user"));
        SauceLabsCredentials.getConnectionLocation(config);
    }

}
