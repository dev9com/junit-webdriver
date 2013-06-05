package com.dynacrongroup.webtest.rule;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: yurodivuie
 * Date: 6/3/13
 * Time: 11:48 AM
 */
public class DriverClassRuleIT {

    @ClassRule
    public static DriverClassRule driver = new DriverClassRule();

    private static final Logger LOG = LoggerFactory.getLogger(DriverClassRuleIT.class);

    @Rule
    public DriverMethodRule rule = new DriverMethodRule(driver);

    @Test
    public void loadASite() {
        driver.get("http://www.google.com");
        assertThat(driver.getTitle()).containsIgnoringCase("google");
    }
}
