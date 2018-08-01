import org.junit.Test;
import quellatalo.nin.fx.ClassUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

public class ClassUtilsTest {
    @Test
    public void test() throws NoSuchMethodException {
        SortedMap<String, Method> getters = ClassUtils.getGetters(Sample.class, true, true, true, true);
        SortedMap<String, Method> expectation = new TreeMap<>();
        expectation.put("getId", Sample.class.getMethod("getId"));
        expectation.put("getName", Sample.class.getMethod("getName"));
//        expectation.put("getTime", Sample.class.getMethod("getTime"));
        expectation.put("getClass", Sample.class.getMethod("getClass"));
        expectation.put("hashCode", Sample.class.getMethod("hashCode"));
        expectation.put("getArgs", Sample.class.getMethod("getArgs"));
        expectation.put("getList", Sample.class.getMethod("getList"));
        assert getters.values().containsAll(expectation.values()) && getters.keySet().size() == expectation.keySet().size();

        getters = ClassUtils.getGetters(Sample.class, true, true, true, true, LocalDateTime.class);
        expectation.clear();
        expectation.put("getId", Sample.class.getMethod("getId"));
        expectation.put("getName", Sample.class.getMethod("getName"));
        expectation.put("getTime", Sample.class.getMethod("getTime"));
        expectation.put("getClass", Sample.class.getMethod("getClass"));
        expectation.put("hashCode", Sample.class.getMethod("hashCode"));
        expectation.put("getArgs", Sample.class.getMethod("getArgs"));
        expectation.put("getList", Sample.class.getMethod("getList"));
        assert getters.values().containsAll(expectation.values()) && getters.keySet().size() == expectation.keySet().size();

        getters = ClassUtils.getGetters(Sample.class, false, false, true, false);
        expectation.clear();
        expectation.put("getId", Sample.class.getMethod("getId"));
        expectation.put("getName", Sample.class.getMethod("getName"));
        expectation.put("getTime", Sample.class.getMethod("getTime"));
//        expectation.put("getClass", Sample.class.getMethod("getClass"));
//        expectation.put("hashCode", Sample.class.getMethod("hashCode"));
        expectation.put("getArgs", Sample.class.getMethod("getArgs"));
        expectation.put("getList", Sample.class.getMethod("getList"));
        assert getters.values().containsAll(expectation.values()) && getters.keySet().size() == expectation.keySet().size();

        getters = ClassUtils.getGetters(Sample.class, false, true, false, false);
        expectation.clear();
        expectation.put("getId", Sample.class.getMethod("getId"));
        expectation.put("getName", Sample.class.getMethod("getName"));
        expectation.put("getTime", Sample.class.getMethod("getTime"));
        expectation.put("getClass", Sample.class.getMethod("getClass"));
//        expectation.put("hashCode", Sample.class.getMethod("hashCode"));
//        expectation.put("getArgs", Sample.class.getMethod("getArgs"));
//        expectation.put("getList", Sample.class.getMethod("getList"));
        assert getters.values().containsAll(expectation.values()) && getters.keySet().size() == expectation.keySet().size();
    }
}
