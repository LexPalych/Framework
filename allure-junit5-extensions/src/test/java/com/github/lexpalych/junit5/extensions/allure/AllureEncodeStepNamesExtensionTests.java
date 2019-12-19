package com.github.lexpalych.junit5.extensions.allure;

import static org.junit.jupiter.api.Assertions.*;

import io.qameta.allure.model.StepResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

final class AllureEncodeStepNamesExtensionTests {
  @Test
  void defaultConstructorTest() {
    String regex = "bite my shiny metal ass";
    System.setProperty("allureEncodeStepNamesRegex", regex);
    AllureEncodeStepNamesExtension extension = new AllureEncodeStepNamesExtension();
    assertEquals(regex, extension.getRegex());
  }

  @Test
  void nonDefaultConstructorTest() {
    String regex = "sweetComplexRegex";
    AllureEncodeStepNamesExtension extension = new AllureEncodeStepNamesExtension(regex);
    assertEquals(regex, extension.getRegex());
  }

  @Test
  void afterEachTest() {
    // Fixture set up
    String regex = "(?<=soSecretPassword=)\\S*";

    Map<String, StepResult> stepResults =
        Map.of(
            AllureExtensionsTestUtil.getUuid(), AllureExtensionsTestUtil.getStepResult("soSecretPassword=123456"),
            AllureExtensionsTestUtil.getUuid(), AllureExtensionsTestUtil.getStepResult("soSecretPassword=qwerty"),
            AllureExtensionsTestUtil.getUuid(), AllureExtensionsTestUtil.getStepResult("soSecretPassword=impossibleToCrack"));

    List<String> shouldNotBePresent = List.of("123456", "qwerty", "impossibleToCrack");

    AllureExtensionsTestUtil.startSteps(stepResults);

    AllureEncodeStepNamesExtension extension = new AllureEncodeStepNamesExtension(regex);

    // Executing SUT
    extension.afterEach(null);

    // Verification
    List<StepResult> processedStepResults = AllureExtensionsTestUtil.getProcessedStepResults(stepResults);

    List<Executable> executables =
        processedStepResults.stream()
            .map(
                stepResult ->
                    (Executable)
                        () -> {
                          String stepName = stepResult.getName();
                          assertFalse(
                              containsUnexpectedValue(shouldNotBePresent, stepName),
                              stepName + " should not contain " + shouldNotBePresent);
                        })
            .collect(Collectors.toUnmodifiableList());

    assertFalse(executables.isEmpty());
    assertAll(executables);
  }

  private static boolean containsUnexpectedValue(List<String> unexpectedValues, String toCheck) {
    return unexpectedValues.stream().anyMatch(toCheck::contains);
  }
}
