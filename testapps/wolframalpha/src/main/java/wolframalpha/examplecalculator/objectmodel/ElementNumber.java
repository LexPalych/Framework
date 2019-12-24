package wolframalpha.examplecalculator.objectmodel;

import static wolframalpha.examplecalculator.objectmodel.Element.TypeElement.NUMBER;

public final class ElementNumber implements Element<Double> {
    private String element;
    private Double value;

    public ElementNumber(String element) {
        this.element = element;
        this.value = Double.parseDouble(element);
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
        return NUMBER;
    }
}