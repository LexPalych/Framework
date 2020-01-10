package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureLabelExtension;
import com.typesafe.config.ConfigFactory;

import java.util.List;

import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleCalculator.calculate;

public class WolframAlphaExample {
    public static List<String> getExampleList(final String examplePath) {
        return ConfigFactory.load("example.conf").getStringList(examplePath);
    }

    public static TestTemplateInvocationContextBuilder getTestTemplateInvocationContextBuilder(final String example, final String label) {
        String result = calculate(example).toString();
        String displayName = example + " = " + result;

        return new TestTemplateInvocationContextBuilder()
                .withDisplayName(displayName)

                .addParameterResolver(String.class, example, "example")
                .addParameterResolver(String.class, result, "result")

                .addExtension(new AllureLabelExtension("suite", label));
    }
}
