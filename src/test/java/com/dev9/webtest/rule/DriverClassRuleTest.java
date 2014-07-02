package com.dev9.webtest.rule;


import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * User: yurodivuie
 * Date: 5/23/13
 * Time: 12:34 PM
 */
public class DriverClassRuleTest {

    WebDriver driver = mock(WebDriver.class);
    Class testClass = DriverClassRuleTest.class;
    DriverClassRule rule = new DriverClassRule();

    @After
    public void resetDriver() {
        rule.setDriver(null);
    }

    @Test
    public void apply() throws Throwable {
        Description description = mock(Description.class);
        when(description.getTestClass()).thenReturn(testClass);
        assertThat(rule.unwrapDriver()).isNull();

        rule.apply(mock(Statement.class), description).evaluate();
        verify(description).getTestClass();
        assertThat(rule.getTestClass()).isEqualTo(testClass);
        assertThat(rule.unwrapDriver()).isNull();
    }

    @Test
    public void beforeWithoutDriver() throws Throwable {
        rule.initialize(testClass);
        assertThat(rule.unwrapDriver()).isNull();

        rule.before();
        assertThat(rule.unwrapDriver()).isInstanceOf(HtmlUnitDriver.class);
        assertThat(rule.hasFailed()).isFalse();
    }

    @Test
    public void after() {
        rule.setDriver(driver);

        rule.after();
        verify(driver).quit();
        assertThat(rule.unwrapDriver()).isNull();
    }

    @Test
    public void unwrapDriver() {
        rule.setDriver(driver);
        assertThat(rule.unwrapDriver()).isEqualTo(driver);
    }

    @Test
    public void rebuildDriver() {
        WebDriver badDriver = mock(WebDriver.class);
        rule.setDriver(badDriver);
        rule.initialize(testClass);

        rule.rebuildDriver();
        verify(badDriver).quit();
        assertThat(rule.unwrapDriver()).isInstanceOf(HtmlUnitDriver.class);
    }

    @Ignore("mockito can't handle the cast")
    @Test
    public void logContextMessage() {
        final String testMessage = "TEST";
        WebDriver driver = mock(WebDriver.class);
        rule.setDriver(driver);
        rule.logInContext(testMessage);
        verify((JavascriptExecutor)driver).executeScript(contains(testMessage));
    }

    @Test
    public void markAsFailed() {
        rule.setFailed(false);
        assertThat(rule.hasFailed()).isFalse();
        rule.markAsFailed();
        assertThat(rule.hasFailed()).isTrue();
    }

    @Test
    public void reportFailure() throws Throwable {
        rule.initialize(this.getClass());
        rule.before();
        assertThat(rule.hasFailed()).isFalse();
        rule.reportFailure();
        assertThat(rule.hasFailed()).isTrue();
    }

    @Test
    public void buildDriver() {
        rule.initialize(testClass);
        rule.buildDriver();
        assertThat(rule.unwrapDriver()).isInstanceOf(HtmlUnitDriver.class);
    }

    @Test
    public void destroyDriver() {
        rule.setDriver(driver);

        rule.destroyDriver();
        verify(driver).quit();
        assertThat(rule.unwrapDriver()).isNull();
    }
}
