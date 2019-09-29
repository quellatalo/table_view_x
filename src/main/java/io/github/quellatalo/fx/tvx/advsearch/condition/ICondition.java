package io.github.quellatalo.fx.tvx.advsearch.condition;

import io.github.quellatalo.reflection.ClassUtils;

import java.time.LocalDateTime;
import java.util.function.BiPredicate;

public interface ICondition {
    static ICondition[] getGeneralConditions(Class<?> type) {
        return type == LocalDateTime.class ? LocalDateTimeCondition.values() : ClassUtils.isNumeric(type) ? NumericCondition.values() : TextCondition.values();
    }

    boolean test(Object subject, Object value);

    BiPredicate<Object, Object> getBiPredicate();
}
