package objectmodel;

import com.github.lexpalych.allure.webdriver.WebDriverPageObject;
import com.github.lexpalych.junit5.allure.steps.steprepositories.ButtonStepRepository;
import com.github.lexpalych.junit5.allure.steps.steprepositories.FieldStepRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WolframAlphaResult extends WebDriverPageObject<WolframAlphaResult> implements FieldStepRepository, ButtonStepRepository {
    private static final String CONTEXT = "//h2[contains(text(), 'Result')]/../..";

    @Override
    public WolframAlphaResult checkFieldContainsValue(final String name, final String expectedValue) {
        final String xpath = CONTEXT + "//h2[contains(text(), 'Result')]/../..//div//img";
        final String currentValue = getWebElement(xpath).getAttribute("alt");
        assertTrue(currentValue.equals(expectedValue),
                "Ожидлось значение " + expectedValue + " , а получилось" + currentValue);
        return this;
    }
}
