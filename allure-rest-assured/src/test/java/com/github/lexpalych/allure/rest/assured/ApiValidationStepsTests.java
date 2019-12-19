package com.github.lexpalych.allure.rest.assured;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

class ApiValidationStepsTests {
  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void statusCodePass(ApiValidationSteps apiValidationSteps, Response response) {
    Mockito.when(response.statusCode()).thenReturn(SC_OK);
    assertDoesNotThrow(() -> apiValidationSteps.statusCode(SC_OK));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void statusCodeFail(ApiValidationSteps apiValidationSteps, Response response) {
    Mockito.when(response.statusCode()).thenReturn(SC_BAD_REQUEST);
    assertThrows(AssertionError.class, () -> apiValidationSteps.statusCode(SC_OK));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void contentTypePass(ApiValidationSteps apiValidationSteps, Response response) {
    String contentType = ContentType.JSON.toString();
    Mockito.when(response.getContentType()).thenReturn(contentType);
    assertDoesNotThrow(() -> apiValidationSteps.contentType(ContentType.JSON));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void contentTypeFail(ApiValidationSteps apiValidationSteps, Response response) {
    String contentType = ContentType.XML.toString();
    Mockito.when(response.getContentType()).thenReturn(contentType);
    assertThrows(AssertionError.class, () -> apiValidationSteps.contentType(ContentType.JSON));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertEqualsJsonPass(ApiValidationSteps apiValidationSteps, Response response) {
    String jsonPath = "some.field";
    String value = "value";

    Mockito.when(response.getBody().jsonPath().get(jsonPath)).thenReturn(value);
    assertDoesNotThrow(() -> apiValidationSteps.assertEqualsJson(jsonPath, value));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertEqualsJsonFail(ApiValidationSteps apiValidationSteps, Response response) {
    String jsonPath = "some.field";
    String value = "value";
    String wrongValue = "iAmSoWrong";

    Mockito.when(response.getBody().jsonPath().get(jsonPath)).thenReturn(wrongValue);
    assertThrows(AssertionError.class, () -> apiValidationSteps.assertEqualsJson(jsonPath, value));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertContainsStringJsonPass(ApiValidationSteps apiValidationSteps, Response response) {
    Map<String, String> pathsAndValuesToContain =
        Map.of(
            "path1", "value1",
            "path2", "value2",
            "path3", "value3");

    Map<String, String> pathsAndActualValues =
        Map.of(
            "path1", "blah-value1-blah",
            "path2", "blah-value2-blah",
            "path3", "blah-value3-blah");

    setJsonPathsAndValues(response, pathsAndActualValues);

    assertDoesNotThrow(() -> apiValidationSteps.assertContainsStringJson(pathsAndValuesToContain));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertContainsStringJsonFail(ApiValidationSteps apiValidationSteps, Response response) {
    Map<String, String> pathsAndValuesToContain =
        Map.of(
            "path1", "value1",
            "path2", "value2",
            "path3", "value3");

    Map<String, String> pathsAndActualValues =
        Map.of(
            "path1", "blah-value1-blah",
            "path2", "blah-blah",
            "path3", "blah-value3-blah");

    setJsonPathsAndValues(response, pathsAndActualValues);

    assertThrows(
        AssertionError.class,
        () -> apiValidationSteps.assertContainsStringJson(pathsAndValuesToContain));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertNullJsonPass(ApiValidationSteps apiValidationSteps) {
    assertDoesNotThrow(() -> apiValidationSteps.assertNullJson("somePath"));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertNullJsonFail(ApiValidationSteps apiValidationSteps, Response response) {
    String path = "somePath";
    String value = "lul";

    Mockito.when(response.getBody().jsonPath().get(path)).thenReturn(value);

    assertThrows(AssertionError.class, () -> apiValidationSteps.assertNullJson(path));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertNotNullJsonPass(ApiValidationSteps apiValidationSteps, Response response) {
    Map<String, String> values =
        Map.of(
            "path1", "value1",
            "path2", "value2",
            "path3", "value3");

    List<String> paths = new ArrayList<>(values.keySet());

    setJsonPathsAndValues(response, values);

    assertDoesNotThrow(() -> apiValidationSteps.assertNotNullJson(paths));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void assertNotNullJsonFail(ApiValidationSteps apiValidationSteps, Response response) {
    Map<String, String> values = new HashMap<>();

    values.put("path1", "value1");
    values.put("path2", null);
    values.put("path3", "value3");

    setJsonPathsAndValues(response, values);

    List<String> paths = new ArrayList<>(values.keySet());

    assertThrows(AssertionError.class, () -> apiValidationSteps.assertNotNullJson(paths));
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void next(ApiValidationSteps apiValidationSteps) {
    Assertions.assertDoesNotThrow(apiValidationSteps::next);
  }

  @ParameterizedTest
  @MethodSource("stepsAndResponse")
  void extract(ApiValidationSteps apiValidationSteps) {
    Assertions.assertDoesNotThrow(apiValidationSteps::extract);
  }

  private static Stream<Arguments> stepsAndResponse() {
    Response response = mock(Response.class, withSettings().defaultAnswer(RETURNS_DEEP_STUBS));
    ApiValidationSteps validationSteps = new ApiValidationSteps(response);
    return Stream.of(Arguments.of(validationSteps, response));
  }

  private void setJsonPathsAndValues(Response response, Map<String, String> values) {
    values.forEach(
        (path, value) -> Mockito.when(response.getBody().jsonPath().get(path)).thenReturn(value));
  }
}
