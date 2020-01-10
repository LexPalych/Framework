package com.github.lexpalych.testapps.wolframalpha.examplecalculator.element;

import com.github.lexpalych.testapps.wolframalpha.examplecalculator.ExampleException;
import com.github.lexpalych.testapps.wolframalpha.examplecalculator.objectmodel.*;

import java.util.List;
import java.util.function.Function;

import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.objectmodel.Element.TypeElement.*;
import static java.lang.Character.*;

/**
 * Класс методов для распознавания элементов примера
 */
final class ElementCreator {
    /**
     * Определяет функцию для создания элемента примера в зависимости от типа символа примера (знак, число, буква, скобка)
     * @param symbol - текущий символ
     * @return - возвращает функцию создания элемента примера
     */
    static Function<String, Element> getElementCreator(final char symbol) {
        switch (getTypeElement(symbol)) {
            case SIGN:
                return example -> new ElementSign(example.substring(0, 1));

            case FACTORIAL:
                return example -> new ElementFactorial(example.substring(0, 1));

            case FUNCTION:
                return example -> new ElementFunction(example.substring(0, getClosingBracketIndex(example)+1));

            case BRACKET:
                return example -> new ElementBracket(example.substring(0, getClosingBracketIndex(example)+1));

            case NUMBER:
                return example -> {
                    int lastNumeralIndex = 0;

                    while (lastNumeralIndex < example.length() && getTypeElement(example.charAt(lastNumeralIndex)) == Element.TypeElement.NUMBER) {
                        lastNumeralIndex++;
                    }

                    return new ElementNumber(example.substring(0, lastNumeralIndex));
                };

            default:
                throw new ExampleException("Отсутствует условие для символа " + symbol);
        }
    }

    /**
     * Определяет тип символа (знак, число, буква, скобка)
     * @param symbol - символ
     * @return - возвращает тип символа
     */
    private static Element.TypeElement getTypeElement(final char symbol) {
        List<Character> signList = List.of('+', '-', '*', '/', '^');

        if (signList.contains(symbol)) {
            return SIGN;

        } else if (isDigit(symbol) || symbol == '.') {
            return NUMBER;

        } else if (isLetter(symbol)) {
            return FUNCTION;

        } else if (symbol == '(') {
            return BRACKET;

        } else if (symbol == '!') {
            return FACTORIAL;

        } else {
            throw new ExampleException("Неизестный сивол " + symbol);
        }
    }

    /**
     * Находит индекс скобки, закрывающей перую открывающую скобку
     * @param subExample - пример
     * @return - возвращает индекс закрывающей скобочки
     */
    private static int getClosingBracketIndex(final String subExample) {
        int lastBracketIndex = 0;
        int bracketAmount = 0;
        char currentChar;

        do {
            currentChar = subExample.charAt(lastBracketIndex++);

            if (currentChar == '(') {
                bracketAmount++;

            } else if (currentChar == ')') {
                bracketAmount--;
            }

        } while (!(bracketAmount == 0 && currentChar == ')'));

        return --lastBracketIndex;
    }
}