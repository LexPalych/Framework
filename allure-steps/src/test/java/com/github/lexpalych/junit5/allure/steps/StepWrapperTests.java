package com.github.lexpalych.junit5.allure.steps;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import java.util.UUID;
import org.junit.jupiter.api.Test;

final class StepWrapperTests {
  @Test
  void nullNameThrows() {
    StepWrapper stepWrapper = new StepWrapper();
    assertThrows(NullPointerException.class, () -> stepWrapper.startStep(null));
  }

  @Test
  void stopWhenNotStartedDoesNotThrow() {
    StepWrapper stepWrapper = new StepWrapper();
    assertDoesNotThrow(() -> stepWrapper.stopStep(Status.PASSED));
  }

  @Test
  void startStepTest() {
    StepWrapper stepWrapper = spy(StepWrapper.class);
    AllureLifecycle allureLifecycle = mock(AllureLifecycle.class);

    when(stepWrapper.lifecycle()).thenReturn(allureLifecycle);

    stepWrapper.startStep(getStepName());

    verify(allureLifecycle, only()).startStep(anyString(), any());
  }

  @Test
  void stoppedStepTest() {
    StepWrapper stepWrapper = spy(StepWrapper.class);

    AllureLifecycle allureLifecycle = mock(AllureLifecycle.class);

    when(stepWrapper.lifecycle()).thenReturn(allureLifecycle);

    Status status = Status.PASSED;

    stepWrapper.startStep(getStepName());
    stepWrapper.stopStep(status);

    verify(allureLifecycle, times(1)).startStep(anyString(), any());
    verify(allureLifecycle, times(1)).updateStep(anyString(), any());
    verify(allureLifecycle, times(1)).stopStep(anyString());
  }

  private String getStepName() {
    return UUID.randomUUID().toString();
  }
}
