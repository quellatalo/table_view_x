package quellatalo.nin.fx;

import java.lang.reflect.Method;

class ReflectionUtils {
    static Method getGetter(Class c, String capitalizedName) {
        Method m = null;
        try {
            m = c.getMethod("get" + capitalizedName);
        } catch (NoSuchMethodException e) {
            try {
                m = c.getMethod("is" + capitalizedName);
            } catch (NoSuchMethodException e1) {
                // not found
            }
        }
        return m;
    }
}
