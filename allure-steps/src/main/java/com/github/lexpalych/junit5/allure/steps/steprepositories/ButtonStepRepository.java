package com.github.lexpalych.junit5.allure.steps.steprepositories;

import com.github.lexpalych.junit5.allure.steps.StepRepository;
import io.qameta.allure.Step;
import java.util.List;

@StepRepository
public interface ButtonStepRepository<T> {
  @Step("Нажатие кнопки {name}")
  default T clickButton(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Нажатие кнопок {names}")
  default T clickButton(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения кнопки {name}")
  default T checkButtonIsDisplayed(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения кнопок {names}")
  default T checkButtonIsDisplayed(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }
}
