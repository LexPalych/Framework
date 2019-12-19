package com.github.lexpalych.junit5.extensions;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;

import java.util.List;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class IncludeDisplayNameExecutionCondition implements ExecutionCondition {
  private static final String PROPERTY_NAME = "includeDisplayNames";
  private final List<String> DISPLAY_NAMES_LIST = DisplayNameExecutionConditionUtil.getDisplayNamesForSystemProperty(PROPERTY_NAME);

  @Override
  public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
    if (DISPLAY_NAMES_LIST.size() != 0) {
      String displayName = context.getDisplayName();

      boolean isContained = DISPLAY_NAMES_LIST.stream().anyMatch(displayName::contains);

      if (isContained) {
        return enabled(
            displayName + " is present in " + PROPERTY_NAME + " property " + DISPLAY_NAMES_LIST);
      }

      return disabled(
          displayName + " is not present in " + PROPERTY_NAME + " property " + DISPLAY_NAMES_LIST);
    }

    return enabled(PROPERTY_NAME + " property is null");
  }
}
