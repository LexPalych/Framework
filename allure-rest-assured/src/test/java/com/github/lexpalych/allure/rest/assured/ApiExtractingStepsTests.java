package com.github.lexpalych.allure.rest.assured;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;

class ApiExtractingStepsTests {
  @Test
  void saveBodyJsonPath() {
    // Fixture setup
    String jsonPathExpression = "path";
    String key = "key";
    String value = "eatSleepRepeat";

    Map<String, String> map = new HashMap<>();

    Response response =
        mock(Response.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));

    when(response.getBody().jsonPath().get(jsonPathExpression)).thenReturn(value);

    ApiExtractingSteps steps = new ApiExtractingSteps(response);

    // Executing SUT
    steps.saveBodyJsonPath(jsonPathExpression, key, map);

    assertEquals(value, map.get(key));
  }

  @Test
  void saveBodyJsonPathMap() {
    // Fixture setup
    Map<String, String> pathsAndKeys =
        Map.of(
            "expression1", "key1",
            "expression2", "key2",
            "expression3", "key3");

    Map<String, String> values =
        Map.of(
            "expression1", "value1",
            "expression2", "value2",
            "expression3", "value3");

    Map<String, String> expected =
        Map.of(
            "key1", "value1",
            "key2", "value2",
            "key3", "value3");

    Map<String, String> map = new HashMap<>();

    Response response =
        mock(Response.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));

    values.forEach(
        (expression, value) ->
            when(response.getBody().jsonPath().get(expression)).thenReturn(value));

    ApiExtractingSteps steps = new ApiExtractingSteps(response);

    // Executing SUT
    steps.saveBodyJsonPath(pathsAndKeys, map);

    assertEquals(expected, map);
  }

  @Test
  void saveHeader() {
    // Fixture setup
    String header = "authorization";
    String key = "authorization";
    String value = "password";

    Map<String, String> map = new HashMap<>();

    Response response = mock(Response.class);

    when(response.getHeader(header)).thenReturn(value);

    ApiExtractingSteps steps = new ApiExtractingSteps(response);

    // Executing SUT
    steps.saveHeader(header, key, map);

    assertEquals(value, map.get(key));
  }

  @Test
  void saveHeaderMap() {
    // Fixture setup
    Map<String, String> headersAndKeys =
        Map.of(
            "header1", "key1",
            "header2", "key2",
            "header3", "key3");

    Map<String, String> values =
        Map.of(
            "header1", "value1",
            "header2", "value2",
            "header3", "value3");

    Map<String, String> expected =
        Map.of(
            "key1", "value1",
            "key2", "value2",
            "key3", "value3");

    Map<String, String> map = new HashMap<>();

    Response response = mock(Response.class);

    values.forEach((header, value) -> when(response.getHeader(header)).thenReturn(value));

    ApiExtractingSteps steps = new ApiExtractingSteps(response);

    // Executing SUT
    steps.saveHeader(headersAndKeys, map);

    assertEquals(expected, map);
  }

  @Test
  void next() {
    Assertions.assertDoesNotThrow(() -> new ApiExtractingSteps(null).next());
  }
}
