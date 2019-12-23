package com.github.lexpalych.allure.webdriver;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.github.lexpalych.allure.webdriver.WebDriverFactory.getWebDriver;

public final class PageObjectFactoryManager {
    private static final Config CONFIG = ConfigFactory.load();

    public static WebDriverPageObjectFactory createWebDriverFactory() {
        String browser = CONFIG.getString("selenium.browser");
        int timeToWait = CONFIG.getInt("selenium.timeToWait");
        boolean isHeadless = CONFIG.getBoolean("selenium.isHeadless");

        WebDriver webDriver = getWebDriver(browser, timeToWait, isHeadless);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, timeToWait);

        return new WebDriverPageObjectFactory(webDriver, webDriverWait);
    }
}
