import com.github.lexpalych.allure.webdriver.WebDriverPageObjectFactoryCallbacks;
import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.typesafe.config.ConfigFactory;
import objectmodel.WolframAlphaMain;
import objectmodel.WolframAlphaResult;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider;

import java.util.Set;
import java.util.stream.Stream;

public class WolframAlphaCalculatorUiProvider implements TestTemplateInvocationContextProvider {
    @Override
    public boolean supportsTestTemplate(ExtensionContext context) {
        String testClassName = context.getTestClass().orElseThrow().getSimpleName();
        String testMethodName = context.getTestMethod().orElseThrow().getName();

        return testClassName.equals("WolframAlphaUi") && testMethodName.equals("checkCalculator");
    }

    @Override
    public Stream<TestTemplateInvocationContext> provideTestTemplateInvocationContexts(ExtensionContext context) {
        String url = ConfigFactory.load().getString("wolframalpha.url");
        String example = "1+2";
        String value = "3";

        return Stream.of(
                new TestTemplateInvocationContextBuilder()
                .withDisplayName("Проверка калькулятора")
                .addExtension(new WebDriverPageObjectFactoryCallbacks(Set.of(WolframAlphaMain.class, WolframAlphaResult.class)))

                .addParameterResolver(String.class, url, "url")
                .addParameterResolver(String.class, example, "example")
                .addParameterResolver(String.class, value, "result")

                .build()
        );

    }
}
