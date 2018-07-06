package quellatalo.nin.fx;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassUtils {
    public static boolean isAssignableFrom(Class type, List<Class<?>> classList) {
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

    public static boolean isNumeric(Class<?> type) {
        return type == Byte.class || type == Character.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class ||
                type == byte.class || type == char.class || type == short.class || type == int.class || type == long.class || type == float.class || type == double.class;
    }

    public static boolean isEntryQualified(Map.Entry<String, Method> set, boolean displayHashCode, boolean displayClass, boolean displayMapsAndCollections, boolean stringAndPrimitivesOnly, List<Class<?>> forcedDisplayTypes) {
        boolean b = true;
        Class<?> propType = set.getValue().getReturnType();
        if (set.getKey().equals("hashCode") && !displayHashCode) b = false;
        else if ((Map.class.isAssignableFrom(propType) || Collection.class.isAssignableFrom(propType)) && !displayMapsAndCollections)
            b = false;
        else if (set.getKey().equals("Class") && !displayClass) b = false;
        else if (stringAndPrimitivesOnly &&
                (
                        !(propType.isPrimitive() || propType == String.class) &&
                                !ClassUtils.isAssignableFrom(propType, forcedDisplayTypes)
                ) ||
                (set.getKey().equals("Class") && !displayClass) ||
                (set.getKey().equals("hashCode") && !displayHashCode))
            b = false;
        return b;
    }
}
