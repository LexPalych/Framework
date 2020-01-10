package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureLabelExtension;
import com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleCalculator;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.stream.Stream;

import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaBuilderFunctions.PREPARE_API;
import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleCalculator.calculate;

public class WolframAlphaApiFunctionExampleProvider implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        String testClassName = context.getTestClass().orElseThrow().getSimpleName();
        String testMethodName = context.getTestMethod().orElseThrow().getName();

        return testClassName.equals("WolframAlphaApi") && testMethodName.equals("checkCalculatorFunction");
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return ConfigFactory
                .load("example.conf")
                .getStringList("function")
                .stream()
                .map(this::getTestTemplateInvocationContextBuilder)
                .map(PREPARE_API);

    }

    private TestTemplateInvocationContextBuilder getTestTemplateInvocationContextBuilder(final String example) {
        String result = calculate(example).toString();
        String displayName = example + " = " + result;

        return new TestTemplateInvocationContextBuilder()
                .withDisplayName(displayName)

                .addParameterResolver(String.class, example, "example")
                .addParameterResolver(String.class, result, "result")

                .addExtension(new AllureLabelExtension("suite", "Простые примеры с функциями"));
    }
}
