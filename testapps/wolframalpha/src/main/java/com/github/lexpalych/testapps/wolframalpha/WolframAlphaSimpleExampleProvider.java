package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureLabelExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.stream.Stream;

import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaBuilderFunctions.PREPARE_UI;
import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleCalculator.calculate;

public class WolframAlphaSimpleExampleProvider implements TestTemplateInvocationContextProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        String testClassName = context.getTestClass().orElseThrow().getSimpleName();
        String testMethodName = context.getTestMethod().orElseThrow().getName();

        return testClassName.equals("WolframAlphaUi") && testMethodName.equals("checkCalculator");
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return new SimpleExample()
                .getBuilders()
                .map(PREPARE_UI);

    }

    private final class SimpleExample {
        private Stream<TestTemplateInvocationContextBuilder> getBuilders() {
            return Stream.of(
                    addition()/*,
                    subtraction(),
                    multiplication(),
                    division(),
                    exponentiation(),
                    factorial()*/
            );
        }

        private TestTemplateInvocationContextBuilder addition() {
            String example = "1+2";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder subtraction() {
            String example = "5-2";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder multiplication() {
            String example = "5*2";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder division() {
            String example = "12/3";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder exponentiation() {
            String example = "12/3";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder factorial() {
            String example = "5!";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder getBuilder(final String example) {
            LOGGER.debug("Проерка вывода данного сообщения в логи");

            String result = calculate(example).toString();
            String displayName = example + " = " + result;

            return new TestTemplateInvocationContextBuilder()
                    .withDisplayName(displayName)

                    .addParameterResolver(String.class, example, "example")
                    .addParameterResolver(String.class, result, "result")

                    .addExtension(new AllureLabelExtension("suite", "Простые примеры"));
        }
    }
}
