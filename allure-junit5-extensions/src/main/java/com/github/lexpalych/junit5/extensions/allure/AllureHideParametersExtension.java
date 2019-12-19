package com.github.lexpalych.junit5.extensions.allure;

import static java.util.stream.Collectors.toList;

import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import java.util.List;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class AllureHideParametersExtension implements AfterEachCallback {
  @Override
  public void afterEach(ExtensionContext context) {
    Allure.getLifecycle()
        .updateTestCase(
            update -> {
              List<StepResult> processed = processStepResult(update.getSteps());
              update.setSteps(processed);
            });
  }

  private List<StepResult> processStepResult(List<StepResult> stepResults) {
    if (stepResults.size() != 0) {
      return stepResults.stream()
          .peek(
              stepResult -> {
                stepResult.setParameters(List.of());
                stepResult.setSteps(processStepResult(stepResult.getSteps()));
              })
          .collect(toList());
    }

    return List.of();
  }
}
