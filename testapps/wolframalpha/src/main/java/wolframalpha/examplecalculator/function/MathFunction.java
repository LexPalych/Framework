package wolframalpha.examplecalculator.function;

import wolframalpha.examplecalculator.ExampleException;

import java.util.function.Function;

import static wolframalpha.examplecalculator.element.ElementCalculator.getExampleValue;
import static java.lang.StrictMath.PI;

public final class MathFunction {
    private static final Double RAD = PI/180;

    /**
     * Нахождит значение тригонометрической функции
     */
    public static double getFunctionValue(final String example) {
        String functionName = getFunctionName(example);
        String functionArgument = getFunctionArgument(example);

        Function<Double, Double> mathFunction = getMathFunction(functionName);
        Double functionValue = getExampleValue(functionArgument);

        return mathFunction.apply(functionValue);
    }

    /**
     * Находит в переданном примере первую функцию и выцепляет её имя
     * @param example - пример
     * @return - возвращет строку с именем функции
     */
    private static String getFunctionName(final String example) {
        int i = 0;

        while (example.charAt(i) != '(') {
            i++;
        }

        return example.substring(0, i);
    }

    /**
     * Находит в переданном примере перую функцию и выцепляет её аргумент
     * @param example - пример
     * @return - возвращет строку с аргументом функции
     */
    private static String getFunctionArgument(final String example) {
        String functionName = getFunctionName(example);
        return example.substring(functionName.length()+1, example.length()-1);
    }

    /**
     * Распознаёт строку с именем функции и возвращает соответствующую функцию
     * @param functionName - имя функции
     * @return - возвращает функцию для расчётов
     */
    private static Function<Double, Double> getMathFunction(final String functionName) {
        switch (functionName) {
            case "sin":
                return value -> Math.sin(value * RAD);

            case "cos":
                return value -> Math.cos(value * RAD);

            case "tan":
                return value -> Math.tan(value * RAD);

            case "asin":
                return value -> Math.asin(value) / RAD;

            case "acos":
                return value -> Math.acos(value) / RAD;

            case "atan":
                return value -> Math.atan(value) / RAD;

            case "sinh":
                return value -> Math.sinh(value * RAD);

            case "cosh":
                return value -> Math.cosh(value * RAD);

            case "tanh":
                return value -> Math.tanh(value * RAD);

            case "exp":
                return Math::exp;

            case "abs":
                return Math::abs;

            case "sqrt":
                return Math::sqrt;

            case "ln":
                return value -> {
                    if (value > 0)
                        return Math.log(value);
                    else
                        throw new ArithmeticException("Аргумент логарифма должен быть положительным");
                };

            default:
                throw new ExampleException("Неизвестная функция");
        }
    }
}