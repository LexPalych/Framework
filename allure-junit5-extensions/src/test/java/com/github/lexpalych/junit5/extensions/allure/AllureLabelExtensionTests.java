package com.github.lexpalych.junit5.extensions.allure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.TestResult;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.Test;

final class AllureLabelExtensionTests {
  @Test
  void stringConstructorTest() {
    String labelName = "blah-blah-blah";
    String labelValue = "blah";

    AllureLabelExtension extension = new AllureLabelExtension(labelName, labelValue);

    List<Label> actual = extension.getLabelList();

    assertEquals(1, actual.size());

    Label actualLabel = actual.get(0);
    assertEquals(labelName, actualLabel.getName());
    assertEquals(labelValue, actualLabel.getValue());
  }

  @Test
  void labelConstructorTest() {
    String labelName = "blah-blah-blah";
    String labelValue = "blah";

    Label label = getLabel(labelName, labelValue);

    List<Label> expected = List.of(label);

    AllureLabelExtension extension = new AllureLabelExtension(label);

    assertEquals(expected, extension.getLabelList());
  }

  @Test
  void labelListConstructorTest() {
    String labelName = "blah-blah-blah";
    String labelValue = "blah";

    List<Label> expected = List.of(getLabel(labelName, labelValue));

    AllureLabelExtension extension = new AllureLabelExtension(expected);

    assertEquals(expected, extension.getLabelList());
  }

  @Test
  void beforeEachTest() {
    List<Label> labels =
        List.of(getLabel("duck", "quack"), getLabel("dog", "bark"), getLabel("cat", "meow"));

    String uuid = Allure.getLifecycle().getCurrentTestCase().orElseThrow();

    AllureLabelExtension extension = new AllureLabelExtension(labels);

    extension.beforeEach(null);

    AtomicReference<TestResult> testResultAtomicReference = new AtomicReference<>();
    Allure.getLifecycle().updateTestCase(uuid, testResultAtomicReference::set);

    TestResult testResult = testResultAtomicReference.get();

    assertTrue(testResult.getLabels().containsAll(labels));
  }

  private Label getLabel(String name, String value) {
    return new Label().setName(name).setValue(value);
  }
}
