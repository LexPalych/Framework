package com.github.lexpalych.extensions.allure.rest.assured;

import static java.util.stream.Collectors.toMap;

import com.github.lexpalych.extensions.junit5.allure.steps.StepWrapperSteps;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.util.Map;

public final class ApiExtractingSteps extends StepWrapperSteps<ApiExtractingSteps> {
  private final Response response;

  ApiExtractingSteps(Response response) {
    this.response = response;
  }

  @Step("Сохранение {jsonPath} как {key}")
  public <T> ApiExtractingSteps saveBodyJsonPath(String jsonPath, String key, Map<String, T> map) {
    T value = response.getBody().jsonPath().get(jsonPath);
    map.put(key, value);
    return this;
  }

  @Step("Сохранение полей с соответствующими ключами {pathsAndKeys}")
  public <T> ApiExtractingSteps saveBodyJsonPath(Map<String, String> pathsAndKeys, Map<String, T> map) {
    Map<String, T> values =
        pathsAndKeys.entrySet().stream()
            .collect(
                toMap(
                    Map.Entry::getValue,
                    entry -> {
                      String expression = entry.getKey();
                      return response.getBody().jsonPath().get(expression);
                    }));

    map.putAll(values);
    return this;
  }

  @Step("Сохранение заголовка {header} как {key}")
  public <T> ApiExtractingSteps saveHeader(String header, String key, Map<String, String> map) {
    String value = response.getHeader(header);
    map.put(key, value);
    return this;
  }

  @Step("Сохранение заголовков как соответствующие им ключи {pathsAndKeys}")
  public <T> ApiExtractingSteps saveHeader(Map<String, String> headersAndKeys, Map<String, String> map) {
    Map<String, String> values = headersAndKeys
            .entrySet()
            .stream()
            .collect(toMap(Map.Entry::getValue, entry -> response.getHeader(entry.getKey())));

    map.putAll(values);
    return this;
  }

  public ApiRequestSteps next() {
    return ApiRequestSteps.apiRequest();
  }
}
