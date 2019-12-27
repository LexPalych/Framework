package com.github.lexpalych.junit5.extensions.allure;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class AllureFishTaggingExtension implements BeforeEachCallback, AfterEachCallback {
  private static final Logger LOGGER = LogManager.getLogger();

  @Override
  public void beforeEach(ExtensionContext context) {
    ThreadContext.put("id", Allure.getLifecycle().getCurrentTestCase().orElseThrow());
    LOGGER.info(context.getDisplayName());
  }

  @Override
  public void afterEach(ExtensionContext context) {
    ThreadContext.clearMap();
  }
}
