package wolframalpha.objectmodel;

import com.github.lexpalych.allure.webdriver.WebDriverPageObject;
import com.github.lexpalych.junit5.allure.steps.steprepositories.FieldStepRepository;
import io.qameta.allure.Step;

import static java.lang.Double.parseDouble;
import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WolframAlphaResult extends WebDriverPageObject<WolframAlphaResult> implements FieldStepRepository<WolframAlphaResult> {
    private static final String CONTEXT =
            "(//h2[contains(text(), 'Result')]/../.. | //h2[contains(text(), 'Exact result')]/../.. | //h2[contains(text(), 'Decimal')]/../..)";
    private static final Double INACCURACY = 0.00000000000001;

    @Step("Проверка результата ычисления {expectedValue}")
    public WolframAlphaResult checkResultValue(final String expectedValue) {
        final String xpath = CONTEXT  + "//h2/../..//div//img";
        final String currentValue = getWebElement(xpath).getAttribute("alt");

        assertTrue(abs(parseDouble(currentValue) - parseDouble(expectedValue)) < INACCURACY,
                "Ожидлось значение " + expectedValue + " , а получилось " + currentValue);

        return this;
    }
}
