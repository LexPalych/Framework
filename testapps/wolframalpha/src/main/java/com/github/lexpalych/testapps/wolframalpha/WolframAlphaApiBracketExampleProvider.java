package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureLabelExtension;
import com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleCalculator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.stream.Stream;

public class WolframAlphaApiBracketExampleProvider implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        String testClassName = context.getTestClass().orElseThrow().getSimpleName();
        String testMethodName = context.getTestMethod().orElseThrow().getName();

        return testClassName.equals("WolframAlphaApi") && testMethodName.equals("checkCalculator");
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return new SimpleExample()
                .getBuilders()
                .map(WolframAlphaBuilderFunctions.PREPARE_API);

    }

    private final class SimpleExample {
        private Stream<TestTemplateInvocationContextBuilder> getBuilders() {
            return Stream.of(
                    additionInBracket(),
                    subtractionInBracket(),
                    multiplicationInBracket(),
                    divisionInBracket(),
                    exponentiationInBracket(),
                    factorialInBracket()
            );
        }

        private TestTemplateInvocationContextBuilder additionInBracket() {
            String example = "(1+2)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder subtractionInBracket() {
            String example = "(5-2)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder multiplicationInBracket() {
            String example = "(5*2)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder divisionInBracket() {
            String example = "(12/3)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder exponentiationInBracket() {
            String example = "(12/3)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder factorialInBracket() {
            String example = "(5)!";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder getBuilder(final String example) {
            String result = ExampleCalculator.calculate(example).toString();
            String displayName = example + " = " + result;

            return new TestTemplateInvocationContextBuilder()
                    .withDisplayName(displayName)

                    .addParameterResolver(String.class, example, "example")
                    .addParameterResolver(String.class, result, "result")

                    .addExtension(new AllureLabelExtension("suite", "Простые примеры со скобочками"));
        }
    }
}
