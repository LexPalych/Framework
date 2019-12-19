package com.github.lexpalych.junit5.allure.steps.steprepositories;

import com.github.lexpalych.junit5.allure.steps.StepRepository;
import io.qameta.allure.Step;
import java.util.List;
import java.util.Map;

@StepRepository
public interface TableStepRepository<T> {
  @Step("Клик на заголовок {name}")
  default T clickOnHeader(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Клик на заголовки {names}")
  default T clickOnHeader(final List<String> names) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка заголовка {name}")
  default T headerIsPresent(final String name) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка заголовков {headers}")
  default T headerIsPresent(final List<String> headers) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка пустоты таблицы")
  default T checkTableIsEmpty() {
    throw new NotImplementedException("Не реализовано");
  }

  @Step("Нажатие на ячейку {column} в строке {row}")
  default T clickOnEntry(final String column, final int row) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Нажатие на ячейку {column} со значением {value}")
  default T clickOnEntry(final String column, final String value) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Запоминаю значение колонки {column} в строке {row} как {key}")
  default T getCellValueAndPutInMap(
      final String column, final String key, final int row, final Map<String, String> map) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Получение ненулевого значения колонки {column}")
  default T getNonNullValue(final String column, final Map<String, String> map) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что колонка {column} содержит {value}")
  default T checkColumnContainsValue(final String column, final String value) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что колонка {column} в строке {row} содержит {value}")
  default T checkCellContainsValue(final String column, final String value, final int row) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что колонка {column} НЕ содержит {value}")
  default T checkColumnDoesntContainValue(final String column, final String value) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что колонка {column} пуста")
  default T checkColumnIsEmpty(final String column) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что колонка {column} НЕ пуста")
  default T checkColumnIsNotEmpty(final String column) {
    throw new NotImplementedException("Реализуйте метод");
  }

  @Step("Проверка что размер колонки {column} равен {size}")
  default T checkColumnSize(final String column, final int size) {
    throw new NotImplementedException("Реализуйте метод");
  }
}
