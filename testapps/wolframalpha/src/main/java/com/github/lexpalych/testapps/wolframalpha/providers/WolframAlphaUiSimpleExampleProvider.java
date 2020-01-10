package com.github.lexpalych.testapps.wolframalpha.providers;

import com.github.lexpalych.allure.webdriver.WebDriverPageObjectFactoryCallbacks;
import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaMain;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaResult;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.Set;
import java.util.stream.Stream;

import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaBuilderFunctions.PREPARE_UI;
import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaExample.getExampleList;
import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaExample.getTestTemplateInvocationContextBuilder;

public class WolframAlphaUiSimpleExampleProvider implements TestTemplateInvocationContextProvider {
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
                .map(builder -> builder.addExtension(new WebDriverPageObjectFactoryCallbacks(Set.of(WolframAlphaMain.class, WolframAlphaResult.class))))
                .map(PREPARE_UI);

    }

    private static final class SimpleExample {
        private Stream<TestTemplateInvocationContextBuilder> getBuilders() {
            return Stream.of(
                    simple(),
                    bracket(),
                    function(),
                    veryHard()
            )
                    .flatMap(stream -> stream);
        }

        private Stream<TestTemplateInvocationContextBuilder> simple() {
            return getExampleList("function")
                    .stream()
                    .map(example -> getTestTemplateInvocationContextBuilder(example, "Простые примеры"));
        }

        private Stream<TestTemplateInvocationContextBuilder> bracket() {
            return getExampleList("bracket")
                    .stream()
                    .map(example -> getTestTemplateInvocationContextBuilder(example, "Простые примеры со скобочками"));
        }

        private Stream<TestTemplateInvocationContextBuilder> function() {
            return getExampleList("function")
                    .stream()
                    .map(example -> getTestTemplateInvocationContextBuilder(example, "Простые примеры с функциями"));
        }

        private Stream<TestTemplateInvocationContextBuilder> veryHard() {
            return getExampleList("veryHard")
                    .stream()
                    .map(example -> getTestTemplateInvocationContextBuilder(example, "Очень сложные примеры"));
        }

    }
}
