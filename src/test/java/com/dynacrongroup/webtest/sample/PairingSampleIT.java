package com.dynacrongroup.webtest.sample;

import com.dynacrongroup.webtest.rule.DriverClassRule;
import com.dynacrongroup.webtest.rule.DriverMethodRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * User: yurodivuie
 * Date: 6/3/13
 * Time: 11:48 AM
 */
public class PairingSampleIT {

    @ClassRule
    public static DriverClassRule driver = new DriverClassRule();

    private static final Logger LOG = LoggerFactory.getLogger(PairingSampleIT.class);

    @Rule
    public DriverMethodRule rule = new DriverMethodRule(driver);

    @Test
    @Ignore("used as a sample only")
    public void searchForCat() throws InterruptedException {
        String searchTerm = "cat";
        searchFor(searchTerm);

        String resultTitle = driver.getTitle();
        LOG.debug("Title of search results page is [{}]", resultTitle);
        assertThat(resultTitle).containsIgnoringCase(searchTerm);
        new Actions(driver);
    }

    private void searchFor(String searchTerm) {
        driver.get("http://www.google.com");
        assertThat(driver.getTitle()).containsIgnoringCase("google");
        driver.findElement(By.name("q")).sendKeys(searchTerm + "\n");
        new WebDriverWait(driver, 10l).until(ExpectedConditions.presenceOfElementLocated(By.id("ires")));
    }
}
