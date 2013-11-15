package com.dynacrongroup.webtest.sample;

import com.sun.istack.internal.Nullable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: yurodivuie
 * Date: 7/19/13
 * Time: 9:02 AM
 */
public class SampleTest {

    public static final WebDriver driver = new FirefoxDriver();
    private static final Logger LOG = LoggerFactory.getLogger(SampleTest.class);

    @Before
    public void startBrowser(){
        driver.get("http://www.google.com");
    }

    @After
    public void quit() {
        driver.quit();
    }

    @Test
    public void searchForDogs() {

        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("Dogs");
        element.sendKeys("\n");
        new WebDriverWait(driver, 10l).until(ExpectedConditions.presenceOfElementLocated(By.id("ires")));
        assertThat(driver.getTitle()).containsIgnoringCase("dogs");
        element = driver.findElement(By.xpath("//div[@id='ires']//a"));
        element.click();



        new WebDriverWait(driver, 10l).until(new ExpectedCondition<Object>() {
            @Override
            public Object apply(@Nullable org.openqa.selenium.WebDriver webDriver) {
                return driver.getCurrentUrl().equals("http://en.wikipedia.org/wiki/Dog");
            }
        });
//        new WebDriverWait(driver, 10l).until(driver.getCurrentUrl().equals("http://en.wikipedia.org/wiki/Dog"));
        LOG.info(driver.getTitle());
    }

}
