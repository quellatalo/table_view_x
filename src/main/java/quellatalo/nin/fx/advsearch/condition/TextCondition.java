package quellatalo.nin.fx.advsearch.condition;

public enum TextCondition implements ICondition {
    CONTAINS,
    EQUALS,
    STARTS_WITH,
    ENDS_WITH,
    DIFFERS,
    NOT_CONTAINS;

    public static boolean generateBoolean(String subject, TextCondition condition, String value) {
        boolean b;
        switch (condition) {
            case EQUALS:
                b = subject.equals(value);
                break;
            case STARTS_WITH:
                b = subject.startsWith(value);
                break;
            case ENDS_WITH:
                b = subject.endsWith(value);
                break;
            case CONTAINS:
                b = subject.contains(value);
                break;
            case DIFFERS:
                b = !subject.equals(value);
                break;
            case NOT_CONTAINS:
                b = !subject.contains(value);
                break;
            default:
                b = false;
                break;
        }
        return b;
    }
}
