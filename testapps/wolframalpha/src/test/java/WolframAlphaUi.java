import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

public class WolframAlphaUi {
    @TestTemplate
    @ExtendWith(WolframAlphaCalculatorUiProvider.class)
    void checkCalculator(final String url, final String value) {

    }
}
