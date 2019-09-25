package io.github.quellatalo.fx.advsearch.condition;

import java.util.function.BiPredicate;

public enum TextCondition implements ICondition {
    CONTAINS((o, o2) -> ((String) o).contains((String) o2)),
    EQUALS(Object::equals),
    STARTS_WITH((o, o2) -> ((String) o).startsWith((String) o2)),
    ENDS_WITH((o, o2) -> ((String) o).endsWith((String) o2)),
    DIFFERS((o, o2) -> !o.equals(o2)),
    NOT_CONTAINS((o, o2) -> !(((String) o).contains((String) o2)));
    private final BiPredicate<Object, Object> biPredicate;

    TextCondition(BiPredicate<Object, Object> biPredicate) {
        this.biPredicate = biPredicate;
    }

    @Override
    public boolean test(Object subject, Object value) {
        return biPredicate.test(subject, value);
    }

    @Override
    public BiPredicate<Object, Object> getBiPredicate() {
        return biPredicate;
    }
}
