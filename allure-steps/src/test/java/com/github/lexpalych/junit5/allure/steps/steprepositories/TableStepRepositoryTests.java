package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

final class TableStepRepositoryTests {
  private static class TableStepRepositoryImpl
      implements TableStepRepository<TableStepRepositoryImpl> {}

  private final TableStepRepository<?> tableStepRepository = new TableStepRepositoryImpl();

  @Test
  void checkCellContainsValueTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableStepRepository.checkCellContainsValue("blah", "blah", 0));
  }

  @Test
  void checkColumnContainsValueTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableStepRepository.checkColumnContainsValue("blah", "blah"));
  }

  @Test
  void checkColumnDoesntContainValueTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableStepRepository.checkColumnDoesntContainValue("blah", "blah"));
  }

  @Test
  void checkColumnIsEmptyTest() {
    assertThrows(
        NotImplementedException.class, () -> tableStepRepository.checkColumnIsEmpty("blah"));
  }

  @Test
  void checkColumnIsNotEmptyTest() {
    assertThrows(
        NotImplementedException.class, () -> tableStepRepository.checkColumnIsNotEmpty("blah"));
  }

  @Test
  void checkColumnSizeTest() {
    assertThrows(
        NotImplementedException.class, () -> tableStepRepository.checkColumnSize("blah", 0));
  }

  @Test
  void checkTableIsEmptyTest() {
    assertThrows(NotImplementedException.class, tableStepRepository::checkTableIsEmpty);
  }

  @Test
  void clickOnEntryRowTest() {
    assertThrows(NotImplementedException.class, () -> tableStepRepository.clickOnEntry("blah", 0));
  }

  @Test
  void clickOnEntryValueTest() {
    assertThrows(
        NotImplementedException.class, () -> tableStepRepository.clickOnEntry("blah", "blah"));
  }

  @Test
  void clickOnHeaderTest() {
    assertThrows(NotImplementedException.class, () -> tableStepRepository.clickOnHeader("blah"));
  }

  @Test
  void clickOnHeaderListTest() {
    assertThrows(
        NotImplementedException.class, () -> tableStepRepository.clickOnHeader(List.of("blah")));
  }

  @Test
  void headerIsPresentTest() {
    assertThrows(NotImplementedException.class, () -> tableStepRepository.headerIsPresent("blah"));
  }

  @Test
  void headerIsPresentListTest() {
    assertThrows(
        NotImplementedException.class, () -> tableStepRepository.headerIsPresent(List.of("blah")));
  }

  @Test
  void getNonNullValueTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableStepRepository.getNonNullValue("blah", new HashMap<>()));
  }

  @Test
  void getCellValueAndPutInMapTest() {
    assertThrows(
        NotImplementedException.class,
        () -> tableStepRepository.getCellValueAndPutInMap("blah", "blah", 0, new HashMap<>()));
  }
}
