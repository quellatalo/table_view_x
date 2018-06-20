package quellatalo.nin.fx;

import java.util.List;

class ClassUtils {
    static boolean isAssignableFrom(Class type, List<Class> classList) {
        boolean result = false;
        for (Class c : classList) {
            if (c.isAssignableFrom(type)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
