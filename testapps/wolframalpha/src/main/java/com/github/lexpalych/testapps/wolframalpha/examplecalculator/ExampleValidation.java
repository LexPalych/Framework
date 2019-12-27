package com.github.lexpalych.testapps.wolframalpha.examplecalculator;

import org.junit.jupiter.api.function.Executable;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для валидации примера на корректность записи
 */
final class ExampleValidation {
    /**
     * Проверяет выражение на правильность записи
     */
    static void assertExample(final String expression) {

        List<Character> expressionCharList = expression
                .chars()
                .mapToObj(c -> (char) c)
                .collect(toList());

        List<List<Executable>> executableList = List.of(
                checkIncorrectSigns(expressionCharList),
                checkBracketAmount(expressionCharList),
                checkBracketOrder(expressionCharList),
                checkArgumentBracket(expressionCharList),
                checkExpressionInBracketIsCorrect(expressionCharList),
                checkSymbolBeforeFunction(expressionCharList),
                checkSymbolAfterFunction(expressionCharList),
                checkSeveralSignConsecutive(expressionCharList),
                checkFirstSymbol(expressionCharList),
                checkLastSymbol(expressionCharList),
                checkNoOnlyLetter(expressionCharList)
        );

        assertAll(getExecutableList(executableList));
    }

    /**
     * Помещает список списков с Executable`ми в один большой список
     */
    private static List<Executable> getExecutableList(List<List<Executable>> lists) {
        List<Executable> executableList = new LinkedList<>();

        for (List<Executable> executable : lists) {
            executableList.addAll(executable);
        }

        return executableList;
    }

    /**
     * Проверяет выражение на правильность записи
     */
    private static List<Executable> checkIncorrectSigns(final List<Character> expressionCharList) {
        List<Character> signList = List.of('(', ')', '+', '-', '*', '/', '!', '^', '.', ' ', ',');

        return expressionCharList
                .stream()
                .map(symbol -> (Executable) () ->
                        assertTrue(isLetter(symbol) || isDigit(symbol) || signList.contains(symbol),
                                "Симол " + symbol + " является некорректным")
                )
                .collect(toList());
    }

    /**
     * Проверяет совпадение в количестве открывающий и закрывающих скобочек
     */
    private static List<Executable> checkBracketAmount(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();

        int openingBracket = (int) expressionCharList
                .stream()
                .filter(symbol -> symbol.equals('('))
                .count();

        int closingBracket = (int) expressionCharList
                .stream()
                .filter(symbol -> symbol.equals(')'))
                .count();

        executableList.add(()-> assertEquals(openingBracket, closingBracket, "Неверное количество скобочек"));

        return executableList;
    }

    /**
     * Проверяет наличие закрывающей скобочки перед открыающей
     */
    private static List<Executable> checkBracketOrder(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();
        int bracket = 0;

        for (int i = 0; i < expressionCharList.size(); i++) {
            int j = i;
            char symbol = expressionCharList.get(j);

            if (symbol == '(') {
                bracket++;

            } else if (symbol == ')') {
                bracket--;
            }

            if (bracket < 0) {
                executableList.add(() -> fail("Закрывающая скобочка стоит перед открывающей (позиция " + j + ")"));
                bracket = 0;
            }
        }

        return executableList;
    }

    /**
     * Проверяет наличие скобочки перед выражением аргумента функции
     */
    private static List<Executable> checkArgumentBracket(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();

        for (int i = 1; i < expressionCharList.size(); i++) {
            int j = i;

            executableList.add(() ->
                    assertFalse(isDigit(expressionCharList.get(j)) && isLetter(expressionCharList.get(j-1)),
                            "Отсутствует скобочка перед аргументом функции на позиции " + j)
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие в скобочках какого-либо выражения
     */
    private static List<Executable> checkExpressionInBracketIsCorrect(final List<Character> expressionCharList) {
        List<Character> signList = List.of('+', '-', '*', '/', '^', '.', '(');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 1; i < expressionCharList.size(); i++) {
            int j = i;

            executableList.add(() ->
                    assertFalse(expressionCharList.get(j) == ')' && signList.contains(expressionCharList.get(j)),
                            "Отсутствует выражение перед скобочкой на позиции " + j)
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие перед функцией некорректных символов
     */
    private static List<Executable> checkSymbolBeforeFunction(final List<Character> expressionCharList) {
        List<Character> signList = List.of(')', '.', '!');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 1; i < expressionCharList.size(); i++) {
            char currentSymbol = expressionCharList.get(i);
            char prevSymbol = expressionCharList.get(i-1);

            executableList.add(() ->
                    assertFalse(isLetter(currentSymbol) && ((isDigit(prevSymbol) || signList.contains(prevSymbol))),
                            "Некорректный символ (" + prevSymbol + ") перед функцией")
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие после имени функции некорректных символов
     */
    private static List<Executable> checkSymbolAfterFunction(final List<Character> expressionCharList) {
        List<Character> signList = List.of(')', '.', '!', '+', '-', '*', '/', '^');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 0; i < expressionCharList.size()-1; i++) {
            char currentSymbol = expressionCharList.get(i);
            char nextSymbol = expressionCharList.get(i+1);

            executableList.add(() ->
                    assertFalse(isLetter(currentSymbol) && signList.contains(nextSymbol),
                            "Некорректный символ (" + nextSymbol + ") после имени функции")
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие подряд стоящих знаков
     */
    private static List<Executable> checkSeveralSignConsecutive(final List<Character> expressionCharList) {
        List<Character> signList = List.of('+', '-', '*', '/', '^', '.');
        List<Executable> executableList = new LinkedList<>();

        for (int i = 0; i < expressionCharList.size()-1; i++) {
            int j = i;
            char currentSymbol = expressionCharList.get(j);
            char nextSymbol = expressionCharList.get(j+1);

            executableList.add(() ->
                    assertFalse(signList.contains(currentSymbol) && signList.contains(nextSymbol),
                            "Несколько знаков подряд начиная с позиции " + j)
            );
        }

        return executableList;
    }

    /**
     * Проверяет наличие некорректных символов в начале выражения
     */
    private static List<Executable> checkFirstSymbol(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();

        List<Character> signList = List.of('+', '*', '/', '!', '^', '.');

        executableList.add(() -> assertFalse(signList.contains(expressionCharList.get(0)),
                "Некорректный первый символ выражения"));

        return executableList;
    }

    /**
     * Проверяет наличие некорректных символов в конце выражения
     */
    private static List<Executable> checkLastSymbol(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();
        List<Character> signList = List.of('+', '-', '*', '/', '^', '.');

        executableList.add(() -> assertFalse(signList.contains(expressionCharList.get(expressionCharList.size()-1)),
                "Некорректный последний символ выражения"));

        return executableList;
    }

    /**
     * Проверяет наличие в выражении чисел
     */
    private static List<Executable> checkNoOnlyLetter(final List<Character> expressionCharList) {
        List<Executable> executableList = new LinkedList<>();
        executableList.add(() -> assertTrue((int) expressionCharList.stream().filter(Character::isDigit).count() != 0,
                "В выражении отсутствуют числа"));

        return executableList;
    }

}