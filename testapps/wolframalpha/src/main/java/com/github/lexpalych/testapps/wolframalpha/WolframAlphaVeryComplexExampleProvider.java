package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureLabelExtension;
import com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleCalculator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.stream.Stream;

import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaBuilderFunctions.PREPARE_UI;

public class WolframAlphaVeryComplexExampleProvider implements TestTemplateInvocationContextProvider {
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
                    veryComplex1(),
                    veryComplex2(),
                    veryComplex3()
            );
        }

        private TestTemplateInvocationContextBuilder veryComplex1() {
            String example = "-1.1+0.1+2+3*8/2-5*exp(0)+sin(30)-tan(0)-(1.5-0.5*2-1+3!)-(1+(2+((2+3)*2)/2-4)*3)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder veryComplex2() {
            String example = "-5*exp(15)+sin(-30)-tan(0)-(1232.755-0.5634*7562-1745+3!)-(-12+(-24+((2+324)*2)/27-4)*311)*4523*25223";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder veryComplex3() {
            String example = "57.8/32/1*3-(sin(450/5)+tan(ln(10)-83))/(10/2.5)!+exp(5)*sinh(30)*sqrt(-8*sqrt(abs(225))-3!)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder getBuilder(final String example) {
            String result = ExampleCalculator.calculate(example).toString();
            String displayName = example + " = " + result;

            return new TestTemplateInvocationContextBuilder()
                    .withDisplayName(displayName)

                    .addParameterResolver(String.class, example, "example")
                    .addParameterResolver(String.class, result, "result")

                    .addExtension(new AllureLabelExtension("suite", "Очень сложные примеры"));
        }
    }
}
