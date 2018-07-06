package quellatalo.nin.fx.advsearch;

import java.time.LocalDateTime;

public enum NumericCondition implements ICondition {
    EQUALS,
    LESSER_THAN,
    LESSER_THAN_OR_EQUAL,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    NOT_EQUAL;

    public static boolean generateBoolean(double subject, NumericCondition condition, double value) {
        boolean b;
        switch (condition) {
            case EQUALS:
                b = subject == value;
                break;
            case GREATER_THAN:
                b = subject > value;
                break;
            case GREATER_THAN_OR_EQUAL:
                b = subject >= value;
                break;
            case LESSER_THAN:
                b = subject < value;
                break;
            case LESSER_THAN_OR_EQUAL:
                b = subject <= value;
                break;
            case NOT_EQUAL:
            default:
                b = subject != value;
        }
        return b;
    }

    public static boolean generateBoolean(LocalDateTime subject, NumericCondition condition, LocalDateTime value) {
        boolean b;
        switch (condition) {
            case EQUALS:
                b = subject.isEqual(value);
                break;
            case GREATER_THAN:
                b = subject.isAfter(value);
                break;
            case GREATER_THAN_OR_EQUAL:
                b = subject.isAfter(value) || subject.equals(value);
                break;
            case LESSER_THAN:
                b = subject.isBefore(value);
                break;
            case LESSER_THAN_OR_EQUAL:
                b = subject.isBefore(value) || subject.equals(value);
                break;
            case NOT_EQUAL:
            default:
                b = !subject.isEqual(value);
        }
        return b;
    }
}
