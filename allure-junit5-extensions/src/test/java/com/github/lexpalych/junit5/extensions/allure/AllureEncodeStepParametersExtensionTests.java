package com.github.lexpalych.junit5.extensions.allure;

import static org.junit.jupiter.api.Assertions.*;

import io.qameta.allure.model.StepResult;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

final class AllureEncodeStepParametersExtensionTests {
  @Test
  void defaultConstructorTest() {
    String parameterNames = "password,token,secret";
    System.setProperty("allureEncodeStepParameters", parameterNames);
    AllureEncodeStepParametersExtension extension = new AllureEncodeStepParametersExtension();
    assertEquals(List.of(parameterNames.split(",")), extension.getParameterNames());
  }

  @Test
  void nonDefaultConstructorTest() {
    String[] parameterNames = new String[] {"password", "token", "secret"};
    AllureEncodeStepParametersExtension extension =
        new AllureEncodeStepParametersExtension(parameterNames);
    assertEquals(List.of(parameterNames), extension.getParameterNames());
  }

  @Test
  void afterEachTest() {
    // Fixture set up
    Map<String, StepResult> stepResults =
        Map.of(
            AllureExtensionsTestUtil.getUuid(),
                AllureExtensionsTestUtil.getStepResult("login").setParameters(List.of(AllureExtensionsTestUtil.getParameter("password", "1234"))),
            AllureExtensionsTestUtil.getUuid(),
                AllureExtensionsTestUtil.getStepResult("authorize").setParameters(List.of(AllureExtensionsTestUtil.getParameter("token", "ohgeez"))),
            AllureExtensionsTestUtil.getUuid(),
                AllureExtensionsTestUtil.getStepResult("sign in")
                    .setParameters(List.of(AllureExtensionsTestUtil.getParameter("secret", "iwanttosleep"))));

    AllureExtensionsTestUtil.startSteps(stepResults);

    String[] parameterNames = new String[] {"password", "token", "secret"};
    List<String> parameterNamesList = List.of(parameterNames);

    AllureEncodeStepParametersExtension extension =
        new AllureEncodeStepParametersExtension(parameterNames);

    // Executing SUT
    extension.afterEach(null);

    // Verification
    List<StepResult> processedStepResults = AllureExtensionsTestUtil.getProcessedStepResults(stepResults);

    List<Executable> executables =
        processedStepResults.stream()
            .flatMap(stepResult -> stepResult.getParameters().stream())
            .filter(parameter -> parameterNamesList.contains(parameter.getName()))
            .map(parameter -> (Executable) () -> assertEquals("*****", parameter.getValue()))
            .collect(Collectors.toUnmodifiableList());

    assertFalse(executables.isEmpty());
    assertAll(executables);
  }
}
