package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class FieldStepRepositoryTests {
  private static class FieldStepRepositoryImpl
      implements FieldStepRepository<FieldStepRepositoryImpl> {}

  private final FieldStepRepository<?> fieldStepRepository = new FieldStepRepositoryImpl();

  @Test
  void checkFieldContainsValueTest() {
    assertThrows(
        NotImplementedException.class,
        () -> fieldStepRepository.checkFieldContainsValue("blah", "blah"));
  }

  @Test
  void checkFieldContainsValueMapTest() {
    assertThrows(
        NotImplementedException.class,
        () -> fieldStepRepository.checkFieldContainsValue(Map.of("blah", "blah")));
  }

  @Test
  void checkFieldDoesntContainValueTest() {
    assertThrows(
        NotImplementedException.class,
        () -> fieldStepRepository.checkFieldDoesntContainValue("blah", "blah"));
  }

  @Test
  void checkFieldDoesntContainValueMapTest() {
    assertThrows(
        NotImplementedException.class,
        () -> fieldStepRepository.checkFieldDoesntContainValue(Map.of("blah", "blah")));
  }

  @Test
  void checkFieldIsEmptyTest() {
    assertThrows(
        NotImplementedException.class, () -> fieldStepRepository.checkFieldIsEmpty("blah"));
  }

  @Test
  void checkFieldIsEmptyListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> fieldStepRepository.checkFieldIsEmpty(List.of("blah", "blah")));
  }

  @Test
  void checkFieldIsNotEmptyTest() {
    assertThrows(
        NotImplementedException.class, () -> fieldStepRepository.checkFieldIsNotEmpty("blah"));
  }

  @Test
  void checkFieldIsNotEmptyListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> fieldStepRepository.checkFieldIsNotEmpty(List.of("blah", "blah")));
  }

  @Test
  void clearFieldTest() {
    assertThrows(NotImplementedException.class, () -> fieldStepRepository.clearField("blah"));
  }

  @Test
  void fillFieldTest() {
    assertThrows(
        NotImplementedException.class, () -> fieldStepRepository.fillField("blah", "blah"));
  }

  @Test
  void fillFieldMapTest() {
    assertThrows(
        NotImplementedException.class, () -> fieldStepRepository.fillField(Map.of("blah", "blah")));
  }

  @Test
  void putFieldValueInMapTest() {
    assertThrows(
        NotImplementedException.class,
        () -> fieldStepRepository.putFieldValueInMap("blah", "blah", new HashMap<>()));
  }
}
