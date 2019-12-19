package com.github.lexpalych.junit5.extensions.allure;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class AllureFishTaggingExtension implements BeforeEachCallback, AfterEachCallback {
  @Override
  public void beforeEach(ExtensionContext context) {
    ThreadContext.put("id", Allure.getLifecycle().getCurrentTestCase().orElseThrow());
  }

  @Override
  public void afterEach(ExtensionContext context) {
    ThreadContext.clearMap();
  }
}
