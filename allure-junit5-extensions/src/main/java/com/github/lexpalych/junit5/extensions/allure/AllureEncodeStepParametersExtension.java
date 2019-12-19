package com.github.lexpalych.junit5.extensions.allure;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.StepResult;
import java.util.List;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class AllureEncodeStepParametersExtension implements AfterEachCallback {
  private final List<String> parameterNames;

  public AllureEncodeStepParametersExtension() {
    this.parameterNames = List.of(System.getProperty("allureEncodeStepParameters").split(","));
  }

  public AllureEncodeStepParametersExtension(String... parameterNames) {
    this.parameterNames = List.of(parameterNames);
  }

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
                List<Parameter> parameters =
                    stepResult.getParameters().stream()
                        .filter(parameter -> parameterNames.contains(parameter.getName()))
                        .peek(parameter -> parameter.setValue("*****"))
                        .collect(toUnmodifiableList());

                stepResult.setParameters(parameters);

                stepResult.setSteps(processStepResult(stepResult.getSteps()));
              })
          .collect(toList());
    }

    return List.of();
  }

  /** Only for testing */
  List<String> getParameterNames() {
    return parameterNames;
  }
}
