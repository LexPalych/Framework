package com.github.lexpalych.allure.rest.assured;

import static io.restassured.RestAssured.given;

import com.github.lexpalych.junit5.allure.steps.StepWrapperSteps;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import java.util.List;
import java.util.Map;

public final class ApiRequestSteps extends StepWrapperSteps<ApiRequestSteps> {
  private RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

  private ApiRequestSteps() {}

  public static ApiRequestSteps apiRequest() {
    return new ApiRequestSteps();
  }

  @Step("Добавление заголовка {name}={value}")
  public ApiRequestSteps addHeader(String name, String value) {
    requestSpecBuilder.addHeader(name, value);
    return this;
  }

  @Step("Добавление заголовков {headers}")
  public ApiRequestSteps addHeader(Map<String, String> headers) {
    requestSpecBuilder.addHeaders(headers);
    return this;
  }

  @Step("Добавление cookie файла")
  public ApiRequestSteps addCookie(String cookieName) {
    requestSpecBuilder.addCookie(cookieName);
    return this;
  }

  @Step("Добавление параметра {name}={value}")
  public ApiRequestSteps addParam(String name, String value) {
    requestSpecBuilder.addParam(name, value);
    return this;
  }

  @Step("Добавление списка параметров {name}={paramList}")
  public ApiRequestSteps addParam(String name, List<String> paramList) {
    requestSpecBuilder.addParam(name, paramList);
    return this;
  }

  @Step("Добавление мапы параметров {paramMap}")
  public ApiRequestSteps addParam(Map<String, String> paramMap) {
    requestSpecBuilder.addParams(paramMap);
    return this;
  }

  @Step("Добавление тела запроса")
  public ApiRequestSteps setBody(Object body) {
    requestSpecBuilder.setBody(body);
    return this;
  }

  @Step("Отправка PUT запроса на {url}")
  public ApiValidationSteps put(String url) {
    return new ApiValidationSteps(given(requestSpecBuilder.build()).get(url));
  }

  @Step("Отправка GET запроса на {url}")
  public ApiValidationSteps get(String url) {
    return new ApiValidationSteps(given(requestSpecBuilder.build()).get(url));
  }

  @Step("Отправка DELETE запроса на {url}")
  public ApiValidationSteps delete(String url) {
    return new ApiValidationSteps(given(requestSpecBuilder.build()).delete(url));
  }

  @Step("Отправка HEAD запроса на {url}")
  public ApiValidationSteps head(String url) {
    return new ApiValidationSteps(given(requestSpecBuilder.build()).head(url));
  }

  @Step("Отправка PATCH запроса на {url}")
  public ApiValidationSteps patch(String url) {
    return new ApiValidationSteps(given(requestSpecBuilder.build()).patch(url));
  }

  /** Only for testing */
  ApiRequestSteps setRequestSpecBuilder(RequestSpecBuilder requestSpecBuilder) {
    this.requestSpecBuilder = requestSpecBuilder;
    return this;
  }
}
