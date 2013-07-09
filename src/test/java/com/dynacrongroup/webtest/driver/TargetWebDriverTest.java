package com.dynacrongroup.webtest.driver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: yurodivuie
 * Date: 7/9/13
 * Time: 9:51 AM
 */
public class TargetWebDriverTest {

    private static final Logger LOG = LoggerFactory.getLogger(TargetWebDriverTest.class);

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private class TargetWebDriverTestBadPlatform {}
    private class TargetWebDriverTestGoodPlatform {}

    @Test
    public void badPlatform() {
        expectedException.expect(IllegalArgumentException.class);
        new TargetWebDriver(TargetWebDriverTestBadPlatform.class);
    }

    @Test
    public void goodPlatform() {
        TargetWebDriver targetWebDriver = new TargetWebDriver(TargetWebDriverTestGoodPlatform.class);
        assertThat(targetWebDriver.getPlatform()).isEqualTo(Platform.WINDOWS);
    }

}
