package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;
import org.junit.jupiter.api.Test;

final class TableSortingStepRepositoryTests {
  private static class TableSortingStepRepositoryImpl
      implements TableSortingStepRepository<TableSortingStepRepositoryImpl> {}

  private final TableSortingStepRepository<?> tableSortingStepRepository =
      new TableSortingStepRepositoryImpl();

  @Test
  void checkColumnSortedTest() {
    assertThrows(
        NotImplementedException.class,
        () ->
            tableSortingStepRepository.checkColumnSorted(
                "blah", TableSortingStepRepository.Sorting.ASCENDING, Comparator.naturalOrder()));
  }

  @Test
  void checkFilterContainsValueMapTest() {
    assertThrows(
        NotImplementedException.class,
        () ->
            tableSortingStepRepository.checkDateColumnSorted(
                "blah", TableSortingStepRepository.Sorting.ASCENDING));
  }

  @Test
  void checkDateColumnSortedComparatorTest() {
    assertThrows(
        NotImplementedException.class,
        () ->
            tableSortingStepRepository.checkDateColumnSorted(
                "blah", TableSortingStepRepository.Sorting.ASCENDING, null, null));
  }
}
