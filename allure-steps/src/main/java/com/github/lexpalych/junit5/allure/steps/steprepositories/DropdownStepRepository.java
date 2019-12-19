package com.github.lexpalych.junit5.allure.steps.steprepositories;

import com.github.lexpalych.junit5.allure.steps.StepRepository;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;

@StepRepository
public interface DropdownStepRepository<T> {
  @Step("Выбор {value} из выпадающего списка {name}")
  default T selectFromDropdownList(final String name, final String value) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Выбор соответствующих значений из выпадающего списка {values}")
  default T selectFromDropdownList(final Map<String, String> values) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Очистка выпадающего списка {name}")
  default T clearDropdownList(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Очистка выпадающих списков {names}")
  default T clearDropdownList(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }
}
