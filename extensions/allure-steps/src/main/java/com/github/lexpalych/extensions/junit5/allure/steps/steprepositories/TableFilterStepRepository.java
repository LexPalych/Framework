package com.github.lexpalych.extensions.junit5.allure.steps.steprepositories;

import com.github.lexpalych.extensions.junit5.allure.steps.StepRepository;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;

@StepRepository
public interface TableFilterStepRepository<T> {
  @Step("Выбор значения {value} из фильтров {name}")
  default T selectFromFilter(final String name, final String value) {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Выбор значений из соответствуюших фильтров {values}")
  default T selectFromFilter(final Map<String, String> values) {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Заполнение фильтра {name} значением {value}")
  default T fillFilter(final String name, final String value) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Заполнение фильтров соответствующими значениями {values}")
  default T fillFilter(final Map<String, String> values) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Очистка фильтра {name}")
  default T clearFilter(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Очистка фильтров {names}")
  default T clearFilter(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Очистка фильтра-выпадающего списка {name}")
  default T deselectFilter(final String name) {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Очистка фильтров-выпадающих списков {names}")
  default T deselectFilter(final List<String> names) {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Проверка содержания фильтром {name} значения {value}")
  default T checkFilterContainsValue(final String name, final String value) {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Проверка содержания фильтрами соответствующих значений {values}")
  default T checkFilterContainsValue(final Map<String, String> values) {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Проверка пустоты фильтра {name}")
  default T checkFilterIsEmpty(final String name) {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Проверка пустоты фильтров {names}")
  default T checkFilterIsEmpty(final List<String> names) {
    throw new NotImplementedException("Не реализовано");
  }
}
