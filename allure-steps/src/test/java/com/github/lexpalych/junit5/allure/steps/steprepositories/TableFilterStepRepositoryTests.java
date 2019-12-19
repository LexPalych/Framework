package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class TableFilterStepRepositoryTests {
  private static class TableFilterStepRepositoryImpl
      implements TableFilterStepRepository<TableFilterStepRepositoryImpl> {}

  private final TableFilterStepRepository<?> tableFilterStepRepository =
      new TableFilterStepRepositoryImpl();

  @Test
  void checkFilterContainsValueTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.checkFilterContainsValue("blah", "blah"));
  }

  @Test
  void checkFilterContainsValueMapTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.checkFilterContainsValue(Map.of("blah", "blah")));
  }

  @Test
  void checkFilterIsEmptyTest() {
    assertThrows(
        NotImplementedException.class, () -> tableFilterStepRepository.checkFilterIsEmpty("blah"));
  }

  @Test
  void checkFilterIsEmptyListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.checkFilterIsEmpty(List.of("blah", "blah")));
  }

  @Test
  void clearFilterTest() {
    assertThrows(
        NotImplementedException.class, () -> tableFilterStepRepository.clearFilter("blah"));
  }

  @Test
  void clearFilterListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.clearFilter(List.of("blah", "blah")));
  }

  @Test
  void deselectFilterTest() {
    assertThrows(
        NotImplementedException.class, () -> tableFilterStepRepository.deselectFilter("blah"));
  }

  @Test
  void deselectFilterListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.deselectFilter(List.of("blah", "blah")));
  }

  @Test
  void fillFilterTest() {
    assertThrows(
        NotImplementedException.class, () -> tableFilterStepRepository.fillFilter("blah", "blah"));
  }

  @Test
  void fillFilterMapTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.fillFilter(Map.of("blah", "blah")));
  }

  @Test
  void selectFromFilterTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.selectFromFilter("blah", "blah"));
  }

  @Test
  void selectFromFilterMapTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableFilterStepRepository.selectFromFilter(Map.of("blah", "blah")));
  }
}
