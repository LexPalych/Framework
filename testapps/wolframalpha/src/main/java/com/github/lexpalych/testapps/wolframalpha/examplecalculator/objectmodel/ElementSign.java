package com.github.lexpalych.testapps.wolframalpha.examplecalculator.objectmodel;

import com.github.lexpalych.testapps.wolframalpha.examplecalculator.function.ActionFunction;

import java.util.function.BiFunction;

import static com.github.lexpalych.testapps.wolframalpha.examplecalculator.objectmodel.Element.TypeElement.SIGN;

public final class ElementSign implements Element<BiFunction> {
    private String element;
    private BiFunction value;

    public ElementSign(String element) {
        this.element = element;
        this.value = ActionFunction.getMathAction(element);
    }

    @Override
    public BiFunction getValue() {
        return value;
    }

    @Override
    public String getElement() {
        return element;
    }

    @Override
    public void setValue(BiFunction value) {
        this.value = value;
    }

    @Override
    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public TypeElement getTypeElement() {
        return SIGN;
    }
}