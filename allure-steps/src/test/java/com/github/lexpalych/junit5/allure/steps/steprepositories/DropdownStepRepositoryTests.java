package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

final class DropdownStepRepositoryTests {
  private static class DropdownStepRepositoryImpl
      implements DropdownStepRepository<DropdownStepRepositoryImpl> {}

  private final DropdownStepRepository<?> dropdownStepRepository = new DropdownStepRepositoryImpl();

  @Test
  void clearDropdownTest() {
    assertThrows(
        NotImplementedException.class, () -> dropdownStepRepository.clearDropdownList("blah"));
  }

  @Test
  void clearDropdownListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> dropdownStepRepository.clearDropdownList(List.of("blah")));
  }

  @Test
  void checkButtonIsDisplayedTest() {
    assertThrows(
        NotImplementedException.class,
        () -> dropdownStepRepository.selectFromDropdownList("blah", "blah-blah"));
  }

  @Test
  void checkButtonIsDisplayedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> dropdownStepRepository.selectFromDropdownList(Map.of("blah", "blah-blah")));
  }
}
