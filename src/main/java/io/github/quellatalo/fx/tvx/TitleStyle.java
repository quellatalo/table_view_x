package io.github.quellatalo.fx.tvx;

/**
 * Column header naming styles.
 */
public enum TitleStyle {
    /**
     * No change.
     */
    ORIGINAL,
    /**
     * Capitalize the first letter.
     */
    CAPITALIZE,
    /**
     * Turn all characters into uppercase.
     */
    UPPERCASE_ALL,
    /**
     * Turn all characters into lowercase.
     */
    LOWERCASE_ALL,
    /**
     * Add spaces between words.
     */
    ORIGINAL_SPACING,
    /**
     * Add spaces between words, and capitalize the first letters.
     */
    CAPITALIZE_SPACING,
    /**
     * Add spaces between words, and turn all characters into uppercase.
     */
    UPPERCASE_ALL_SPACING,
    /**
     * Add spaces between words, and turn all characters into lowercase.
     */
    LOWERCASE_SPACING;

    public static String transform(String value, TitleStyle titleStyle) {
        switch (titleStyle) {
            default:
            case ORIGINAL:
                break;
            case CAPITALIZE:
                value = StringUtils.capitalizeFirstLetter(value);
                break;
            case CAPITALIZE_SPACING:
                value = StringUtils.spacing(StringUtils.capitalizeFirstLetter(value));
                break;
            case ORIGINAL_SPACING:
                value = StringUtils.spacing(value);
                break;
            case UPPERCASE_ALL:
                value = value.toUpperCase();
                break;
            case LOWERCASE_ALL:
                value = value.toLowerCase();
                break;
            case UPPERCASE_ALL_SPACING:
                value = StringUtils.spacing(value.toUpperCase());
                break;
            case LOWERCASE_SPACING:
                value = StringUtils.spacing(value).toLowerCase();
                break;
        }
        return value;
    }
}
