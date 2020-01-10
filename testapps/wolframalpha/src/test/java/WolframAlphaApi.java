import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaApiBracketExampleProvider;
import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaApiVeryComplexExampleProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaApiFunctionExampleProvider;
import com.github.lexpalych.testapps.wolframalpha.providers.WolframAlphaApiSimpleExampleProvider;

import static com.github.lexpalych.allure.rest.assured.ApiRequestSteps.apiRequest;
import static org.apache.http.HttpStatus.SC_OK;

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
                .statusCode(SC_OK)
                .assertEqualsJsonNumber("instantMath.exactResult", result);

    }

    @TestTemplate
    @ExtendWith({
            WolframAlphaApiFunctionExampleProvider.class,
            WolframAlphaApiVeryComplexExampleProvider.class
    })
    void checkCalculatorFunction(final String url, final String example, final String result) {
        apiRequest()
                .get(url + example)
                .statusCode(SC_OK)
                .assertEqualsJsonNumber("instantMath.approximateResult", result);

    }
}
