package wolframalpha;

import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureLabelExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.stream.Stream;

import static wolframalpha.WolframAlphaBuilderFunctions.PREPARE;
import static wolframalpha.examplecalculator.ExampleCalculator.calculate;

public class WolframAlphaFunctionExampleProvider implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        String testClassName = context.getTestClass().orElseThrow().getSimpleName();
        String testMethodName = context.getTestMethod().orElseThrow().getName();

        return testClassName.equals("WolframAlphaUi") && testMethodName.equals("checkFunctions");
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        return new SimpleExample()
                .getBuilders()
                .map(PREPARE);

    }

    private final class SimpleExample {
        private Stream<TestTemplateInvocationContextBuilder> getBuilders() {
            return Stream.of(
                    sin(),
                    cos(),
                    tan(),
                    asin(),
                    acos(),
                    atan(),
                    sinh(),
                    cosh(),
                    tanh(),
                    ln(),
                    exp(),
                    abs(),
                    sqrt()
            );
        }

        private TestTemplateInvocationContextBuilder sin() {
            String example = "sin(60)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder cos() {
            String example = "cos(30)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder tan() {
            String example = "tan(45)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder asin() {
            String example = "asin(0.5)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder acos() {
            String example = "acos(0.5)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder atan() {
            String example = "atan(1)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder sinh() {
            String example = "sinh(10)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder cosh() {
            String example = "cosh(10)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder tanh() {
            String example = "tanh(10)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder ln() {
            String example = "ln(10)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder exp() {
            String example = "exp(10)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder abs() {
            String example = "abs(-5)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder sqrt() {
            String example = "sqrt(16)";
            return getBuilder(example);
        }

        private TestTemplateInvocationContextBuilder getBuilder(final String example) {
            String result = calculate(example).toString();
            String displayName = example + " = " + result;

            return new TestTemplateInvocationContextBuilder()
                    .withDisplayName(displayName)

                    .addParameterResolver(String.class, example, "example")
                    .addParameterResolver(String.class, result, "result")

                    .addExtension(new AllureLabelExtension("suite", "Простые примеры с функциями"));
        }
    }
}
