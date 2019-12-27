package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.allure.webdriver.WebDriverPageObjectFactoryCallbacks;
import com.github.lexpalych.junit5.extensions.TestTemplateInvocationContextBuilder;
import com.github.lexpalych.junit5.extensions.allure.*;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaMain;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.TestTemplateInvocationContext;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaResult;

import java.io.PrintStream;
import java.util.Set;
import java.util.function.Function;

class WolframAlphaBuilderFunctions {
    private static final Config CONFIG = ConfigFactory.load();

    private static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder()
            .addHeader("encoding", "UTF-8")
            .addRequestSpecification(loggingRequestSpec())
            .build();

    private static RequestSpecification loggingRequestSpec() {
        PrintStream printStream = LoggerStreamProvider.getLoggerPrintStream();

        return new RequestSpecBuilder()
                .addFilter(RequestLoggingFilter.logRequestTo(printStream))
                .addFilter(ResponseLoggingFilter.logResponseTo(printStream))
                .build();
    }

    static final Function<TestTemplateInvocationContextBuilder, TestTemplateInvocationContext> PREPARE_UI =
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

    static final Function<TestTemplateInvocationContextBuilder, TestTemplateInvocationContext> PREPARE_API =
            builder -> builder
                    .withDisplayNamePrefix("Проверка примера через API")
                    .addParameterResolver(String.class, CONFIG.getString("wolframalpha.api.url"), "url")

                    .addExtension(0, new AllureFishTaggingExtension())
                    .addExtension(new AllureHideParametersExtension())
                    .addExtension(new AllureEncodeStepNamesExtension("(?<=Добавление заголовка encoding=).*"))
                    .addExtension(new AllureConcurrentLoggerAttachmentsExtension())
                    .build();
}
