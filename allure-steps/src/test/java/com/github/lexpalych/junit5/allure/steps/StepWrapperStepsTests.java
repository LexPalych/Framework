package com.github.lexpalych.junit5.allure.steps;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import io.qameta.allure.model.Status;
import java.util.UUID;
import org.junit.jupiter.api.Test;

final class StepWrapperStepsTests {
  private static class StepWrapperStepsConcrete
      extends StepWrapperSteps<StepWrapperStepsConcrete> {}

  @Test
  void startStepEmptyStepNameThrowsNullPointerExceptionTest() {
    StepWrapperSteps<StepWrapperStepsConcrete> stepWrapperSteps = new StepWrapperStepsConcrete();
    assertThrows(NullPointerException.class, () -> stepWrapperSteps.startStep(null));
  }

  @Test
  void startStepTest() {
    StepWrapperSteps<StepWrapperStepsConcrete> stepWrapperSteps =
        spy(StepWrapperStepsConcrete.class);
    StepWrapper stepWrapper = mock(StepWrapper.class);

    when(stepWrapperSteps.stepWrapper()).thenReturn(stepWrapper);

    String stepName = getStepName();

    stepWrapperSteps.startStep(stepName);

    verify(stepWrapper, only()).startStep(stepName);
  }

  @Test
  void stepTest() {
    StepWrapperSteps<StepWrapperStepsConcrete> stepWrapperSteps =
        spy(StepWrapperStepsConcrete.class);
    StepWrapper stepWrapper = mock(StepWrapper.class);

    when(stepWrapperSteps.stepWrapper()).thenReturn(stepWrapper);

    String stepName = getStepName();

    stepWrapperSteps.step(stepName, () -> {});

    verify(stepWrapper, times(1)).startStep(stepName);
    verify(stepWrapper, times(1)).stopStep(Status.PASSED);
  }

  @Test
  void stepWithExceptionTest() {
    class VeryScaryException extends RuntimeException {}

    StepWrapperSteps<StepWrapperStepsConcrete> stepWrapperSteps =
        spy(StepWrapperStepsConcrete.class);
    StepWrapper stepWrapper = mock(StepWrapper.class);

    when(stepWrapperSteps.stepWrapper()).thenReturn(stepWrapper);

    String stepName = getStepName();

    Runnable runnable =
        () -> {
          throw new VeryScaryException();
        };

    assertThrows(VeryScaryException.class, () -> stepWrapperSteps.step(stepName, runnable));
    verify(stepWrapper, times(1)).startStep(stepName);
    verify(stepWrapper, times(1)).stopStep(Status.BROKEN);
  }

  @Test
  void stopStepTest() {
    StepWrapperSteps<StepWrapperStepsConcrete> stepWrapperSteps =
        spy(StepWrapperStepsConcrete.class);
    StepWrapper stepWrapper = mock(StepWrapper.class);

    when(stepWrapperSteps.stepWrapper()).thenReturn(stepWrapper);

    String stepName = getStepName();

    stepWrapperSteps.startStep(stepName).stopStep();

    verify(stepWrapper, times(1)).startStep(stepName);
    verify(stepWrapper, times(1)).stopStep(Status.PASSED);
  }

  private String getStepName() {
    return UUID.randomUUID().toString();
  }
}
