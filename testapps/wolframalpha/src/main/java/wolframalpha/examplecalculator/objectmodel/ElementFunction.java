package wolframalpha.examplecalculator.objectmodel;

import static wolframalpha.examplecalculator.objectmodel.Element.TypeElement.FUNCTION;
import static wolframalpha.examplecalculator.function.MathFunction.getFunctionValue;

public final class ElementFunction implements Element<Double> {
    private String element;
    private Double value;

    public ElementFunction(String element) {
        this.element = element;
        this.value = getFunctionValue(element);
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
        return FUNCTION;
    }
}