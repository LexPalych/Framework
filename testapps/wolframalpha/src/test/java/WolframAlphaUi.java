import com.github.lexpalych.testapps.wolframalpha.WolframAlphaBracketExampleProvider;
import com.github.lexpalych.testapps.wolframalpha.WolframAlphaFunctionExampleProvider;
import com.github.lexpalych.testapps.wolframalpha.WolframAlphaVeryComplexExampleProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import com.github.lexpalych.testapps.wolframalpha.WolframAlphaSimpleExampleProvider;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaMain;
import com.github.lexpalych.testapps.wolframalpha.objectmodel.WolframAlphaResult;

class WolframAlphaUi {
    @TestTemplate
    @ExtendWith({
            WolframAlphaSimpleExampleProvider.class,
            WolframAlphaBracketExampleProvider.class,
            WolframAlphaFunctionExampleProvider.class,
            WolframAlphaVeryComplexExampleProvider.class
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
