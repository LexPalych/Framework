package com.github.lexpalych.junit5.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

final class TestTemplateInvocationContextImplTests {
  @Test
  void getDisplayNameTest() {
    String displayName = "display me!";
    int invocationIndex = 5;
    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextImpl(displayName, null);

    assertEquals(
        displayName + " [" + invocationIndex + "]", context.getDisplayName(invocationIndex));
  }

  @Test
  void getAdditionalExtensionsTest() {
    List<Extension> expected =
        List.of(
            new ExcludeDisplayNameExecutionCondition(),
            new IncludeDisplayNameRegexExecutionCondition(),
            new GenericParameterResolver<>(String.class, "meow"));

    TestTemplateInvocationContext context = new TestTemplateInvocationContextImpl(null, expected);

    assertEquals(expected, context.getAdditionalExtensions());
  }
}
