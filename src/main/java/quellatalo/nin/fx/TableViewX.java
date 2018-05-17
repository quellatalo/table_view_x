package quellatalo.nin.fx;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static quellatalo.nin.fx.ReflectionUtils.getGetter;

/**
 * TableView X is an attempt to improve TableView to be able to handle custom user-defined types as data.
 * By using reflection, it will read the data structure, and create columns for all properties.
 *
 * @param <S> Specific type this TableView accepts.
 */
public class TableViewX<S> extends TableView<S> {
    private boolean rowCounting = false;
    private boolean onlyStringAndPrimitives = false;
    private int baseIndex = 0;
    private String rowCounterTitle = "#";
    private TitleStyle titleStyle = TitleStyle.ORIGINAL;

    /**
     * Sets whether this TableView only displays String and Primitives while ignore all other types.
     *
     * @return Whether this TableView only displays String and Primitives while ignore all other types.
     */
    public boolean isOnlyStringAndPrimitives() {
        return onlyStringAndPrimitives;
    }

    /**
     * Sets whether this TableView only displays String and Primitives while ignore all other types.
     *
     * @param onlyStringAndPrimitives Whether this TableView only displays String and Primitives while ignore all other types.
     */
    public void setOnlyStringAndPrimitives(boolean onlyStringAndPrimitives) {
        this.onlyStringAndPrimitives = onlyStringAndPrimitives;
    }

    /**
     * Gets whether this TableViewX displays a counting column.
     *
     * @return Whether this TableViewX displays a counting column.
     */
    public boolean isRowCounting() {
        return rowCounting;
    }

    public void setRowCounting(boolean rowCounting) {
        this.rowCounting = rowCounting;
    }

    /**
     * Gets the title of the row counting column.
     *
     * @return The title of the row counting column.
     */
    public String getRowCounterTitle() {
        return rowCounterTitle;
    }

    /**
     * Sets the title of the row counting column.
     *
     * @param rowCounterTitle The title of the row counting column.
     */
    public void setRowCounterTitle(String rowCounterTitle) {
        this.rowCounterTitle = rowCounterTitle;
    }

    /**
     * Gets the naming style for column headers.
     *
     * @return The naming style for column headers.
     */
    public TitleStyle getTitleStyle() {
        return titleStyle;
    }

    /**
     * Sets the naming style for column headers.
     *
     * @param titleStyle The naming style for column headers.
     */
    public void setTitleStyle(TitleStyle titleStyle) {
        this.titleStyle = titleStyle;
    }

    /**
     * Gets the first index of the Row Counting column.
     *
     * @return The first index of the Row Counting column.
     */
    public int getBaseIndex() {
        return baseIndex;
    }

    /**
     * Sets the first index of the Row Counting column.
     *
     * @param baseIndex The based index. Most common ones are 0-based and 1-based.
     */
    public void setBaseIndex(int baseIndex) {
        this.baseIndex = baseIndex;
    }

    /**
     * Sets the items for the TableViewX to display.
     *
     * @param items List of items to display.
     */
    public void setContent(List<S> items) {
        getColumns().clear();
        getItems().clear();
        if (items != null && !items.isEmpty()) {
            Class c = items.get(0).getClass();
            Field[] fields = c.getDeclaredFields();
            if (rowCounting) {
                TableColumn<S, Number> indexColumn = new TableColumn<>(rowCounterTitle);
                indexColumn.setSortable(false);
                indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(getItems().indexOf(column.getValue()) + baseIndex));
                getColumns().add(indexColumn);
            }
            for (Field field : fields) {
                String name = field.getName();
                Class fieldType = field.getType();
                String capitalizedName = StringUtils.capitalizeFirstLetter(name);
                // add column
                if (!onlyStringAndPrimitives || fieldType.isPrimitive() || fieldType == String.class) {
                    Method m = getGetter(c, capitalizedName);
                    if (m != null) {
                        switch (titleStyle) {
                            default:
                            case ORIGINAL:
                                break;
                            case CAPITALIZE:
                                name = capitalizedName;
                                break;
                            case CAPITALIZE_SPACING:
                                name = StringUtils.spacing(capitalizedName);
                                break;
                            case ORIGINAL_SPACING:
                                name = StringUtils.spacing(name);
                                break;
                            case UPPERCASE_ALL:
                                name = name.toUpperCase();
                                break;
                            case LOWERCASE_ALL:
                                name = name.toLowerCase();
                                break;
                            case UPPERCASE_ALL_SPACING:
                                name = StringUtils.spacing(name.toUpperCase());
                                break;
                            case LOWERCASE_SPACING:
                                name = StringUtils.spacing(name).toLowerCase();
                                break;
                        }
                        TableColumn<S, ?> column = new TableColumn(name);
                        getColumns().add(column);
                        column.setCellValueFactory(param -> {
                            Object o = null;
                            try {
                                o = m.invoke(param.getValue());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            return new SimpleObjectProperty(o);
                        });
                    }
                }
            }
            getItems().addAll(items);
        }
    }


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
        LOWERCASE_SPACING
    }
}
