package com.github.lexpalych.extensions.allure.webdriver;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class WebDriverPageObjectFactory {
  private static final Logger LOGGER = LogManager.getLogger(WebDriverPageObjectFactory.class);

  private int COUNTER;
  private final WebDriver webDriver;
  private final WebDriverWait webDriverWait;
  private final Map<Class<? extends WebDriverPageObject<?>>, WebDriverPageObject<? extends WebDriverPageObject<?>>>
          pageObjectCache = new HashMap<>();

  WebDriverPageObjectFactory(final WebDriver webDriver, final WebDriverWait webDriverWait) {
    this.webDriver = webDriver;
    this.webDriverWait = webDriverWait;
  }

  <T extends WebDriverPageObject<T>> T createPageObject(final Class<T> pageObjectClass) {
    isSupported(pageObjectClass);

    LOGGER.debug("Creating PageObject " + pageObjectClass.getName());

    WebDriverPageObject<? extends WebDriverPageObject<?>> cachedPageObject =
        pageObjectCache.get(pageObjectClass);

    if (cachedPageObject != null) {
      LOGGER.debug("Cache request № " + COUNTER++);
      return pageObjectClass.cast(cachedPageObject);
    } else {
      try {
        Constructor<T> constructor = pageObjectClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance = constructor.newInstance();
        initPage(instance, pageObjectClass);
        pageObjectCache.put(pageObjectClass, instance);
        return instance;
      } catch (final InstantiationException
          | IllegalAccessException
          | InvocationTargetException
          | NoSuchMethodException e) {
        e.printStackTrace();
        throw new PageNotInitializedException(
            "Page cannot be initialized, problem in the default constructor");
      }
    }
  }

  private <T extends WebDriverPageObject<T>> void isSupported(final Class<T> pageObjectClass) {
    LOGGER.debug(
        "Validity check PageObject "
            + pageObjectClass.getName()
            + " and PageObjectFactory "
            + this.getClass().getName());
    Class<?> superClass = pageObjectClass.getSuperclass();

    if (!superClass.equals(WebDriverPageObject.class)) {
      throw new PageNotInitializedException(
          this.getClass().getName() + " does not support PageObject " + pageObjectClass.getName());
    }
  }

  void shutdown() {
    webDriver.quit();
  }

  byte[] makeScreenshot() {
    return ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
  }

  private <T extends WebDriverPageObject<T>> void initPage(
      final T pageObjectInstance, Class<T> pageObjectClass) {
    // Страница уже проверена на валидность, можно спокойно кастовать
    @SuppressWarnings("unchecked")
    Class<WebDriverPageObject<?>> superClass =
        (Class<WebDriverPageObject<?>>) pageObjectClass.getSuperclass();

    Map<String, Object> nameAndValues =
        Map.of(
            "driver", webDriver,
            "driverWait", webDriverWait,
            "pageObjectFactory", this);

    // Получение полей страницы и инициализация
    setFieldValue(superClass, pageObjectInstance, nameAndValues);
  }

  <T> void setFieldValue(Class<T> toSearch, Object instance, Map<String, Object> nameAndValues) {
    nameAndValues.forEach(
        (key, value) -> {
          try {
            Field field = toSearch.getDeclaredField(key);
            field.setAccessible(true);
            field.set(instance, value);
          } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            throw new PageNotInitializedException(
                "Cannot init page" + instance.getClass().getName());
          }
        });
  }

  static class PageNotInitializedException extends RuntimeException {
    PageNotInitializedException(final String message) {
      super(message);
    }
  }
}
