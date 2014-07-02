package com.dev9.webtest.rule;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;

import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.*;

/**
 * User: yurodivuie
 * Date: 5/23/13
 * Time: 12:18 PM
 */
public class DriverMethodRuleTest {

    DriverClassRule driver = mock(DriverClassRule.class);
    DriverMethodRule rule = new DriverMethodRule(driver);
    Description description = mock(Description.class);

    @Before
    public void prepareDescription() {
        when(description.getTestClass()).thenReturn((Class)DriverMethodRule.class);
    }

    @Test
    public void ruleLogsMethodStart() {
        rule.starting(description);
        verify(driver).logInContext(contains(DriverMethodRule.STARTED_MESSAGE));
    }

    @Test
    public void ruleLogsMethodFailure() {
        rule.failed(new Exception(), description);
        verify(driver).logInContext(contains(DriverMethodRule.FAILED_MESSAGE));
        verify(driver).reportFailure();
    }

    @Test
    public void ruleLogsMethodPass() {
        rule.succeeded(description);
        verify(driver).logInContext(contains(DriverMethodRule.PASSED_MESSAGE));
    }
}
