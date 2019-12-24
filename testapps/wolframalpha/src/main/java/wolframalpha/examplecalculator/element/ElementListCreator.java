package wolframalpha.examplecalculator.element;

import wolframalpha.examplecalculator.objectmodel.Element;
import wolframalpha.examplecalculator.objectmodel.ElementFactorial;
import wolframalpha.examplecalculator.objectmodel.ElementNumber;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static wolframalpha.examplecalculator.element.ElementCreator.getElementCreator;
import static wolframalpha.examplecalculator.element.ElementListCreator.ElementListReplacer.replaceElementList;
import static wolframalpha.examplecalculator.objectmodel.Element.TypeElement.FACTORIAL;
import static wolframalpha.examplecalculator.objectmodel.Element.TypeElement.SIGN;

final class ElementListCreator {
    /**
     * Разбивает пример (подпример главного примера) на элементы:
     * Числа, знаки математических действий, выражения в скобках, функции
     * Значения таких элементов, как выражения в скобках и функции, рекурсивно высчитываются
     * Помещает полученные элементы в список для дальнейшего расчёта значения примера (подпримера главного примера)
     * @param subExample - пример (подпример главного примера)
     * @return - возвращает список элементов примера, состоящий из числовых значений и знаков (лямбда-функций) между ними
     */
    static List<Element> getElementList(final String subExample) {
        List<Element> elementList = new LinkedList<>();
        Element element;
        int i = 0;

        while (i < subExample.length()) {
            char currentSymbol = subExample.charAt(i);

            //В зависимости от того, какой текущий символ, выбирается функция, которая создаёт элемент примера
            Function<String, Element> elementCreator = getElementCreator(currentSymbol);
            element = elementCreator.apply(subExample.substring(i));
            elementList.add(element);

            //Итератор переносится на индекс символа, стоящего сразу после последнего символа элемента примера
            i += element.getElement().length();
        }

        return replaceElementList(elementList);
    }

    static class ElementListReplacer {
        /**
         * Подпрвляет "сырой" список элементов, убирая "!" во всём списке и "-" в начале списка
         * @param rowElementList - "сырой" список элементов
         * @return - полностью исправленный список элементов
         */
        static List<Element> replaceElementList(final List<Element> rowElementList) {
            return replaceFactorialElement(replaceFirstElement(rowElementList));
        }

        /**
         * Проверяет, есть ли в начале списка элемент типа SIGN (знак минус "-")
         * Если есть, то в начало списка помещается ноль ("0")
         * Нужно для сохранения принципа "число-знак-число-...-знак-...-число"
         * @param elementList - "сырой" список элементов
         * @return - исправленный список элементов
         */
        private static List<Element> replaceFirstElement(final List<Element> elementList) {
            if (elementList.get(0).getTypeElement() == SIGN) {
                elementList.add(0, new ElementNumber("0"));
            }

            return elementList;
        }

        /**
         * Проверяет, есть ли в "сыром" списке элементов знак факториала "!"
         * Если есть, заменяет предыдущий элемент элементом типа FACTORIAL
         * Знак "!" перемещается к новому созданному элементу и затирается в "сыром" списке элементов
         * Нужно для сохранения принципа "число-знак-число-...-знак-...-число"
         * @param elementList - "сырой" список элементов
         * @return - исправленный список элементов
         */
        private static List<Element> replaceFactorialElement(final List<Element> elementList) {
            int i = 0;

            while (i < elementList.size()) {
                if (elementList.get(i).getTypeElement() == FACTORIAL) {
                    String element = elementList.get(i-1).getElement() + elementList.get(i).getElement();
                    Double value = (Double) elementList.get(i-1).getValue();

                    elementList.set(i-1, new ElementFactorial(element, value));
                    elementList.remove(i);

                } else {
                    i++;
                }
            }

            return elementList;
        }
    }

}