package com.github.lexpalych.junit5.extensions.allure;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public final class AllureConcurrentLoggerAttachmentsExtension implements AfterEachCallback {
  private final String logPath;
  private final String regex;

  public AllureConcurrentLoggerAttachmentsExtension() {
    this.logPath = System.getProperty("concurrentLoggerLogPath");
    this.regex = System.getProperty("concurrentLoggerRegex");
  }

  public AllureConcurrentLoggerAttachmentsExtension(String logPath, String regex) {
    this.logPath = logPath;
    this.regex = regex;
  }

  @Override
  public void afterEach(ExtensionContext context) throws IOException {
    // Группировка логов по айдишнику
    String groupedLogMessages = getMessagesWithId(lifecycle().getCurrentTestCase().orElseThrow());

    groupedLogMessages = groupedLogMessages.replaceAll(regex, "*****");

    lifecycle()
        .addAttachment("Полный лог", "text/plain", ".txt", groupedLogMessages.getBytes(UTF_8));
  }

  private String getMessagesWithId(String uuid) throws IOException {
    String uuidTemplate = "[" + uuid + "] ";
    return Files.readAllLines(Path.of(System.getProperty("user.dir") + logPath), UTF_8).stream()
        .filter(logMessage -> logMessage.contains(uuidTemplate))
        .map(logMessage -> logMessage.replaceAll("\\[" + uuid + "] ", ""))
        .collect(Collectors.joining("\n"));
  }

  /** Only for testing */
  String getLogPath() {
    return logPath;
  }

  /** Only for testing */
  String getRegex() {
    return regex;
  }

  /** Only for testing */
  AllureLifecycle lifecycle() {
    return Allure.getLifecycle();
  }
}
