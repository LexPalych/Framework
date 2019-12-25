package wolframalpha;

import com.github.lexpalych.allure.webdriver.WebDriverPageObjectFactoryCallbacks;
import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureEncodeStepNamesExtension;
import com.github.lexpalych.junit5.extensions.allure.AllureFishTaggingExtension;
import com.github.lexpalych.junit5.extensions.allure.AllureHideParametersExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import wolframalpha.objectmodel.WolframAlphaMain;
import wolframalpha.objectmodel.WolframAlphaResult;

import java.util.Set;
import java.util.function.Function;

class WolframAlphaBuilderFunctions {
    private static final Config CONFIG = ConfigFactory.load();

    static final Function<TestTemplateInvocationContextBuilder, TestTemplateInvocationContext> PREPARE =
            builder -> builder
                    .withDisplayNamePrefix("Проверка примера")
                    .addExtension(new WebDriverPageObjectFactoryCallbacks(Set.of(WolframAlphaMain.class, WolframAlphaResult.class)))
                    .addParameterResolver(String.class, CONFIG.getString("wolframalpha.url"), "url")

                    .addExtension(0, new AllureFishTaggingExtension())
                    .addExtension(new AllureHideParametersExtension())
//                    .addExtension(new AllureEncodeStepNamesExtension())
//                    .addExtension(new AllureConcurrentLoggerAttachmentsExtension())
            .build();
}
