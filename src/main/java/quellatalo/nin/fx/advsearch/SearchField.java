package quellatalo.nin.fx.advsearch;

import java.util.function.UnaryOperator;

public class SearchField {
    private final Class<?> type;
    private final UnaryOperator<Object> subject;

    public SearchField(Class<?> type, UnaryOperator<Object> subject) {
        this.type = type;
        this.subject = subject;
    }

    public Class<?> getType() {
        return type;
    }

    public UnaryOperator<Object> getSubject() {
        return subject;
    }
}
