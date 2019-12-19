package com.github.lexpalych.junit5.allure.steps.steprepositories;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

final class WebStepRepositoryTests {
  private static class WebStepRepositoryImpl implements WebStepRepository<WebStepRepositoryImpl> {}

  private final WebStepRepository<?> webStepRepository = new WebStepRepositoryImpl();

  @Test
  void openUrlTest() {
    assertThrows(NotImplementedException.class, () -> webStepRepository.openUrl("blah"));
  }

  @Test
  void checkFormIsDisplayedTest() {
    assertThrows(
        NotImplementedException.class, () -> webStepRepository.checkFormIsDisplayed("blah"));
  }
}
