package quellatalo.nin.fx.advsearch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import quellatalo.nin.fx.ClassUtils;
import quellatalo.nin.fx.advsearch.searchfield.ISearchField;

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

    public LazAdvFilterDialog(String title) {
        super(AlertType.CONFIRMATION);
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

    public void prepare(Class<?> type, boolean displayHashCode, boolean displayClass, boolean displayMapsAndCollections, boolean stringAndPrimitivesOnly, List<Class<?>> forcedDisplayTypes) {
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
}
