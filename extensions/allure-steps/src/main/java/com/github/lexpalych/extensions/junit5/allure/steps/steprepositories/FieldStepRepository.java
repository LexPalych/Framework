package com.github.lexpalych.extensions.junit5.allure.steps.steprepositories;

import com.github.lexpalych.extensions.junit5.allure.steps.StepRepository;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;

@StepRepository
public interface FieldStepRepository<T> {
  @Step("Проверка что поле {name} не пустое")
  default T checkFieldIsNotEmpty(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что поля {names} не пустые")
  default T checkFieldIsNotEmpty(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка пустоты поля {name}")
  default T checkFieldIsEmpty(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка пустоты полей {names}")
  default T checkFieldIsEmpty(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Запоминаю {name} как {key}")
  default T putFieldValueInMap(final String name, final String key, final Map<String, String> map) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка содержания полем {name} значения {expectedValue}")
  default T checkFieldContainsValue(final String name, final String expectedValue) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка содержания полями соответствующих значений {values}")
  default T checkFieldContainsValue(final Map<String, String> values) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что поле {name} не содержит значения {unexpectedValue}")
  default T checkFieldDoesntContainValue(final String name, final String unexpectedValue) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что поля не содержат соответствующих значений {values}")
  default T checkFieldDoesntContainValue(final Map<String, String> values) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Заполнение поля {name} значением {value}")
  default T fillField(final String name, final String value) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Заполнение полей соответствующими значениями {values}")
  default T fillField(final Map<String, String> values) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Очистка поля {name}")
  default T clearField(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }
}
