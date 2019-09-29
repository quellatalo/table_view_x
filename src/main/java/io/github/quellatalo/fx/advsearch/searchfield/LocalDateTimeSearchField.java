package io.github.quellatalo.fx.advsearch.searchfield;

import io.github.quellatalo.fx.advsearch.condition.ICondition;
import io.github.quellatalo.fx.advsearch.condition.LocalDateTimeCondition;
import io.github.quellatalo.fx.datetime.DateTimePicker;
import javafx.scene.control.Control;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class LocalDateTimeSearchField implements ISearchField {
    private static final Function<Control, Object> FUNCTION = control -> ((DateTimePicker) control).getDateTimeValue();
    private final UnaryOperator<Object> subjectOperator;

    public LocalDateTimeSearchField(UnaryOperator<Object> subjectOperator) {
        this.subjectOperator = subjectOperator;
    }

    @Override
    public ICondition[] getConditions() {
        return LocalDateTimeCondition.values();
    }

    @Override
    public UnaryOperator<Object> getSubjectOperator() {
        return subjectOperator;
    }

    @Override
    public Class<?> getValueControlType() {
        return DateTimePicker.class;
    }

    @Override
    public Function<Control, Object> getValueFunction() {
        return FUNCTION;
    }
}
