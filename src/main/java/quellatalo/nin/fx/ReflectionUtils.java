package quellatalo.nin.fx;

import java.lang.reflect.Method;

class ReflectionUtils {
    static Method getGetter(Class c, String fieldName, String capitalizedName) {
        Method m = null;
        try {
            m = c.getMethod("get" + capitalizedName);
        } catch (NoSuchMethodException e) {
            try {
                m = c.getMethod("is" + capitalizedName);
            } catch (NoSuchMethodException e1) {
                if (fieldName.startsWith("is") || fieldName.startsWith("has")) {
                    try {
                        m = c.getMethod(fieldName);
                    } catch (NoSuchMethodException e2) {
                        // not found
                    }
                }
            }
        }
        return m;
    }
}
