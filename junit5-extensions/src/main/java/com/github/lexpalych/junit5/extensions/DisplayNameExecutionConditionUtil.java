package com.github.lexpalych.junit5.extensions;

import java.util.Arrays;
import java.util.List;

final class DisplayNameExecutionConditionUtil {
  static List<String> getDisplayNamesForSystemProperty(String property) {
    String displayNames = System.getProperty(property);

    if (displayNames != null) {
      return Arrays.asList(displayNames.split(","));

    } else {
      return List.of();
    }
  }
}
