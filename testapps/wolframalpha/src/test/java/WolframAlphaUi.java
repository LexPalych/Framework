import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import wolframalpha.WolframAlphaSimpleExampleProvider;
import wolframalpha.objectmodel.WolframAlphaMain;
import wolframalpha.objectmodel.WolframAlphaResult;

class WolframAlphaUi {
    @TestTemplate
    @ExtendWith(WolframAlphaSimpleExampleProvider.class)
    void checkCalculator(final WolframAlphaMain wolframAlphaMain, final String url, final String example, final String result) {
        wolframAlphaMain
                .openUrl(url)
                .fillField("Enter what you want to calculate or know about", example)
                .clickButton("Compute")

                .seePage(WolframAlphaResult.class)
                .checkFieldContainsValue("Result", result);

    }
}
