package wolframalpha.objectmodel;

import com.github.lexpalych.allure.webdriver.WebDriverPageObject;
import com.github.lexpalych.junit5.allure.steps.steprepositories.ButtonStepRepository;
import com.github.lexpalych.junit5.allure.steps.steprepositories.FieldStepRepository;

public class WolframAlphaMain extends WebDriverPageObject<WolframAlphaMain> implements FieldStepRepository, ButtonStepRepository {
    private static final String CONTEXT = "//input[@placeholder='Enter what you want to calculate or know about']/../../../../../..";

    @Override
    public WolframAlphaMain clickButton(final String name) {
        click(CONTEXT + "//button[@aria-label='" + name + "']");
        return this;
    }

    @Override
    public WolframAlphaMain fillField(final String name, final String value) {
        sendKeys(CONTEXT + "//input[@placeholder='" + name + "']", value);
        return this;
    }
}
