package com.dynacrongroup.webtest.sample;

import com.dynacrongroup.webtest.rule.DriverClassRule;
import com.dynacrongroup.webtest.rule.DriverMethodRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assume.assumeTrue;

/**
 * User: yurodivuie
 * Date: 6/5/13
 * Time: 7:13 PM
 */

@RunWith(Parameterized.class)
public class LinkedParametersIT {

    @ClassRule
    public static DriverClassRule driver = new DriverClassRule();

    private static final Logger LOG = LoggerFactory.getLogger(LinkedParametersIT.class);
    private static final String LATIN_FOR_CAT = "felis catus";

    @Rule
    public DriverMethodRule rule = new DriverMethodRule(driver);

    private final String languageCode;
    private final String localizedCat;

    @Parameters
    public static Collection<Object[]> parameters() {
        Object[][] parameterArray = {
                {"en","Cat"},
                {"fr","Chat"},
                {"de","Hauskatze"},
                {"an","Felis_catus"}
        };

        return Arrays.asList(parameterArray);
    }

    public LinkedParametersIT(String languageCode, String localizedCat) {
        this.languageCode = languageCode;
        this.localizedCat = localizedCat;
    }

    @Test
    public void verifyLatinName() {
        assumeTrue(driver.getTargetWebDriver().isRemote());
        String url = format("http://%s.wikipedia.org/wiki/%s", languageCode, localizedCat);
        LOG.info("Test url: {}", url);
        driver.get(url);
        assertThat(driver.findElement(By.tagName("body")).getText()).containsIgnoringCase(LATIN_FOR_CAT);
    }

}
