package com.github.lexpalych.junit5.extensions;

import java.util.Objects;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public final class GenericParameterResolver<T> implements ParameterResolver {
  private final T value;
  private final Class<T> parameterType;
  private final String name;

  GenericParameterResolver(Class<T> parameterType, T value) {
    this.parameterType = parameterType;
    this.value = value;
    this.name = null;
  }

  GenericParameterResolver(Class<T> parameterType, T value, String name) {
    this.parameterType = parameterType;
    this.value = value;
    this.name = name;
  }

  @Override
  public boolean supportsParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    if (parameterContext.getParameter().getType() == parameterType) {
      if (name != null) {
        return parameterContext.getParameter().getName().equals(name);
      }

      return true;
    }

    return false;
  }

  @Override
  public Object resolveParameter(
      ParameterContext parameterContext, ExtensionContext extensionContext)
      throws ParameterResolutionException {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GenericParameterResolver<?> that = (GenericParameterResolver<?>) o;
    return Objects.equals(value, that.value)
        && Objects.equals(parameterType, that.parameterType)
        && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, parameterType, name);
  }
}
