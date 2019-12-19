package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

final class MessagesStepRepositoryTests {
  private static class MessagesStepRepositoryImpl
      implements MessagesStepRepository<MessagesStepRepositoryImpl> {}

  private final MessagesStepRepository<?> messagesStepRepository = new MessagesStepRepositoryImpl();

  @Test
  void errorIsDisplayedTest() {
    assertThrows(
        NotImplementedException.class, () -> messagesStepRepository.errorIsDisplayed("blah"));
  }

  @Test
  void errorIsDisplayedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> messagesStepRepository.errorIsDisplayed(List.of("blah", "blah")));
  }

  @Test
  void textIsDisplayedTest() {
    assertThrows(
        NotImplementedException.class, () -> messagesStepRepository.textIsDisplayed("blah"));
  }

  @Test
  void textIsDisplayedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> messagesStepRepository.textIsDisplayed(List.of("blah", "blah")));
  }

  @Test
  void warningIsDisplayedTest() {
    assertThrows(
        NotImplementedException.class, () -> messagesStepRepository.warningIsDisplayed("blah"));
  }

  @Test
  void warningIsDisplayedListTest() {
    assertThrows(
        NotImplementedException.class,
        () -> messagesStepRepository.warningIsDisplayed(List.of("blah", "blah")));
  }
}
