package com.dev9.webtest.driver;

import com.opera.core.systems.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public enum Browser {
    CHROME(org.openqa.selenium.chrome.ChromeDriver.class),
    FIREFOX(org.openqa.selenium.firefox.FirefoxDriver.class),
    PHANTOMJS(PhantomJSDriver.class),
    HTMLUNIT(org.openqa.selenium.htmlunit.HtmlUnitDriver.class),
    IEXPLORE(org.openqa.selenium.ie.InternetExplorerDriver.class),
    OPERA(OperaDriver.class),
    SAFARI(org.openqa.selenium.safari.SafariDriver.class);

    private Class driverClass;

    public Class getDriverClass() {
        return driverClass;
    }

    private Browser(Class driverClass) {
        this.driverClass = driverClass;
    }

    //@JsonCreator
    public static Browser fromJson(String text) {
        return valueOf(text.toUpperCase());
    }
}