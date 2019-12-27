package com.github.lexpalych.testapps.wolframalpha.examplecalculator.function;

import com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleException;

import java.util.List;
import java.util.function.BiFunction;

public final class ActionFunction {
    private static final BiFunction<Double, Double, Double> ADDITIONAL = Double::sum;
    private static final BiFunction<Double, Double, Double> SUBTRACTION = (x, y) -> x - y;
    private static final BiFunction<Double, Double, Double> MULTIPLICATION = (x, y) -> x * y;
    private static final BiFunction<Double, Double, Double> DIVISION = (x, y) -> x / y;
    private static final BiFunction<Double, Double, Double> EXPONENTIATION = Math::pow;

    public static final List<BiFunction> ACTION_ORDER =  List.of(EXPONENTIATION, DIVISION, MULTIPLICATION, SUBTRACTION, ADDITIONAL);

    public static BiFunction<Double, Double, Double> getMathAction(final String sign) {
        switch (sign) {
            case "+":
                return ADDITIONAL;

            case "-":
                return SUBTRACTION;

            case "*":
                return MULTIPLICATION;

            case "/":
                return DIVISION;

            case "^":
                return EXPONENTIATION;

            default:
                throw new ExampleException("Неизвестный знак дейстия");

        }
    }

}