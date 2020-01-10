package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureLabelExtension;
import com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleCalculator;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;
import java.util.stream.Stream;

import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaBuilderFunctions.PREPARE_UI;

public class WolframAlphaBracketExampleProvider implements TestTemplateInvocationContextProvider {
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
            return ConfigFactory
                    .load("example.conf")
                    .getStringList("bracket")
                    .stream()
                    .map(this::getBuilder);
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
