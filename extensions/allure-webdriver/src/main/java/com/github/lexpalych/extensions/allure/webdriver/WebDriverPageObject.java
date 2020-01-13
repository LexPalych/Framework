package com.github.lexpalych.extensions.allure.webdriver;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.github.lexpalych.extensions.junit5.allure.steps.StepWrapperSteps;
import java.util.List;

import com.github.lexpalych.extensions.junit5.allure.steps.steprepositories.WebStepRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebDriverPageObject<T extends WebDriverPageObject<T>> extends StepWrapperSteps<T> implements WebStepRepository<T> {
  @SuppressWarnings("unused")
  private WebDriverPageObjectFactory pageObjectFactory;

  protected WebDriver driver;
  protected WebDriverWait driverWait;

  public final <D extends WebDriverPageObject<D>> D seePage(final Class<D> pageClass) {
    assertNotEquals(this.getClass(), pageClass);
    return pageObjectFactory.createPageObject(pageClass);
  }

  @Override
  public T openUrl(final String url) {
    driver.get(url);
    return (T) this;
  }

  protected void sendKeys(final String xpath, final CharSequence... value) {
    getWebElement(xpath).sendKeys(value);
  }

  protected void click(final String xpath) {
    getWebElement(xpath).click();
  }

  protected void clear(final String xpath) {
    getWebElement(xpath).clear();
  }

  protected WebElement getWebElement(final String xpath) {
    return driver.findElement(By.xpath(xpath));
  }

  protected List<WebElement> getWebElementList(final String xpath) {
    return driver.findElements(By.xpath(xpath));
  }

  protected void jsClick(String xpath) {
    jsClick(getWebElement(xpath));
  }

  protected void jsClick(WebElement webElement) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
  }

  protected void scrollIntoView(WebElement webElement) {
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
  }

  protected void scrollIntoView(String xpath) {
    scrollIntoView(getWebElement(xpath));
  }

  protected void waitUntilPageIsLoaded() {
    driverWait.until(
        webDriver ->
            (((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState")
                .equals("complete")));
  }
}
