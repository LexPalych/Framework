package com.github.lexpalych.allure.rest.assured;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

import io.restassured.builder.RequestSpecBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ApiRequestStepsTests {
  @Test
  void apiRequest() {
    assertDoesNotThrow(ApiRequestSteps::apiRequest);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void addHeader(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps) {
    String name = "header";
    String value = "value";

    apiRequestSteps.addHeader(name, value);

    verify(builder, only()).addHeader(name, value);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void addHeaderMap(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps) {
    Map<String, String> headers =
        Map.of(
            "header1", "value1",
            "header2", "value2",
            "header3", "value3");

    apiRequestSteps.addHeader(headers);

    verify(builder, only()).addHeaders(headers);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void addCookie(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps) {
    String cookieName = "ILOVECOOKIES";

    apiRequestSteps.addCookie(cookieName);

    verify(builder, only()).addCookie(cookieName);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void addParam(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps) {
    String name = "parameter";
    String value = "value";

    apiRequestSteps.addParam(name, value);

    verify(builder, only()).addParam(name, value);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void addParamList(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps) {
    String name = "param";
    List<String> parameters = List.of("value1", "value2", "value3");

    apiRequestSteps.addParam(name, parameters);

    verify(builder, only()).addParam(name, parameters);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void addParamMap(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps) {
    Map<String, String> parameters =
        Map.of(
            "header1", "value1",
            "header2", "value2",
            "header3", "value3");

    apiRequestSteps.addParam(parameters);

    verify(builder, only()).addParams(parameters);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void setBody(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps) {
    Person person = new Person("Ozzy Osbourne", 71);

    apiRequestSteps.setBody(person);

    verify(builder, only()).setBody(person);
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void put(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps, String url) {
    apiRequestSteps.put(url);

    verify(builder, only()).build();
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void get(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps, String url) {
    apiRequestSteps.get(url);

    verify(builder, only()).build();
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void delete(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps, String url) {
    apiRequestSteps.delete(url);

    verify(builder, only()).build();
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void head(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps, String url) {
    apiRequestSteps.head(url);

    verify(builder, only()).build();
  }

  @ParameterizedTest
  @MethodSource("builderMockProvider")
  void patch(RequestSpecBuilder builder, ApiRequestSteps apiRequestSteps, String url) {
    apiRequestSteps.patch(url);

    verify(builder, only()).build();
  }

  private static Stream<Arguments> builderMockProvider() {
    String url = "https://google.com";

    RequestSpecBuilder builder = spy(RequestSpecBuilder.class);

    return Stream.of(
        Arguments.of(builder, ApiRequestSteps.apiRequest().setRequestSpecBuilder(builder), url));
  }

  private static class Person {
    private final String name;
    private final int age;

    Person(String name, int age) {
      this.name = name;
      this.age = age;
    }

    public String getName() {
      return name;
    }

    public int getAge() {
      return age;
    }
  }
}
