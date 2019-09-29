package io.github.quellatalo.fx.tvx;

class StringUtils {
    static String capitalizeFirstLetter(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    static String spacing(String str) {
        StringBuilder sb = new StringBuilder();
        if (str.length() > 0) {
            sb.append(str.charAt(0));
            for (int i = 1; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(' ');
                }
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
