package io.github.quellatalo.fx.advsearch.condition;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiPredicate;

public enum ArrayCondition implements ICondition {
    CONTAINS((o, o2) -> {
        boolean rs = false;
        int len = Array.getLength(o);
        if (len > 0) {
            Class<?> memberType = Array.get(o, 0).getClass();
            Object[] newArray = (Object[]) Array.newInstance(memberType, len);
            for (int i = 0; i < len; i++) {
                newArray[i] = Array.get(o, i);
            }
            rs = Arrays.asList(newArray).toString().contains(o2.toString());
            System.out.println(rs);
        }
        return rs;
    }),
    EQUALS(Object::equals),
    STARTS_WITH((o, o2) -> ((String) o).startsWith((String) o2)),
    ENDS_WITH((o, o2) -> ((String) o).endsWith((String) o2)),
    DIFFERS((o, o2) -> !o.equals(o2)),
    NOT_CONTAINS((o, o2) -> !(((String) o).contains((String) o2)));
    private final BiPredicate<Object, Object> biPredicate;

    ArrayCondition(BiPredicate<Object, Object> biPredicate) {
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
