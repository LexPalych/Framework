package com.github.lexpalych.allure.webdriver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

class WebDriverPageObjectTests {
  private static class ConcretePageObject extends WebDriverPageObject<ConcretePageObject> {}

  private static class AnotherConcretePageObject
      extends WebDriverPageObject<AnotherConcretePageObject> {}

  @ParameterizedTest
  @MethodSource("seePageProvider")
  void seePage(WebDriverPageObject<ConcretePageObject> pageObject) {
    assertDoesNotThrow(() -> pageObject.seePage(AnotherConcretePageObject.class));
  }

  @ParameterizedTest
  @MethodSource("seePageProvider")
  void seePageThrowsIfSameClass(WebDriverPageObject<ConcretePageObject> pageObject) {
    assertThrows(AssertionError.class, () -> pageObject.seePage(ConcretePageObject.class));
  }

  @ParameterizedTest
  @MethodSource("xpathAndValueProvider")
  void sendKeys(
      WebDriverPageObject<?> pageObject,
      WebDriver webDriver,
      WebElement webElement,
      String xpath,
      String value) {
    pageObject.sendKeys(xpath, value);

    verify(webDriver, only()).findElement(By.xpath(xpath));
    verify(webElement, only()).sendKeys(value);
  }

  @ParameterizedTest
  @MethodSource("xpathAndValueProvider")
  void click(
      WebDriverPageObject<?> pageObject, WebDriver webDriver, WebElement webElement, String xpath) {
    pageObject.click(xpath);

    verify(webDriver, only()).findElement(By.xpath(xpath));
    verify(webElement, only()).click();
  }

  @ParameterizedTest
  @MethodSource("xpathAndValueProvider")
  void clear(
      WebDriverPageObject<?> pageObject, WebDriver webDriver, WebElement webElement, String xpath) {
    pageObject.clear(xpath);

    verify(webDriver, only()).findElement(By.xpath(xpath));
    verify(webElement, only()).clear();
  }

  @ParameterizedTest
  @MethodSource("webDriverAndXpathProvider")
  void getWebElement(WebDriverPageObject<?> pageObject, WebDriver webDriver, String xpath) {
    pageObject.getWebElement(xpath);

    verify(webDriver, only()).findElement(By.xpath(xpath));
  }

  @ParameterizedTest
  @MethodSource("webDriverAndXpathProvider")
  void getWebElementList(WebDriverPageObject<?> pageObject, WebDriver webDriver, String xpath) {
    pageObject.getWebElementList(xpath);

    verify(webDriver, only()).findElements(By.xpath(xpath));
  }

  @ParameterizedTest
  @MethodSource("xpathAndValueProvider")
  void jsClick(
      WebDriverPageObject<?> pageObject, WebDriver webDriver, WebElement webElement, String xpath) {
    pageObject.jsClick(xpath);

    verify(webDriver, times(1)).findElement(By.xpath(xpath));
    verify(((JavascriptExecutor) webDriver), times(1))
        .executeScript("arguments[0].click();", webElement);
  }

  @ParameterizedTest
  @MethodSource("xpathAndValueProvider")
  void scrollIntoView(
      WebDriverPageObject<?> pageObject, WebDriver webDriver, WebElement webElement, String xpath) {
    pageObject.scrollIntoView(xpath);

    verify(webDriver, times(1)).findElement(By.xpath(xpath));
    verify(((JavascriptExecutor) webDriver), times(1))
        .executeScript("arguments[0].scrollIntoView(true);", webElement);
  }

  @Test
  void waitUntilPageIsLoaded() {
    WebDriver webDriver = mock(ChromeDriver.class);
    WebDriverWait webDriverWait = mock(WebDriverWait.class);

    @SuppressWarnings("unchecked")
    ArgumentCaptor<Function<WebDriver, Boolean>> captor = ArgumentCaptor.forClass(Function.class);

    ConcretePageObject pageObject = new ConcretePageObject();

    pageObject.driver = webDriver;
    pageObject.driverWait = webDriverWait;

    when(((JavascriptExecutor) webDriver).executeScript(any(), any())).thenReturn(false);

    pageObject.waitUntilPageIsLoaded();

    verify(webDriverWait, only()).until(captor.capture());

    captor.getValue().apply(webDriver);

    verify(((JavascriptExecutor) webDriver), only()).executeScript("return document.readyState");
  }

  private static Stream<Arguments> webDriverAndXpathProvider() {
    String xpath = "someXpath";

    WebDriver webDriver = mock(WebDriver.class);

    ConcretePageObject pageObject = new ConcretePageObject();

    pageObject.driver = webDriver;

    return Stream.of(Arguments.of(pageObject, webDriver, xpath));
  }

  private static Stream<Arguments> xpathAndValueProvider() {
    String xpath = "someXpath";
    String value = "someValue";

    WebDriver webDriver = mock(ChromeDriver.class);
    WebElement webElement = mock(WebElement.class);

    when(webDriver.findElement(By.xpath(xpath))).thenReturn(webElement);

    ConcretePageObject pageObject = new ConcretePageObject();

    pageObject.driver = webDriver;

    return Stream.of(Arguments.of(pageObject, webDriver, webElement, xpath, value));
  }

  private static Stream<Arguments> seePageProvider() {
    WebDriver webDriver = mock(WebDriver.class);
    WebDriverWait webDriverWait = mock(WebDriverWait.class);

    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(webDriver, webDriverWait);

    WebDriverPageObject<ConcretePageObject> pageObject =
        factory.createPageObject(ConcretePageObject.class);

    return Stream.of(Arguments.of(pageObject));
  }
}
