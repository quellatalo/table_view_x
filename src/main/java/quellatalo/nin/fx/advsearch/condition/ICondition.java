package quellatalo.nin.fx.advsearch.condition;

import quellatalo.nin.fx.ClassUtils;

import java.time.LocalDateTime;
import java.util.function.BiPredicate;

public interface ICondition {
    static ICondition[] getGeneralConditions(Class<?> type) {
        return type == LocalDateTime.class || ClassUtils.isNumeric(type) ? NumericCondition.values() : TextCondition.values();
    }

    boolean test(Object subject, Object value);

    BiPredicate<Object, Object> getBiPredicate();
}
