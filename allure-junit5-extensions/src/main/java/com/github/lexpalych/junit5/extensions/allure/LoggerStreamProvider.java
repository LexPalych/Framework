package com.github.lexpalych.junit5.extensions.allure;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.PrintStream;

public class LoggerStreamProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final PrintStream LOGGER_PRINT_STREAM = IoBuilder.forLogger(LOGGER).buildPrintStream();

    public static PrintStream getLoggerPrintStream() {
        return LOGGER_PRINT_STREAM;
    }
}
