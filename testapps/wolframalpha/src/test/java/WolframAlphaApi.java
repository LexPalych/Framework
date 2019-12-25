import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import wolframalpha.WolframAlphaApiSimpleExampleProvider;

import java.util.HashMap;
import java.util.Map;

import static com.github.lexpalych.allure.rest.assured.ApiRequestSteps.apiRequest;

class WolframAlphaApi {
    @TestTemplate
    @ExtendWith(WolframAlphaApiSimpleExampleProvider.class)
    void checkCalculator(final String url, final String example, final String result) {
        Map<String, String> map = new HashMap<>();

        apiRequest()
                .get(url + example)
                .statusCode(200)
                .assertEqualsJsonNumber("instantMath.exactResult", result);



    }
}
