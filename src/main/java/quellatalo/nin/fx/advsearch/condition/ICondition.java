package quellatalo.nin.fx.advsearch.condition;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import quellatalo.nin.fx.ClassUtils;
import quellatalo.nin.fx.datetime.DateTimePicker;

import java.time.LocalDateTime;

public interface ICondition {
    static ICondition[] getConditions(Class<?> type) {
        return type == LocalDateTime.class || ClassUtils.isNumeric(type) ? NumericCondition.values() : TextCondition.values();
    }

    static boolean generateBoolean(Object subject, ICondition condition, Control value) {
        boolean b;
        Class<?> type = subject.getClass();
        if (ClassUtils.isNumeric(type)) {
            b = NumericCondition.generateBoolean(((Number) subject).doubleValue(), (NumericCondition) condition, Double.parseDouble(((TextField) value).getText()));
        } else if (type == LocalDateTime.class) {
            b = NumericCondition.generateBoolean((LocalDateTime) subject, (NumericCondition) condition, ((DateTimePicker) value).getDateTimeValue());
        } else {
            b = TextCondition.generateBoolean(subject.toString().toLowerCase(), (TextCondition) condition, ((TextField) value).getText().toLowerCase());
        }
        return b;
    }
}
