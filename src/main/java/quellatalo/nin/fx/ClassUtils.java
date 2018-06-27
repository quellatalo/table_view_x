package quellatalo.nin.fx;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ClassUtils {
    static boolean isAssignableFrom(Class type, List<Class<?>> classList) {
        boolean result = false;
        for (Class<?> c : classList) {
            if (c.isAssignableFrom(type)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public static Map<String, Method> getGetters(Class c) {
        Map<String, Method> rs = new HashMap<>();
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (method.getParameterCount() == 0) {
                boolean isGetter = false;
                String name = method.getName();
                if (method.getReturnType() != Void.TYPE) {
                    if (name.startsWith("get")) {
                        name = method.getName().substring(3);
                        isGetter = true;
                    } else if (name.startsWith("is")) {
                        name = name.substring(2);
                        isGetter = true;
                    } else if (name.startsWith("has")) {
                        isGetter = true;
                    }
                }
                if (isGetter) rs.put(name, method);
            }
        }
        return rs;
    }
}
