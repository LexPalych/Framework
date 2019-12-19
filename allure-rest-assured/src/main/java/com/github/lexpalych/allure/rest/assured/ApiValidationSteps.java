package com.github.lexpalych.allure.rest.assured;

import static com.github.lexpalych.allure.rest.assured.ApiRequestSteps.apiRequest;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

import com.github.lexpalych.junit5.allure.steps.StepWrapperSteps;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.function.Executable;

public final class ApiValidationSteps extends StepWrapperSteps<ApiValidationSteps> {
  private final Response response;

  ApiValidationSteps(Response response) {
    this.response = response;
  }

  @Step("Проверка кода {status}")
  public ApiValidationSteps statusCode(int status) {
    assertEquals(status, response.statusCode());
    return this;
  }

  @Step("Проверка Content-Type {contentType}")
  public ApiValidationSteps contentType(ContentType contentType) {
    assertEquals(contentType, ContentType.fromContentType(response.getContentType()));
    return this;
  }

  @Step("Проверка содержания в {jsonPath} значения {expected}")
  public <T> ApiValidationSteps assertEqualsJson(String jsonPath, T expected) {
    T actual = response.getBody().jsonPath().get(jsonPath);
    assertEquals(expected, actual);
    return this;
  }

  @Step("Проверка содержания полями соответствующих значений {values}")
  public ApiValidationSteps assertContainsStringJson(Map<String, String> values) {
    List<Executable> executables =
        values.entrySet().stream()
            .map(
                entry -> {
                  String expected = entry.getValue();
                  String actual = response.getBody().jsonPath().get(entry.getKey());
                  return (Executable)
                      () ->
                          assertTrue(
                              actual.contains(expected), actual + " не содержит " + expected);
                })
            .collect(toList());

    assertAll(executables);
    return this;
  }

  @Step("Проверка пустоты {jsonPath}")
  public ApiValidationSteps assertNullJson(String jsonPath) {
    assertNull(response.getBody().jsonPath().get(jsonPath));
    return this;
  }

  @Step("Проверка не пустые {jsonPaths}")
  public ApiValidationSteps assertNotNullJson(List<String> jsonPaths) {
    List<Executable> executables =
        jsonPaths.stream()
            .map(
                jsonPath ->
                    (Executable) () -> assertNotNull(response.getBody().jsonPath().get(jsonPath)))
            .collect(toList());

    assertAll(executables);
    return this;
  }

  public ApiRequestSteps next() {
    return apiRequest();
  }

  public ApiExtractingSteps extract() {
    return new ApiExtractingSteps(response);
  }
}
