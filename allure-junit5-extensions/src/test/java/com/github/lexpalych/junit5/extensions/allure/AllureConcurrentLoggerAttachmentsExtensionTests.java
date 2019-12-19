package com.github.lexpalych.junit5.extensions.allure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import io.qameta.allure.AllureLifecycle;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

final class AllureConcurrentLoggerAttachmentsExtensionTests {
  @Test
  void defaultConstructorTest() {
    String logPath = "blah-blah";
    String regex = "bite my shiny metal ass";

    System.setProperty("concurrentLoggerLogPath", logPath);
    System.setProperty("concurrentLoggerRegex", regex);

    AllureConcurrentLoggerAttachmentsExtension extension =
        new AllureConcurrentLoggerAttachmentsExtension();

    assertEquals(logPath, extension.getLogPath());
    assertEquals(regex, extension.getRegex());
  }

  @Test
  void nonDefaultConstructorTest() {
    String logPath = "blah-blah-blah";
    String regex = "developersdevelopersdevelopersdevelopers";

    AllureConcurrentLoggerAttachmentsExtension extension =
        new AllureConcurrentLoggerAttachmentsExtension(logPath, regex);

    assertEquals(logPath, extension.getLogPath());
    assertEquals(regex, extension.getRegex());
  }

  @Test
  void afterEachTest() throws IOException {
    String uuid = UUID.randomUUID().toString();
    String regex = "blah-blah-blah";
    String logPath = "/build/log.txt";
    List<String> lines =
        List.of(
            getString(uuid, "Some clever message"),
            getString(uuid, "Another clever message"),
            getString(uuid, "I'm out of clever messages"));

    Path path = Path.of(System.getProperty("user.dir") + logPath);
    FileWriter fileWriter = new FileWriter(path.toString());

    for (String line : lines) {
      fileWriter.write(line + System.lineSeparator());
    }

    AllureConcurrentLoggerAttachmentsExtension extension =
        spy(new AllureConcurrentLoggerAttachmentsExtension(logPath, regex));
    AllureLifecycle lifecycle = mock(AllureLifecycle.class);

    when(lifecycle.getCurrentTestCase()).thenReturn(Optional.of(uuid));
    when(extension.lifecycle()).thenReturn(lifecycle);

    extension.afterEach(null);

    verify(lifecycle, times(1)).getCurrentTestCase();
    verify(lifecycle, times(1))
        .addAttachment(eq("Полный лог"), eq("text/plain"), eq(".txt"), any(byte[].class));
  }

  private String getString(String uuid, String message) {
    return "[" + uuid + "] " + message;
  }
}
