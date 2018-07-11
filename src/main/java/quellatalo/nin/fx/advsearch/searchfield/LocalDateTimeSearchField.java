package quellatalo.nin.fx.advsearch.searchfield;

import javafx.scene.control.Control;
import quellatalo.nin.fx.advsearch.condition.ICondition;
import quellatalo.nin.fx.advsearch.condition.LocalDateTimeCondition;
import quellatalo.nin.fx.datetime.DateTimePicker;

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
