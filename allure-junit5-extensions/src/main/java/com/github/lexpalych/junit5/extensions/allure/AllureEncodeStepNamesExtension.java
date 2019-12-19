package com.github.lexpalych.junit5.extensions.allure;

import static java.util.stream.Collectors.toList;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.StepResult;
import java.util.List;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class AllureEncodeStepNamesExtension implements AfterEachCallback {
  private final String regex;

  public AllureEncodeStepNamesExtension() {
    this.regex = System.getProperty("allureEncodeStepNamesRegex");
  }

  public AllureEncodeStepNamesExtension(String regex) {
    this.regex = regex;
  }

  @Override
  public void afterEach(ExtensionContext context) {
    lifecycle()
        .updateTestCase(
            update -> {
              List<StepResult> processed = processStepResult(update.getSteps());
              update.setSteps(processed);
            });
  }

  List<StepResult> processStepResult(List<StepResult> stepResults) {
    if (stepResults.size() != 0) {
      return stepResults.stream()
          .peek(
              stepResult -> {
                String name = stepResult.getName();

                name = name.replaceAll(regex, "*****");

                stepResult.setName(name);

                stepResult.setSteps(processStepResult(stepResult.getSteps()));
              })
          .collect(toList());
    }

    return List.of();
  }

  /** Only for testing */
  String getRegex() {
    return regex;
  }

  private AllureLifecycle lifecycle() {
    return Allure.getLifecycle();
  }
}
