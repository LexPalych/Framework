package com.github.lexpalych.junit5.extensions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

final class TestTemplateInvocationContextBuilderTests {
  @Test
  void withDisplayNameTest() {
    int invocationIndex = 10;
    String displayName = "Test to rule all tests";
    String expected = displayName + " [" + invocationIndex + "]";

    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextBuilder().withDisplayName(displayName).build();

    assertEquals(expected, context.getDisplayName(invocationIndex));
  }

  @Test
  void withDisplayNamePrefixTest() {
    int invocationIndex = 10;
    String displayNamePrefix = "Test to rule all tests";
    String expected = displayNamePrefix + " null [" + invocationIndex + "]";

    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextBuilder().withDisplayNamePrefix(displayNamePrefix).build();

    assertEquals(expected, context.getDisplayName(invocationIndex));
  }

  @Test
  void withDisplayNamePostfixTest() {
    int invocationIndex = 10;
    String displayNamePostfix = "Test to rule all tests";
    String expected = "null " + displayNamePostfix + " [" + invocationIndex + "]";

    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextBuilder()
            .withDisplayNamePostfix(displayNamePostfix)
            .build();

    assertEquals(expected, context.getDisplayName(invocationIndex));
  }

  @Test
  void addParameterResolver() {
    Class<String> aClass = String.class;
    String value = "lolwut";

    List<Extension> expected = List.of(new GenericParameterResolver<>(aClass, value));

    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextBuilder().addParameterResolver(aClass, value).build();

    List<Extension> actualExtensions = context.getAdditionalExtensions();

    assertEquals(expected, actualExtensions);
  }

  @Test
  void addParameterResolverWithName() {
    Class<String> aClass = String.class;
    String value = "lolwut";
    String name = "message";

    List<Extension> expected = List.of(new GenericParameterResolver<>(aClass, value, name));

    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextBuilder()
            .addParameterResolver(aClass, value, name)
            .build();

    List<Extension> actualExtensions = context.getAdditionalExtensions();

    assertEquals(expected, actualExtensions);
  }

  @Test
  void addExtensionsTest() {
    Extension first = new ExcludeDisplayNameExecutionCondition();
    Extension second = new IncludeDisplayNameRegexExecutionCondition();

    List<Extension> expected = List.of(first, second);

    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextBuilder().addExtension(first).addExtension(second).build();

    assertEquals(expected, context.getAdditionalExtensions());
  }

  @Test
  void addExtensionsIndexTest() {
    Extension first = new ExcludeDisplayNameExecutionCondition();
    Extension second = new IncludeDisplayNameRegexExecutionCondition();
    Extension third = new GenericParameterResolver<>(String.class, "ohmy");

    List<Extension> expected = List.of(first, second, third);

    TestTemplateInvocationContext context =
        new TestTemplateInvocationContextBuilder()
            .addExtension(second)
            .addExtension(third)
            .addExtension(0, first)
            .build();

    assertEquals(expected, context.getAdditionalExtensions());
  }
}
