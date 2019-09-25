package io.github.quellatalo.fx.advsearch;

import io.github.quellatalo.reflection.ClassUtils;
import io.github.quellatalo.fx.advsearch.searchfield.ISearchField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;

public class LazAdvFilterDialog extends Alert {
    private final VBox vBox;
    private final Hyperlink hplNewCondition;
    private final SortedMap<String, ISearchField> searchFields;
    private final Map<String, ISearchField> customSearchFields;
    private final List<SearchItem> searchItems;
    private final EventHandler<ActionEvent> removeItem;
    private boolean stringAndPrimitivesOnly;
    private boolean displayClass;
    private boolean displayHashCode;
    private boolean displayMapsAndCollections;
    private List<Class<?>> forcedDisplayTypes;

    public LazAdvFilterDialog(String title, boolean displayHashCode, boolean displayClass, boolean displayMapsAndCollections, boolean stringAndPrimitivesOnly, List<Class<?>> forcedDisplayTypes) {
        super(AlertType.CONFIRMATION);
        this.displayClass = displayClass;
        this.displayHashCode = displayHashCode;
        this.displayMapsAndCollections = displayMapsAndCollections;
        this.stringAndPrimitivesOnly = stringAndPrimitivesOnly;
        this.forcedDisplayTypes = forcedDisplayTypes;
        vBox = new VBox();
        vBox.setSpacing(5);
        setGraphic(null);
        getDialogPane().setContent(vBox);
        setTitle(title);
        setHeaderText("");
        setResizable(true);
        hplNewCondition = new Hyperlink("Add Condition");
        searchItems = new ArrayList<>();
        searchFields = new TreeMap<>();
        customSearchFields = new HashMap<>();
        removeItem = event -> {
            setHeight(getHeight() - 25 - vBox.getSpacing());
            searchItems.remove(event.getSource());
        };
        hplNewCondition.setOnAction(event -> {
            SearchItem searchItem = new SearchItem(searchFields);
            searchItem.setOnRemoveAction(removeItem);
            vBox.getChildren().add(searchItem);
            searchItems.add(searchItem);
            setHeight(getHeight() + 25 + vBox.getSpacing());
        });
    }

    public void prepare(Class<?> type) {
        searchFields.clear();
        Map<String, Method> getters = ClassUtils.getGetters(type, displayHashCode, displayClass, displayMapsAndCollections, stringAndPrimitivesOnly, forcedDisplayTypes);
        for (Map.Entry<String, Method> entry : getters.entrySet()) {
            searchFields.put(entry.getKey(), ISearchField.createInstance(entry.getValue()));
        }
        searchFields.putAll(customSearchFields);
        vBox.getChildren().clear();
        searchItems.clear();
        vBox.getChildren().add(hplNewCondition);
        hplNewCondition.getOnAction().handle(null);
    }

    public Predicate<Object> generatePredicate() {
        Predicate<Object> rs = o1 -> true;
        for (SearchItem searchItem : searchItems) {
            rs = rs.and(searchItem.getPredicate());
        }
        return rs;
    }

    public Map<String, ISearchField> getCustomSearchFields() {
        return customSearchFields;
    }

    public boolean isStringAndPrimitivesOnly() {
        return stringAndPrimitivesOnly;
    }

    public void setStringAndPrimitivesOnly(boolean stringAndPrimitivesOnly) {
        this.stringAndPrimitivesOnly = stringAndPrimitivesOnly;
    }

    public boolean isDisplayClass() {
        return displayClass;
    }

    public void setDisplayClass(boolean displayClass) {
        this.displayClass = displayClass;
    }

    public boolean isDisplayHashCode() {
        return displayHashCode;
    }

    public void setDisplayHashCode(boolean displayHashCode) {
        this.displayHashCode = displayHashCode;
    }

    public boolean isDisplayMapsAndCollections() {
        return displayMapsAndCollections;
    }

    public void setDisplayMapsAndCollections(boolean displayMapsAndCollections) {
        this.displayMapsAndCollections = displayMapsAndCollections;
    }

    public List<Class<?>> getForcedDisplayTypes() {
        return forcedDisplayTypes;
    }

    public void setForcedDisplayTypes(List<Class<?>> forcedDisplayTypes) {
        this.forcedDisplayTypes = forcedDisplayTypes;
    }
}
