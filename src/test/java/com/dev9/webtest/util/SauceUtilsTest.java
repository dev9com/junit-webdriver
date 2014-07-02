package com.dev9.webtest.util;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * User: yurodivuie
 * Date: 7/18/13
 * Time: 12:27 PM
 */
public class SauceUtilsTest {

    private static final Logger LOG = LoggerFactory.getLogger(SauceUtilsTest.class);

    @Test
    public void notAJavaScriptExecutor() {
        WebDriver driver = mock(WebDriver.class);
        SauceUtils.logInContext(driver, "message1");
        verifyZeroInteractions(driver);
    }

    @Test
    public void javaScriptExecutor() {
        WebDriver driver = mock(RemoteWebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));
        SauceUtils.logInContext(driver, "message2");
        verify((JavascriptExecutor)driver).executeScript(contains("message2"));
    }

    @Test
    public void getJobIdFromHtmlUnitDriver() {
        assertThat(SauceUtils.getJobId(new HtmlUnitDriver())).isNull();
    }

    @Test
    @Ignore("Will not work in CI")
    public void getJobIdFromFirefox() {
        WebDriver driver = new FirefoxDriver();
        try {
            assertThat(SauceUtils.getJobId(new FirefoxDriver())).isNotNull();
        }
        finally {
            driver.quit();
        }
    }

    @Test
    public void getJobUrlFromHtmlUnitDriver() {
        assertThat(SauceUtils.getJobUrl(new HtmlUnitDriver())).isNull();
    }

}
