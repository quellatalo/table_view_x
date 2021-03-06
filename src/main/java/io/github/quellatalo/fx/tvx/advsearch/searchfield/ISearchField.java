package io.github.quellatalo.fx.tvx.advsearch.searchfield;

import io.github.quellatalo.fx.tvx.advsearch.condition.ICondition;
import io.github.quellatalo.reflection.ClassUtils;
import javafx.scene.control.Control;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface ISearchField {
    static ISearchField createInstance(Method method) {
        ISearchField searchField;
        Class<?> type = method.getReturnType();
        UnaryOperator<Object> subjectOperator = o -> {
            Object rs = null;
            try {
                rs = method.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return rs;
        };
        if (type == LocalDateTime.class) {
            searchField = new LocalDateTimeSearchField(subjectOperator);
        } else if (ClassUtils.isNumeric(type)) {
            searchField = new NumericSearchField(subjectOperator);
        } else {
            searchField = new TextSearchField(subjectOperator);
        }
        return searchField;
    }

    ICondition[] getConditions();

    UnaryOperator<Object> getSubjectOperator();

    Class<?> getValueControlType();

    Function<Control, Object> getValueFunction();
}
