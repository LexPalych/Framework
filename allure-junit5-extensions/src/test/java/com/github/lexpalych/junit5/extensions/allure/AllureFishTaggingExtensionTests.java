package com.github.lexpalych.junit5.extensions.allure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.Test;

final class AllureFishTaggingExtensionTests {
  @Test
  void beforeEachTest() {
    String uuid = Allure.getLifecycle().getCurrentTestCase().orElseThrow();

    AllureFishTaggingExtension extension = new AllureFishTaggingExtension();

    extension.beforeEach(null);

    assertEquals(uuid, ThreadContext.get("id"));
  }

  @Test
  void afterEachTest() {
    AllureFishTaggingExtension extension = new AllureFishTaggingExtension();

    extension.beforeEach(null);
    extension.afterEach(null);

    assertNull(ThreadContext.get("id"));
  }
}
