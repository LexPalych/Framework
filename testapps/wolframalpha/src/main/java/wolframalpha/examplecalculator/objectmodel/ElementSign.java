package wolframalpha.examplecalculator.objectmodel;

import java.util.function.BiFunction;

import static wolframalpha.examplecalculator.objectmodel.Element.TypeElement.SIGN;
import static wolframalpha.examplecalculator.function.ActionFunction.getMathAction;

public final class ElementSign implements Element<BiFunction> {
    private String element;
    private BiFunction value;

    public ElementSign(String element) {
        this.element = element;
        this.value = getMathAction(element);
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