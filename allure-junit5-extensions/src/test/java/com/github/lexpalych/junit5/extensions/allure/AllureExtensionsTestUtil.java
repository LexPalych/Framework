package com.github.lexpalych.junit5.extensions.allure;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.StepResult;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

final class AllureExtensionsTestUtil {
  static StepResult getStepResult(String name) {
    return new StepResult().setName(name);
  }

  static Parameter getParameter(String name, String value) {
    return new Parameter().setName(name).setValue(value);
  }

  static List<StepResult> getProcessedStepResults(Map<String, StepResult> stepResults) {
    return stepResults.keySet().stream()
        .map(
            stepUuid -> {
              AtomicReference<StepResult> stepResultAtomicReference = new AtomicReference<>();
              lifecycle().updateStep(stepUuid, stepResultAtomicReference::set);
              return stepResultAtomicReference.get();
            })
        .collect(Collectors.toUnmodifiableList());
  }

  static void startSteps(Map<String, StepResult> stepResults) {
    stepResults.forEach((key, value) -> lifecycle().startStep(key, value));
  }

  static String getUuid() {
    return UUID.randomUUID().toString();
  }

  static AllureLifecycle lifecycle() {
    return Allure.getLifecycle();
  }
}
