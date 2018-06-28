package quellatalo.nin.fx;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * TableView X is an attempt to improve TableView to be able to handle custom user-defined types as data.
 * By using reflection, it will read the data structure, and create columns for all properties.
 *
 * @param <S> Specific type this TableView accepts.
 */
public class TableViewX<S> extends TableView<S> {
    public static final int DEFAULT_BASE_INDEX = 0;
    public static final String DEFAULT_ROW_COUNTER_TITLE = "#";

    private BooleanProperty rowCounting;
    private BooleanProperty stringAndPrimitivesOnly;
    private BooleanProperty displayClass;
    private BooleanProperty displayHashCode;
    private IntegerProperty baseIndex;
    private StringProperty rowCounterTitle;
    private ObjectProperty<TitleStyle> titleStyle;
    private List<Class<?>> forcedDisplayTypes;

    /**
     * Construct a TableViewX.
     */
    public TableViewX() {
        rowCounting = new SimpleBooleanProperty(this, "rowCounting", false);
        stringAndPrimitivesOnly = new SimpleBooleanProperty(this, "stringAndPrimitivesOnly", true);
        displayClass = new SimpleBooleanProperty(this, "displayClass", false);
        displayHashCode = new SimpleBooleanProperty(this, "displayHashCode", false);
        baseIndex = new SimpleIntegerProperty(this, "baseIndex", DEFAULT_BASE_INDEX);
        rowCounterTitle = new SimpleStringProperty(this, "rowCounterTitle", DEFAULT_ROW_COUNTER_TITLE);
        titleStyle = new SimpleObjectProperty<>(this, "titleStyle", TitleStyle.ORIGINAL);
        forcedDisplayTypes = new ArrayList<>();
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
            Map<String, Method> getters = ClassUtils.getGetters(c);
            getters.forEach((s, method) -> {
                if (!(!displayClass.get() && s.equals("Class")) && !(!displayHashCode.get() && s.equals("hashCode"))) {
                    Class propType = method.getReturnType();
                    // add column
                    if (
                            !stringAndPrimitivesOnly.get()
                                    || propType.isPrimitive()
                                    || propType == String.class
                                    || ClassUtils.isAssignableFrom(propType, forcedDisplayTypes)
                            ) {
                        String displayLabel = TitleStyle.transform(s, titleStyle.get());
                        TableColumn<S, Object> column = new TableColumn<>(displayLabel);
                        getColumns().add(column);
                        column.setCellValueFactory(param -> {
                            Object o = null;
                            try {
                                o = method.invoke(param.getValue());
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                            return new SimpleObjectProperty<>(o);
                        });
                    }
                }
            });
            getColumns().sort(Comparator.comparing(TableColumnBase::getText));
            if (rowCounting.get()) {
                TableColumn<S, Number> indexColumn = new TableColumn<>(rowCounterTitle.get());
                indexColumn.setSortable(false);
                indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(getItems().indexOf(column.getValue()) + baseIndex.get()));
                getColumns().add(0, indexColumn);
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
    public boolean getStringAndPrimitivesOnly() {
        return stringAndPrimitivesOnly.get();
    }

    /**
     * Sets whether this TableView only displays String and Primitives while ignore all other types.
     *
     * @param stringAndPrimitivesOnly Whether this TableView only displays String and Primitives while ignore all other types.
     */
    public void setStringAndPrimitivesOnly(boolean stringAndPrimitivesOnly) {
        this.stringAndPrimitivesOnly.set(stringAndPrimitivesOnly);
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
     * Gets the return types from the properties which will be forced to display.
     *
     * @return The types that will be forced to display on the TableView.
     */
    public List<Class<?>> getForcedDisplayTypes() {
        return forcedDisplayTypes;
    }

    public BooleanProperty rowCountingProperty() {
        return rowCounting;
    }

    public boolean isStringAndPrimitivesOnly() {
        return stringAndPrimitivesOnly.get();
    }

    public BooleanProperty stringAndPrimitivesOnlyProperty() {
        return stringAndPrimitivesOnly;
    }

    public BooleanProperty displayClassProperty() {
        return displayClass;
    }

    public BooleanProperty displayHashCodeProperty() {
        return displayHashCode;
    }

    public IntegerProperty baseIndexProperty() {
        return baseIndex;
    }

    public StringProperty rowCounterTitleProperty() {
        return rowCounterTitle;
    }

    public ObjectProperty<TitleStyle> titleStyleProperty() {
        return titleStyle;
    }
}
