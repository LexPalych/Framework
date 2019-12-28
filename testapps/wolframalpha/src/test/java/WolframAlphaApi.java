import com.github.lexpalych.testapps.wolframalpha.WolframAlphaApiBracketExampleProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import com.github.lexpalych.testapps.wolframalpha.WolframAlphaApiFunctionExampleProvider;
import com.github.lexpalych.testapps.wolframalpha.WolframAlphaApiSimpleExampleProvider;

import static com.github.lexpalych.allure.rest.assured.ApiRequestSteps.apiRequest;

class WolframAlphaApi {
    @TestTemplate
    @ExtendWith({
            WolframAlphaApiSimpleExampleProvider.class,
            WolframAlphaApiBracketExampleProvider.class
    })
    void checkCalculator(final String url, final String example, final String result) {
        apiRequest()
                .addHeader("encoding", "UTF-8")
                .get(url + example)
                .statusCode(200)
                .assertEqualsJsonNumber("instantMath.exactResult", result);

    }

    @TestTemplate
    @ExtendWith({
            WolframAlphaApiFunctionExampleProvider.class
    })
    void checkCalculatorFunction(final String url, final String example, final String result) {
        apiRequest()
                .get(url + example)
                .statusCode(200)
                .assertEqualsJsonNumber("instantMath.approximateResult", result);

    }
}
