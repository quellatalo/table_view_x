package io.github.quellatalo.fx.tvx.advsearch.searchfield;

import io.github.quellatalo.fx.tvx.advsearch.condition.ICondition;
import io.github.quellatalo.fx.tvx.advsearch.condition.NumericCondition;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class NumericSearchField implements ISearchField {
    private static final Function<Control, Object> FUNCTION = control -> Double.parseDouble(((TextField) control).getText());
    private final UnaryOperator<Object> subjectOperator;

    public NumericSearchField(UnaryOperator<Object> subjectOperator) {
        this.subjectOperator = subjectOperator;
    }

    @Override
    public ICondition[] getConditions() {
        return NumericCondition.values();
    }

    @Override
    public UnaryOperator<Object> getSubjectOperator() {
        return subjectOperator;
    }

    @Override
    public Class<?> getValueControlType() {
        return TextField.class;
    }

    @Override
    public Function<Control, Object> getValueFunction() {
        return FUNCTION;
    }
}
