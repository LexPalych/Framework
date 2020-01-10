//import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaUiBracketExampleProvider;
//import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaUiFunctionExampleProvider;
//import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaUiVeryHardExampleProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaUiSimpleExampleProvider;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaMain;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaResult;

class WolframAlphaUi {
    @TestTemplate
    @ExtendWith({
            WolframAlphaUiSimpleExampleProvider.class/*,
            WolframAlphaUiBracketExampleProvider.class,
            WolframAlphaUiFunctionExampleProvider.class,
            WolframAlphaUiVeryHardExampleProvider.class*/
    })
    void checkCalculator(final WolframAlphaMain wolframAlphaMain, final String url, final String example, final String result) {
        wolframAlphaMain
                .openUrl(url)
                .fillField("Enter what you want to calculate or know about", example)
                .clickButton("Compute")

                .seePage(WolframAlphaResult.class)
                .checkResultValue(result);

    }
}
