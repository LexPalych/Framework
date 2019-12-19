package com.github.lexpalych.junit5.allure.steps.steprepositories;

import com.github.lexpalych.junit5.allure.steps.StepRepository;
import io.qameta.allure.Step;
import java.util.List;

@StepRepository
public interface RadioButtonStepRepository<T> {
  @Step("Нажатие радиокнопки {name}")
  default T clickRadioButton(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Нажатие радиокнопок {names}")
  default T clickRadioButton(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка выбора радиокнопки {name}")
  default T checkRadioButtonIsSelected(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка выбора радиокнопкок {names}")
  default T checkRadioButtonIsSelected(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что радиокнопка {name} не выбрана")
  default T checkRadioButtonIsNotSelected(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что радиокнопки не выбраны {names}")
  default T checkRadioButtonIsNotSelected(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения радиокнопки {name}")
  default T checkRadioButtonIsDisplayed(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка отображения радиокнопок {names}")
  default T checkRadioButtonIsDisplayed(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }
}
