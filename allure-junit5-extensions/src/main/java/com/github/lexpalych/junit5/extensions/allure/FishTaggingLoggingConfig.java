package com.github.lexpalych.junit5.extensions.allure;

import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.io.IOException;
import java.net.URI;

@Plugin(name = "FishTaggingLoggingConfig", category = ConfigurationFactory.CATEGORY)
@Order(Integer.MAX_VALUE)
public class FishTaggingLoggingConfig extends ConfigurationFactory {
    private static final String LOG_PATH = ConfigFactory.load().getString("logging.path");

    static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.ALL);

        LayoutComponentBuilder layout = builder
                .newLayout("PatternLayout")
                .addAttribute("pattern", "[%X{id}] %m%n")
                .addAttribute("charset", "UTF-8");

        AppenderComponentBuilder fileAppender = builder
                .newAppender("fileAppender", "File")
                .addAttribute("fileName", System.getProperty("user.dir") + LOG_PATH)
                .addAttribute("append", false)
                .addAttribute("immediateFlush", true)
                .addComponent(layout);

        RootLoggerComponentBuilder rootLogger = builder
                .newRootLogger(Level.ALL);

        LoggerComponentBuilder restAssured = builder
                .newLogger("com.github.lexpalych.allure-junit5-extensions.LoggerStreamProvider")
                .add(builder.newAppenderRef("fileAppender"));

        LoggerComponentBuilder stepWrapper = builder
                .newLogger("com.github.lexpalych.allure-steps.StepWrapper")
                .add(builder.newAppenderRef("fileAppender"));

        LoggerComponentBuilder api = builder
                .newLogger("com.github.lexpalych.allure-rest-assured")
                .add(builder.newAppenderRef("fileAppender"));

        LoggerComponentBuilder springJbc = builder
                .newLogger("org.springframework.jdbc")
                .add(builder.newAppenderRef("fileAppender"));

        LoggerComponentBuilder wolframalpha = builder
                .newLogger("com.github.lexpalych.testapps.wolframalpha")
                .add(builder.newAppenderRef("fileAppender"));

        Configuration configuration = builder
                .add(fileAppender)
                .add(rootLogger)
                .add(restAssured)
                .add(stepWrapper)
                .add(api)
                .add(springJbc)
                .add(wolframalpha)
                .build();

        try {
            builder.writeXmlConfiguration(System.out);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return configuration;

    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, String name, URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }
}
