package com.github.lexpalych.testapps.wolframalpha.examplecalculator;

import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleValidation.assertExample;
import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.element.ElementCalculator.getExampleValue;

public final class ExampleCalculator {
    /**
     * Проверяет пример на корректность записи и производит его вычисление
     * Вынесен отдельно, чтобы в итоговой библиотеке был всего один публичный метод
     * @param example - пример
     * @return - возвращает итоговый результат вычислений
     */
    public static Double calculate(final String example) {
        assertExample(example);
        return getExampleValue(example);
    }
}