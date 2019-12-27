package com.github.lexpalych.testapps.wolframalpha.examplecalculator.objectmodel;

public interface Element<T> {
    T getValue();

    String getElement();

    void setValue(T value);

    void setElement(String element);

    TypeElement getTypeElement();

    enum TypeElement {
        SIGN,
        NUMBER,
        FUNCTION,
        BRACKET,
        FACTORIAL
    }
}