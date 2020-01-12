package com.github.lexpalych.extensions.junit5.allure.steps.steprepositories;

import com.github.lexpalych.extensions.junit5.allure.steps.StepRepository;
import io.qameta.allure.Step;
import java.util.List;

@StepRepository
public interface MessagesStepRepository<T> {
  @Step("Проверка отображения предупреждения {warning}")
  default T warningIsDisplayed(final String warning) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения предупреждений {warnings}")
  default T warningIsDisplayed(final List<String> warnings) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения текста {text}")
  default T textIsDisplayed(final String text) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения текста {texts}")
  default T textIsDisplayed(final List<String> texts) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения ошибки {error}")
  default T errorIsDisplayed(final String error) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения ошибок {errors}")
  default T errorIsDisplayed(final List<String> errors) {
    throw new NotImplementedException("Реализуйте метод");
  }
}
