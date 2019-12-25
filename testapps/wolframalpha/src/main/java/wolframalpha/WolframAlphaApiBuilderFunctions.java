package wolframalpha;

import com.github.lexpalych.allure.webdriver.WebDriverPageObjectFactoryCallbacks;
import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureFishTaggingExtension;
import com.github.lexpalych.junit5.extensions.allure.AllureHideParametersExtension;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import wolframalpha.objectmodel.WolframAlphaMain;
import wolframalpha.objectmodel.WolframAlphaResult;

import java.util.Set;
import java.util.function.Function;

class WolframAlphaApiBuilderFunctions {
    private static final Config CONFIG = ConfigFactory.load();

    static final Function<TestTemplateInvocationContextBuilder, TestTemplateInvocationContext> PREPARE =
            builder -> builder
                    .withDisplayNamePrefix("Проверка примера через API")
                    .addParameterResolver(String.class, CONFIG.getString("wolframalpha.api.url"), "url")

                    .addExtension(0, new AllureFishTaggingExtension())
//                    .addExtension(new AllureHideParametersExtension())
//                    .addExtension(new AllureEncodeStepNamesExtension())
//                    .addExtension(new AllureConcurrentLoggerAttachmentsExtension())
            .build();
}
