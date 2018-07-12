package quellatalo.nin.fx;

import java.lang.reflect.Method;
import java.util.*;

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

    public static SortedMap<String, Method> getGetters(Class c) {
        SortedMap<String, Method> rs = new TreeMap<>();
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

    public static SortedMap<String, Method> getGetters(Class c, boolean displayHashCode, boolean displayClass, boolean displayMapsAndCollections, boolean stringAndPrimitivesOnly, List<Class<?>> forcedDisplayTypes) {
        SortedMap<String, Method> getters = getGetters(c);
        ArrayList<Map.Entry<String, Method>> entrySet = new ArrayList<>(getters.entrySet());
        for (Map.Entry<String, Method> entry : entrySet) {
            if (!isEntryQualified(entry, displayHashCode, displayClass, displayMapsAndCollections, stringAndPrimitivesOnly, forcedDisplayTypes)) {
                getters.remove(entry.getKey());
            }
        }
        return getters;
    }

    public static boolean isNumeric(Class<?> type) {
        return type == Byte.class || type == Character.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class ||
                type == byte.class || type == char.class || type == short.class || type == int.class || type == long.class || type == float.class || type == double.class;
    }

    private static boolean isEntryQualified(Map.Entry<String, Method> set, boolean displayHashCode, boolean displayClass, boolean displayMapsAndCollections, boolean stringAndPrimitivesOnly, List<Class<?>> forcedDisplayTypes) {
        boolean b = false;
        Class<?> propType = set.getValue().getReturnType();
        if (set.getKey().equals("hashCode")) b = displayHashCode;
        else if ((propType.isArray() || Map.class.isAssignableFrom(propType) || Collection.class.isAssignableFrom(propType)))
            b = displayMapsAndCollections;
        else if (set.getKey().equals("Class")) b = displayClass;
        else if (isAssignableFrom(propType, forcedDisplayTypes)) b = true;
        else if (!stringAndPrimitivesOnly) b = true;
        else b = propType.isPrimitive() || propType == String.class;
        return b;
    }
}
