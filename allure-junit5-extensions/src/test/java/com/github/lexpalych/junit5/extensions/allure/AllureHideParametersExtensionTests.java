package com.github.lexpalych.junit5.extensions.allure;

import static org.junit.jupiter.api.Assertions.*;

import io.qameta.allure.model.StepResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

final class AllureHideParametersExtensionTests {
  @Test
  void afterEachTest() {
    // Fixture set up
    Map<String, StepResult> stepResults =
        Map.of(
            AllureExtensionsTestUtil.getUuid(),
                AllureExtensionsTestUtil.getStepResult("login")
                    .setParameters(
                        List.of(AllureExtensionsTestUtil.getParameter("password", "1234"), AllureExtensionsTestUtil.getParameter("login", "t1000"))),
            AllureExtensionsTestUtil.getUuid(),
                AllureExtensionsTestUtil.getStepResult("authorize")
                    .setParameters(
                        List.of(AllureExtensionsTestUtil.getParameter("token", "ohgeez"), AllureExtensionsTestUtil.getParameter("name", "morty"))),
            AllureExtensionsTestUtil.getUuid(),
                AllureExtensionsTestUtil.getStepResult("sign in")
                    .setParameters(
                        List.of(
                            AllureExtensionsTestUtil.getParameter("secret", "iwanttosleep"),
                            AllureExtensionsTestUtil.getParameter("really", "badly"),
                            AllureExtensionsTestUtil.getParameter("and", "also"),
                            AllureExtensionsTestUtil.getParameter("i", "want"),
                            AllureExtensionsTestUtil.getParameter("to", "eat"),
                            AllureExtensionsTestUtil.getParameter("seriously", "!"))));

    AllureExtensionsTestUtil.startSteps(stepResults);

    AllureHideParametersExtension extension = new AllureHideParametersExtension();

    // Executing SUT
    extension.afterEach(null);

    // Verification
    List<StepResult> processedStepResults = AllureExtensionsTestUtil.getProcessedStepResults(stepResults);

    List<Executable> executables =
        processedStepResults.stream()
            .map(stepResult -> (Executable) () -> assertTrue(stepResult.getParameters().isEmpty()))
            .collect(Collectors.toUnmodifiableList());

    assertFalse(executables.isEmpty());
    assertAll(executables);
  }
}
