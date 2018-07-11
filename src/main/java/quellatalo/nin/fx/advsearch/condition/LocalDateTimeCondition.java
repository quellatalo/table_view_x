package quellatalo.nin.fx.advsearch.condition;

import java.time.LocalDateTime;
import java.util.function.BiPredicate;

public enum LocalDateTimeCondition implements ICondition {
    EQUALS((o, o2) -> ((LocalDateTime) o).isEqual((LocalDateTime) o2)),
    LESSER_THAN((o, o2) -> ((LocalDateTime) o).isBefore((LocalDateTime) o2)),
    LESSER_THAN_OR_EQUAL(LESSER_THAN.getBiPredicate().or(EQUALS.getBiPredicate())),
    GREATER_THAN((o, o2) -> ((LocalDateTime) o).isAfter((LocalDateTime) o2)),
    GREATER_THAN_OR_EQUAL(GREATER_THAN.getBiPredicate().or(EQUALS.getBiPredicate())),
    DIFFERS((o, o2) -> !((LocalDateTime) o).isEqual((LocalDateTime) o2));
    private final BiPredicate<Object, Object> biPredicate;

    LocalDateTimeCondition(BiPredicate<Object, Object> biPredicate) {
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
