package com.github.lexpalych.allure.webdriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.qameta.allure.AllureLifecycle;
import java.lang.reflect.Parameter;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;

class WebDriverPageObjectFactoryCallbacksTests {
  private static class FirstPageObject extends WebDriverPageObject<FirstPageObject> {}

  private static class SecondPageObject extends WebDriverPageObject<SecondPageObject> {}

  @Test
  void afterEach() {
    WebDriverPageObjectFactory factory =
        mock(WebDriverPageObjectFactory.class, withSettings().useConstructor(null, null));

    WebDriverPageObjectFactoryCallbacks callbacks =
        new WebDriverPageObjectFactoryCallbacks(null, factory);

    callbacks.afterEach(null);

    verify(factory, only()).shutdown();
  }

  @Test
  void supportsParameterTrue() {
    Set<Class<? extends WebDriverPageObject<?>>> pageObjectClassSet =
        Set.of(FirstPageObject.class, SecondPageObject.class);

    ParameterContext parameterContext = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(parameterContext.getParameter()).thenReturn(parameter);
    doReturn(FirstPageObject.class).when(parameter).getType();

    WebDriverPageObjectFactoryCallbacks callbacks =
        new WebDriverPageObjectFactoryCallbacks(pageObjectClassSet, null);

    assertTrue(callbacks.supportsParameter(parameterContext, null));
  }

  @Test
  void supportsParameterFalseEmptySet() {
    Set<Class<? extends WebDriverPageObject<?>>> pageObjectClassSet = Set.of();

    ParameterContext parameterContext = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(parameterContext.getParameter()).thenReturn(parameter);
    doReturn(FirstPageObject.class).when(parameter).getType();

    WebDriverPageObjectFactoryCallbacks callbacks =
        new WebDriverPageObjectFactoryCallbacks(pageObjectClassSet, null);

    assertFalse(callbacks.supportsParameter(parameterContext, null));
  }

  @Test
  void supportsParameterFalse() {
    Set<Class<? extends WebDriverPageObject<?>>> pageObjectClassSet =
        Set.of(SecondPageObject.class);

    ParameterContext parameterContext = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(parameterContext.getParameter()).thenReturn(parameter);
    doReturn(FirstPageObject.class).when(parameter).getType();

    WebDriverPageObjectFactoryCallbacks callbacks =
        new WebDriverPageObjectFactoryCallbacks(pageObjectClassSet, null);

    assertFalse(callbacks.supportsParameter(parameterContext, null));
  }

  @Test
  void resolveParameter() {
    // Fixture setup
    WebDriverPageObjectFactory factory =
        mock(WebDriverPageObjectFactory.class, withSettings().useConstructor(null, null));

    ParameterContext parameterContext = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(parameterContext.getParameter()).thenReturn(parameter);
    doReturn(FirstPageObject.class).when(parameter).getType();

    WebDriverPageObjectFactoryCallbacks callbacks =
        new WebDriverPageObjectFactoryCallbacks(null, factory);

    // Executing SUT
    callbacks.resolveParameter(parameterContext, null);

    verify(factory, only()).createPageObject(FirstPageObject.class);
  }

  @Test
  void resolveParameterFail() {
    // Fixture setup
    WebDriverPageObjectFactory factory = new WebDriverPageObjectFactory(null, null);

    ParameterContext parameterContext = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(parameterContext.getParameter()).thenReturn(parameter);
    doReturn(Integer.class).when(parameter).getType();

    WebDriverPageObjectFactoryCallbacks callbacks =
        new WebDriverPageObjectFactoryCallbacks(null, factory);

    assertThrows(
        WebDriverPageObjectFactory.PageNotInitializedException.class,
        () -> callbacks.resolveParameter(parameterContext, null));
  }

  @Test
  void handleTestExecutionException() {
    class FatalException extends RuntimeException {}

    // Fixture setup
    byte[] screenshot = new byte[] {1, 2, 3, 4, 5};

    WebDriverPageObjectFactory factory = mock(WebDriverPageObjectFactory.class);
    when(factory.makeScreenshot()).thenReturn(screenshot);

    AllureLifecycle lifecycle = mock(AllureLifecycle.class);

    WebDriverPageObjectFactoryCallbacks callbacks =
        spy(new WebDriverPageObjectFactoryCallbacks(null, factory));

    when(callbacks.lifecycle()).thenReturn(lifecycle);

    FatalException exception = new FatalException();

    // Executing SUT
    assertThrows(
        FatalException.class, () -> callbacks.handleTestExecutionException(null, exception));

    verify(factory, only()).makeScreenshot();
    verify(lifecycle, only()).addAttachment("Screenshot", "image/png", "", screenshot);
  }
}
