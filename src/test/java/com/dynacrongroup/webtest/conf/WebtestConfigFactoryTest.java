package com.dynacrongroup.webtest.conf;

import com.typesafe.config.Config;
import org.junit.Test;
import org.slf4j.Logger;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: yurodivuie
 * Date: 5/30/13
 * Time: 3:52 PM
 */
public class WebtestConfigFactoryTest {

    private class WithProfile {

    }

    @Test
    public void testGetConfig() throws Exception {
        Config config = WebtestConfigFactory.getConfig();
        assertThat(config.getInt("test1")).isEqualTo(1);
        assertThat(config.hasPath("test2")).isFalse();
    }

    @Test
    public void testGetConfigForClass() throws Exception {
        Config config = WebtestConfigFactory.getConfig(WebtestConfigFactoryTest.class);
        assertThat(config.getInt("test1")).isEqualTo(1);
        assertThat(config.getInt("test2")).isEqualTo(2);
    }

    @Test
    public void testGetConfigForClassWithoutClassConf() throws Exception {
        Config config = WebtestConfigFactory.getConfig(Logger.class);
        assertThat(config.getInt("test1")).isEqualTo(1);
        assertThat(config.hasPath("test2")).isFalse();
    }

    @Test
    public void testGetConfigForClassWithProfile() throws Exception {
        Config config = WebtestConfigFactory.getConfig(WithProfile.class);
        assertThat(config.getInt("test1")).isEqualTo(1);
        assertThat(config.hasPath("test2")).isFalse();
        assertThat(config.getInt("test3")).isEqualTo(3);

    }


}
