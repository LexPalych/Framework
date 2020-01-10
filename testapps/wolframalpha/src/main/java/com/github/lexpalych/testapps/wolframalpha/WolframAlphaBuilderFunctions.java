package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.allure.webdriver.WebDriverPageObjectFactoryCallbacks;
import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.AllureConcurrentLoggerAttachmentsExtension;
import com.github.lexpalych.junit5.extensions.allure.AllureEncodeStepNamesExtension;
import com.github.lexpalych.junit5.extensions.allure.AllureFishTaggingExtension;
import com.github.lexpalych.junit5.extensions.allure.AllureHideParametersExtension;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaMain;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaResult;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;

import java.util.Set;
import java.util.function.Function;

import static com.github.lexpalych.testapps.wolframalpha.WolframAlphaRequestSpecification.REQUEST_SPEC;

public class WolframAlphaBuilderFunctions {
    private static final Config CONFIG = ConfigFactory.load();

    public static final Function<TestTemplateInvocationContextBuilder, TestTemplateInvocationContext> PREPARE_UI =
            builder -> builder
                    .addParameterResolver(RequestSpecification.class, REQUEST_SPEC, "requestSpecification")

                    .withDisplayNamePrefix("Проверка примера")
                    .addExtension(new WebDriverPageObjectFactoryCallbacks(Set.of(WolframAlphaMain.class, WolframAlphaResult.class)))
                    .addParameterResolver(String.class, CONFIG.getString("wolframalpha.ui.url"), "url")

                    .addExtension(0, new AllureFishTaggingExtension())
                    .addExtension(new AllureHideParametersExtension())
                    .addExtension(new AllureEncodeStepNamesExtension("(?<=Добавление заголовка encoding=).*"))
                    .addExtension(new AllureConcurrentLoggerAttachmentsExtension())
            .build();

    public static final Function<TestTemplateInvocationContextBuilder, TestTemplateInvocationContext> PREPARE_API =
            builder -> builder
                    .withDisplayNamePrefix("Проверка примера через API")
                    .addParameterResolver(String.class, CONFIG.getString("wolframalpha.api.url"), "url")

                    .addExtension(0, new AllureFishTaggingExtension())
                    .addExtension(new AllureHideParametersExtension())
                    .addExtension(new AllureEncodeStepNamesExtension("(?<=Добавление заголовка encoding=).*"))
                    .addExtension(new AllureConcurrentLoggerAttachmentsExtension())
                    .build();
}
