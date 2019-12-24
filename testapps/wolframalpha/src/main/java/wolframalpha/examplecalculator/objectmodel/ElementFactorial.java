package wolframalpha.examplecalculator.objectmodel;

import static wolframalpha.examplecalculator.objectmodel.Element.TypeElement.FACTORIAL;

public final class ElementFactorial implements Element<Double> {
    private String element;
    private Double value;

    public ElementFactorial(String element) {
        this.element = element;
    }

    public ElementFactorial(String element, Double value) {
        this.element = element;
        this.value = getFactorial(value);
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String getElement() {
        return element;
    }

    @Override
    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public TypeElement getTypeElement() {
        return FACTORIAL;
    }

    /**
     * Находит факториал числа
     * @param number - число
     * @return - возвращает факториал числа типом Double
     */
    private static Double getFactorial(final Double number) {
        if (number < 0)
            throw new ArithmeticException("Отрицательный аргумент факториала");

        if (number % 1 !=0)
            throw new ArithmeticException("Аргумент факториала не является целым числом");

        if (number == 0 || number == 1)
            return 1.0;

        else
            return number * getFactorial(number-1);
    }
}