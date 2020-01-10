package com.github.lexpalych.testapps.wolframalpha.examplecalculator.objectmodel;

import com.github.lexpalych.testapps.wolframalpha.examplecalculator.function.MathFunction;

import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.objectmodel.Element.TypeElement.FUNCTION;

public final class ElementFunction implements Element<Double> {
    private String element;
    private Double value;

    public ElementFunction(String element) {
        this.element = element;
        this.value = MathFunction.getFunctionValue(element);
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