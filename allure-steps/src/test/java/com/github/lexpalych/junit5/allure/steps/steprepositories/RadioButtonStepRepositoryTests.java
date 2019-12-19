package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

final class RadioButtonStepRepositoryTests {
  private static class RadioButtonStepRepositoryImpl
      implements RadioButtonStepRepository<RadioButtonStepRepositoryImpl> {}

  private final RadioButtonStepRepository<?> radioButtonStepRepository =
      new RadioButtonStepRepositoryImpl();

  @Test
  void checkRadioButtonIsDisplayedTest() {
    assertThrows(
        NotImplementedException.class,
        () -> radioButtonStepRepository.checkRadioButtonIsDisplayed("blah"));
  }

  @Test
  void checkRadioButtonIsDisplayedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> radioButtonStepRepository.checkRadioButtonIsDisplayed(List.of("blah")));
  }

  @Test
  void checkRadioButtonIsNotSelectedTest() {
    assertThrows(
        NotImplementedException.class,
        () -> radioButtonStepRepository.checkRadioButtonIsNotSelected("blah"));
  }

  @Test
  void checkRadioButtonIsNotSelectedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> radioButtonStepRepository.checkRadioButtonIsNotSelected(List.of("blah")));
  }

  @Test
  void checkRadioButtonIsSelectedTest() {
    assertThrows(
        NotImplementedException.class,
        () -> radioButtonStepRepository.checkRadioButtonIsSelected("blah"));
  }

  @Test
  void checkRadioButtonIsSelectedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> radioButtonStepRepository.checkRadioButtonIsSelected(List.of("blah")));
  }

  @Test
  void clickRadioButtonTest() {
    assertThrows(
        NotImplementedException.class, () -> radioButtonStepRepository.clickRadioButton("blah"));
  }

  @Test
  void clickRadioButtonListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> radioButtonStepRepository.clickRadioButton(List.of("blah")));
  }
}
