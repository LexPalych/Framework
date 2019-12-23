package com.github.lexpalych.allure.webdriver;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.*;

import static com.github.lexpalych.allure.webdriver.PageObjectFactoryManager.createWebDriverFactory;

public final class WebDriverPageObjectFactoryCallbacks
    implements AfterEachCallback, ParameterResolver, TestExecutionExceptionHandler {
  private static final Logger LOGGER = LogManager.getLogger();

  private final Set<Class<? extends WebDriverPageObject<?>>> pageObjectClassSet;
  private final WebDriverPageObjectFactory pageObjectFactory;

//  public WebDriverPageObjectFactoryCallbacks(
//          Set<Class<? extends WebDriverPageObject<?>>> pageObjectClassSet,
//          WebDriverPageObjectFactory pageObjectFactory) {
//
//    this.pageObjectClassSet = pageObjectClassSet;
//    this.pageObjectFactory = pageObjectFactory;
//  }

  public WebDriverPageObjectFactoryCallbacks(Set<Class<? extends WebDriverPageObject<?>>> pageObjectClassSet) {

    this.pageObjectClassSet = pageObjectClassSet;
    this.pageObjectFactory = createWebDriverFactory();
  }

  @Override
  public void afterEach(ExtensionContext context) {
    this.pageObjectFactory.shutdown();
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return pageObjectClassSet.stream()
        .anyMatch(
            pageObjectClass -> parameterContext.getParameter().getType().equals(pageObjectClass));
  }

  @SuppressWarnings("unchecked")
  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return pageObjectFactory.createPageObject(
        (Class<? extends WebDriverPageObject>) parameterContext.getParameter().getType());
  }

  @Override
  public void handleTestExecutionException(ExtensionContext context, Throwable throwable)
      throws Throwable {
    LOGGER.debug("Test ended with exception");
    lifecycle().addAttachment("Screenshot", "image/png", "", pageObjectFactory.makeScreenshot());
    throw throwable;
  }

  /** Only for testing */
  AllureLifecycle lifecycle() {
    return Allure.getLifecycle();
  }
}
