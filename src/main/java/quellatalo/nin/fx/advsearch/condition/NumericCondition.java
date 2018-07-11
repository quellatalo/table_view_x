package quellatalo.nin.fx.advsearch.condition;

import java.util.function.BiPredicate;

public enum NumericCondition implements ICondition {
    EQUALS((o, o2) -> (double) o == (double) o2),
    LESSER_THAN((o, o2) -> (double) o < (double) o2),
    LESSER_THAN_OR_EQUAL((o, o2) -> (double) o <= (double) o2),
    GREATER_THAN((o, o2) -> (double) o > (double) o2),
    GREATER_THAN_OR_EQUAL((o, o2) -> (double) o >= (double) o2),
    DIFFERS((o, o2) -> (double) o != (double) o2);
    private final BiPredicate<Object, Object> biPredicate;

    NumericCondition(BiPredicate<Object, Object> biPredicate) {
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
