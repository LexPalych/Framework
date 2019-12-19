package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

final class ButtonStepRepositoryTests {
  private static class ButtonStepRepositoryImpl
      implements ButtonStepRepository<ButtonStepRepositoryImpl> {}

  private final ButtonStepRepository<?> buttonStepRepository = new ButtonStepRepositoryImpl();

  @Test
  void clickButtonTest() {
    assertThrows(NotImplementedException.class, () -> buttonStepRepository.clickButton("blah"));
  }

  @Test
  void clickButtonListTest() {
    assertThrows(
        NotImplementedException.class, () -> buttonStepRepository.clickButton(List.of("blah")));
  }

  @Test
  void checkButtonIsDisplayedTest() {
    assertThrows(
        NotImplementedException.class, () -> buttonStepRepository.checkButtonIsDisplayed("blah"));
  }

  @Test
  void checkButtonIsDisplayedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> buttonStepRepository.checkButtonIsDisplayed(List.of("blah")));
  }
}
