package com.github.lexpalych.testapps.wolframalpha;

import com.github.lexpalych.extensions.junit5.extensions.allure.LoggerStreamProvider;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import java.io.PrintStream;

public class WolframAlphaRequestSpecification {
    static final RequestSpecification REQUEST_SPEC = new RequestSpecBuilder()
            .addHeader("encoding", "UTF-8")
            .addRequestSpecification(loggingRequestSpec())
            .build();

    static RequestSpecification loggingRequestSpec() {
        PrintStream printStream = LoggerStreamProvider.getLoggerPrintStream();

        return new RequestSpecBuilder()
                .addFilter(RequestLoggingFilter.logRequestTo(printStream))
                .addFilter(ResponseLoggingFilter.logResponseTo(printStream))
                .build();
    }
}
