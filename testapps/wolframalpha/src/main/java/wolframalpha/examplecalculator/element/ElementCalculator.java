package wolframalpha.examplecalculator.element;

import wolframalpha.examplecalculator.objectmodel.Element;

import java.util.List;
import java.util.function.BiFunction;

import static wolframalpha.examplecalculator.element.ElementListCreator.getElementList;
import static wolframalpha.examplecalculator.function.ActionFunction.ACTION_ORDER;

public final class ElementCalculator {
    /**
     * Выполняет расчёт примера (подпримера главного примера):
     * Переводит пример в список элементов примера и
     * Выполняет действия между числами примера в соответствии с порядком действия знаков
     * @param subExample - пример (подпример главного примера)
     * @return - возвращает значение примера (подпримера главного примера)
     */
    public static Double getExampleValue(final String subExample) {
        List<Element> elementList = getElementList(subExample);
        Double leftElement;
        Double rightElement;
        Double value;

        //Если получившийся список знаков, выполняемых по порядку, получился не пустой, выполняем расчёт элементов
        //Если пустой, значит в списке элементов лишь одно единственно число, которое и возвращаем
        for (BiFunction action : ACTION_ORDER) {
            int i = 0;

            while (i < elementList.size()) {
                if (elementList.get(i).getValue() == action) {
                    leftElement = (Double) elementList.get(i-1).getValue();
                    rightElement = (Double) elementList.get(i+1).getValue();

                    value = (Double) action.apply(leftElement, rightElement);
                    elementList.get(i-1).setValue(value);

                    elementList.remove(i);
                    elementList.remove(i);

                } else {
                    i++;
                }
            }
        }

        return (Double) elementList.get(0).getValue();
    }
}