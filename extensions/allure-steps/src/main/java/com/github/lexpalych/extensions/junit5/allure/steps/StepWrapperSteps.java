package com.github.lexpalych.extensions.junit5.allure.steps;

import io.qameta.allure.model.Status;

@SuppressWarnings("unchecked")
public abstract class StepWrapperSteps<T extends StepWrapperSteps<T>> {
  private static final ThreadLocal<StepWrapper> stepWrapperThreadLocal = ThreadLocal.withInitial(StepWrapper::new);

  public final T startStep(String stepName) {
    stepWrapper().startStep(stepName);
    return (T) this;
  }

  public final T stopStep() {
    stepWrapper().stopStep(Status.PASSED);
    return (T) this;
  }

  public final T step(String stepName, Runnable runnable) {
    stepWrapper().startStep(stepName);

    try {
      runnable.run();
      stepWrapper().stopStep(Status.PASSED);
    } catch (Exception e) {
      stepWrapper().stopStep(Status.BROKEN);
      throw e;
    }

    return (T) this;
  }

  /** Only for testing */
  StepWrapper stepWrapper() {
    return stepWrapperThreadLocal.get();
  }
}
