package quellatalo.nin.fx.advsearch.searchfield;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import quellatalo.nin.fx.advsearch.condition.ICondition;
import quellatalo.nin.fx.advsearch.condition.TextCondition;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class TextSearchField implements ISearchField {
    private static final Function<Control, Object> FUNCTION = control -> ((TextField) control).getText();
    private final UnaryOperator<Object> subjectOperator;

    public TextSearchField(UnaryOperator<Object> subjectOperator) {
        this.subjectOperator = subjectOperator;
    }

    @Override
    public ICondition[] getConditions() {
        return TextCondition.values();
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
