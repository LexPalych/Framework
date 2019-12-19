package com.github.lexpalych.junit5.extensions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Parameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

final class GenericParameterResolverTests {
  @Test
  void classAndValueConstructorTest() {
    Class<String> aClass = String.class;
    String value = "someNonsense";

    ParameterContext context = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(context.getParameter()).thenReturn(parameter);
    doReturn(aClass).when(parameter).getType();

    ParameterResolver resolver = new GenericParameterResolver<>(aClass, value);

    assertTrue(resolver.supportsParameter(context, null));
    assertEquals(value, resolver.resolveParameter(context, null));
  }

  @Test
  void classAndValueConstructorIncorrectParameterTypeTest() {
    Class<String> aClass = String.class;
    Class<Integer> parameterType = Integer.class;
    String value = "someNonsense";

    ParameterContext context = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(context.getParameter()).thenReturn(parameter);
    doReturn(parameterType).when(parameter).getType();

    ParameterResolver resolver = new GenericParameterResolver<>(aClass, value);

    assertFalse(resolver.supportsParameter(context, null));
    assertEquals(value, resolver.resolveParameter(context, null));
  }

  @Test
  void classAndValueAndNameConstructorTest() {
    Class<String> aClass = String.class;
    String value = "someNonsense";
    String name = "message";

    ParameterContext context = mock(ParameterContext.class);
    Parameter parameter = mock(Parameter.class);

    when(context.getParameter()).thenReturn(parameter);
    doReturn(aClass).when(parameter).getType();
    doReturn(name).when(parameter).getName();

    ParameterResolver resolver = new GenericParameterResolver<>(aClass, value, name);

    assertTrue(resolver.supportsParameter(context, null));
    assertEquals(value, resolver.resolveParameter(context, null));
  }

  @Test
  void hashCodeTest() {
    Class<String> aClass = String.class;
    String value = "someNonsense";
    String name = "message";

    assertEquals(
        new GenericParameterResolver<>(aClass, value, name).hashCode(),
        new GenericParameterResolver<>(aClass, value, name).hashCode());
  }

  @Test
  void equalsTest() {
    Class<String> aClass = String.class;
    String value = "someNonsense";
    String name = "message";

    GenericParameterResolver<String> first = new GenericParameterResolver<>(aClass, value, name);
    GenericParameterResolver<String> second = new GenericParameterResolver<>(aClass, value, name);

    assertEquals(first, second);
  }
}
