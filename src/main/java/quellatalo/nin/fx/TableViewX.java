package quellatalo.nin.fx;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * TableView X is an attempt to improve TableView to be able to handle custom user-defined types as data.
 * By using reflection, it will read the data structure, and create columns for all properties.
 *
 * @param <S> Specific type this TableView accepts.
 */
public class TableViewX<S> extends TableView<S> {
    private BooleanProperty rowCounting;
    private BooleanProperty onlyStringAndPrimitives;
    private BooleanProperty displayClass;
    private BooleanProperty displayHashCode;
    private IntegerProperty baseIndex;
    private StringProperty rowCounterTitle;
    private ObjectProperty<TitleStyle> titleStyle;

    /**
     * Construct a TableViewX.
     */
    public TableViewX() {
        rowCounting = new SimpleBooleanProperty(this, "rowCounting", false);
        onlyStringAndPrimitives = new SimpleBooleanProperty(this, "onlyStringAndPrimitives", false);
        displayClass = new SimpleBooleanProperty(this, "displayClass", false);
        displayHashCode = new SimpleBooleanProperty(this, "displayHashCode", false);
        baseIndex = new SimpleIntegerProperty(this, "baseIndex", 0);
        rowCounterTitle = new SimpleStringProperty(this, "rowCounterTitle", "#");
        titleStyle = new SimpleObjectProperty<>(this, "titleStyle", TitleStyle.ORIGINAL);
    }

    /**
     * Construct a TableViewX with specified content.
     */
    public TableViewX(ObservableList<S> items) {
        this();
        setContent(items);
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
            Method[] methods = c.getMethods();
            if (rowCounting.get()) {
                TableColumn<S, Number> indexColumn = new TableColumn<>(rowCounterTitle.get());
                indexColumn.setSortable(false);
                indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(getItems().indexOf(column.getValue()) + baseIndex.get()));
                getColumns().add(indexColumn);
            }
            boolean isGetter;
            String name;
            for (Method method : methods) {
                if (method.getParameterCount() == 0) {
                    isGetter = false;
                    name = method.getName();
                    if (name.startsWith("get")) {
                        name = method.getName().substring(3);
                        isGetter = true;
                    } else if (name.startsWith("is")) {
                        name = name.substring(2);
                        isGetter = true;
                    } else if (name.startsWith("has")) {
                        isGetter = true;
                    }
                    if (isGetter) {
                        if (!displayClass.get() && name.equals("Class")) continue;
                        if (!displayHashCode.get() && name.equals("hashCode")) continue;
                        Class propType = method.getReturnType();
                        // add column
                        if (!onlyStringAndPrimitives.get() || propType.isPrimitive() || propType == String.class) {
                            String capitalizedName = StringUtils.capitalizeFirstLetter(name);
                            switch (titleStyle.get()) {
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
                            TableColumn<S, ?> column = new TableColumn<>(name);
                            getColumns().add(column);
                            column.setCellValueFactory(param -> {
                                Object o = null;
                                try {
                                    o = method.invoke(param.getValue());
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                return new SimpleObjectProperty(o);
                            });
                        }
                    }
                }
            }
            getItems().addAll(items);
        }
    }

    /**
     * Gets whether this TableViewX displays a counting column.
     *
     * @return Whether this TableViewX displays a counting column.
     */
    public boolean isRowCounting() {
        return rowCounting.get();
    }

    /**
     * Sets whether this TableViewX displays a counting column.
     *
     * @param rowCounting Whether this TableViewX displays a counting column.
     */
    public void setRowCounting(boolean rowCounting) {
        this.rowCounting.set(rowCounting);
    }

    /**
     * Gets whether this TableView only displays String and Primitives while ignore all other types.
     *
     * @return Whether this TableView only displays String and Primitives while ignore all other types.
     */
    public boolean isOnlyStringAndPrimitives() {
        return onlyStringAndPrimitives.get();
    }

    /**
     * Sets whether this TableView only displays String and Primitives while ignore all other types.
     *
     * @param onlyStringAndPrimitives Whether this TableView only displays String and Primitives while ignore all other types.
     */
    public void setOnlyStringAndPrimitives(boolean onlyStringAndPrimitives) {
        this.onlyStringAndPrimitives.set(onlyStringAndPrimitives);
    }

    /**
     * Gets whether this TableView displays Class type of the items.
     *
     * @return Whether this TableView displays Class type of the items.
     */
    public boolean isDisplayClass() {
        return displayClass.get();
    }

    /**
     * Sets whether this TableView displays Class type of the items.
     *
     * @param displayClass Whether this TableView displays Class type of the items.
     */
    public void setDisplayClass(boolean displayClass) {
        this.displayClass.set(displayClass);
    }

    /**
     * Gets whether this TableView displays HashCode of the items.
     *
     * @return Whether this TableView displays HashCode of the items.
     */
    public boolean isDisplayHashCode() {
        return displayHashCode.get();
    }

    /**
     * Sets whether this TableView displays HashCode of the items.
     *
     * @param displayHashCode Whether this TableView displays HashCode of the items.
     */
    public void setDisplayHashCode(boolean displayHashCode) {
        this.displayHashCode.set(displayHashCode);
    }

    /**
     * Gets the first index of the Row Counting column.
     *
     * @return The first index of the Row Counting column.
     */
    public int getBaseIndex() {
        return baseIndex.get();
    }

    /**
     * Sets the first index of the Row Counting column.
     *
     * @param baseIndex The based index. Most common ones are 0-based and 1-based.
     */
    public void setBaseIndex(int baseIndex) {
        this.baseIndex.set(baseIndex);
    }

    /**
     * Gets the title of the row counting column.
     *
     * @return The title of the row counting column.
     */
    public String getRowCounterTitle() {
        return rowCounterTitle.get();
    }

    /**
     * Sets the title of the row counting column.
     *
     * @param rowCounterTitle The title of the row counting column.
     */
    public void setRowCounterTitle(String rowCounterTitle) {
        this.rowCounterTitle.set(rowCounterTitle);
    }

    /**
     * Gets the naming style for column headers.
     *
     * @return The naming style for column headers.
     */
    public TitleStyle getTitleStyle() {
        return titleStyle.get();
    }

    /**
     * Sets the naming style for column headers.
     *
     * @param titleStyle The naming style for column headers.
     */
    public void setTitleStyle(TitleStyle titleStyle) {
        this.titleStyle.set(titleStyle);
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
