package com.github.lexpalych.extensions.junit5.extensions;

import static org.junit.jupiter.api.extension.ConditionEvaluationResult.disabled;
import static org.junit.jupiter.api.extension.ConditionEvaluationResult.enabled;

import java.util.regex.Pattern;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class ExcludeDisplayNameRegexExecutionCondition implements ExecutionCondition {
  private static final String PROPERTY_NAME = "excludeDisplayNamesRegex";
  private final String PATTERN = System.getProperty(PROPERTY_NAME);

  @Override
  public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
    if (PATTERN != null) {
      String displayName = context.getDisplayName();

      if (Pattern.matches(PATTERN, displayName)) {
        return disabled(displayName + " matches " + PROPERTY_NAME + " pattern " + PATTERN);
      }

      return enabled(displayName + " doesn't match " + PROPERTY_NAME + " pattern " + PATTERN);
    }

    return enabled(PROPERTY_NAME + " property is null");
  }
}
