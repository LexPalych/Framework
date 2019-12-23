import objectmodel.WolframAlphaMain;
import objectmodel.WolframAlphaResult;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

class WolframAlphaUi {
    @TestTemplate
    @ExtendWith(WolframAlphaCalculatorUiProvider.class)
    void checkCalculator(final WolframAlphaMain wolframAlphaMain, final String url, final String example, final String result) {
        wolframAlphaMain
                .openUrl(url)
                .fillField("Enter what you want to calculate or know about", example)
                .clickButton("Compute")

                .seePage(WolframAlphaResult.class)
                .checkFieldContainsValue("Result", result);

    }
}
