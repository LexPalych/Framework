package com.github.lexpalych.junit5.extensions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
final class IncludeDisplayNameExecutionConditionTests {
  private static final String PROPERTY_NAME = "includeDisplayNames";

  @Test
  void nullPropertyEnablesAllTests() {
    System.clearProperty(PROPERTY_NAME);

    ExecutionCondition executionCondition = new IncludeDisplayNameExecutionCondition();

    ConditionEvaluationResult result = executionCondition.evaluateExecutionCondition(null);

    assertFalse(result.isDisabled());
    assertEquals(PROPERTY_NAME + " property is null", result.getReason().orElseThrow());
  }

  @Test
  void shouldDisableTest() {
    String displayNameToInclude = "includeMeee";
    String displayName = "whatIsGoingOnHere";

    System.setProperty(PROPERTY_NAME, displayNameToInclude);

    ExtensionContext context = mock(ExtensionContext.class);
    when(context.getDisplayName()).thenReturn(displayName);

    ExecutionCondition executionCondition = new IncludeDisplayNameExecutionCondition();

    ConditionEvaluationResult result = executionCondition.evaluateExecutionCondition(context);

    assertTrue(result.isDisabled());
    assertEquals(
        displayName
            + " is not present in "
            + PROPERTY_NAME
            + " property "
            + List.of(displayNameToInclude),
        result.getReason().orElseThrow());
  }

  @Test
  void shouldEnableTest() {
    String displayName = "includeMeNow";

    System.setProperty(PROPERTY_NAME, displayName);

    ExtensionContext context = mock(ExtensionContext.class);
    when(context.getDisplayName()).thenReturn(displayName);

    ExecutionCondition executionCondition = new IncludeDisplayNameExecutionCondition();

    ConditionEvaluationResult result = executionCondition.evaluateExecutionCondition(context);

    assertFalse(result.isDisabled());
    assertEquals(
        displayName + " is present in " + PROPERTY_NAME + " property " + List.of(displayName),
        result.getReason().orElseThrow());
  }
}
