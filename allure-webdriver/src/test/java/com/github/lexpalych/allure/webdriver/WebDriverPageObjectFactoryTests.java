package com.github.lexpalych.allure.webdriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

class WebDriverPageObjectFactoryTests {
  private static class CoolPageObject extends WebDriverPageObject<CoolPageObject> {}

  @Test
  void createPageObject() {
    WebDriver webDriver = mock(WebDriver.class);
    WebDriverWait webDriverWait = mock(WebDriverWait.class);

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(webDriver, webDriverWait);

    WebDriverPageObject<CoolPageObject> pageObject = factory.createPageObject(CoolPageObject.class);

    assertEquals(webDriver, pageObject.driver);
    assertEquals(webDriverWait, pageObject.driverWait);
  }

  @Test
  void createPageObjectCache() {
    WebDriver webDriver = mock(WebDriver.class);
    WebDriverWait webDriverWait = mock(WebDriverWait.class);

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(webDriver, webDriverWait);

    WebDriverPageObject<CoolPageObject> first = factory.createPageObject(CoolPageObject.class);

    WebDriverPageObject<CoolPageObject> second = factory.createPageObject(CoolPageObject.class);

    assertEquals(first, second);
  }

  @Test
  void createPageObjectNoDefaultConstructor() {
    class SomePageObject extends WebDriverPageObject<SomePageObject> {
      private SomePageObject(String parameter) {}
    }

    WebDriver webDriver = mock(WebDriver.class);

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(webDriver, null);

    assertThrows(
        WebDriverPageObjectFactory.PageNotInitializedException.class,
        () -> factory.createPageObject(SomePageObject.class));
  }

  @Test
  void createPageObjectSuperclass() {
    class SomePageObject<T extends WebDriverPageObject<T>> extends WebDriverPageObject<T> {}

    class ChildPageObject extends SomePageObject<ChildPageObject> {}

    WebDriver webDriver = mock(WebDriver.class);

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(webDriver, null);

    assertThrows(
        WebDriverPageObjectFactory.PageNotInitializedException.class,
        () -> factory.createPageObject(ChildPageObject.class));
  }

  @Test
  void shutdown() {
    WebDriver webDriver = mock(WebDriver.class);

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(webDriver, null);

    factory.shutdown();

    verify(webDriver, only()).quit();
  }

  @Test
  void makeScreenshot() {
    WebDriver webDriver = mock(ChromeDriver.class);

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(webDriver, null);

    factory.makeScreenshot();

    verify(((TakesScreenshot) webDriver), only()).getScreenshotAs(OutputType.BYTES);
  }

  @Test
  void setFieldValue() {
    class SomeClass {}

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(null, null);

    assertThrows(
        WebDriverPageObjectFactory.PageNotInitializedException.class,
        () -> factory.setFieldValue(SomeClass.class, new SomeClass(), Map.of("lol", "wut")));
  }
}
