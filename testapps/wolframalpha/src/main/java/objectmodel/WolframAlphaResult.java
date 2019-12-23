package objectmodel;

import com.github.lexpalych.allure.webdriver.WebDriverPageObject;
import com.github.lexpalych.junit5.allure.steps.steprepositories.ButtonStepRepository;
import com.github.lexpalych.junit5.allure.steps.steprepositories.FieldStepRepository;

public class WolframAlphaResult extends WebDriverPageObject<WolframAlphaResult> implements FieldStepRepository, ButtonStepRepository {
    private static final String CONTEXT = "//h2[contains(text(), 'Result')]/../..";

    @Override
    public WolframAlphaResult checkFieldContainsValue(final String name, final String value) {
        final String xpath = "//h2[contains(text(), 'Result')]/../..//div//img";
        sendKeys(CONTEXT + "//input[@placeholder='" + name + "']", value);
        return this;
    }
}
