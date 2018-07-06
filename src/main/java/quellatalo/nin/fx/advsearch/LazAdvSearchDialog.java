package quellatalo.nin.fx.advsearch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LazAdvSearchDialog extends Alert {
    private VBox vBox;
    private Hyperlink hplNewCondition;
    private Class<?> type;
    private boolean displayHashCode, displayClass, displayMapsAndCollections, stringAndPrimitivesOnly;
    private List<Class<?>> forcedDisplayTypes;
    private EventHandler<ActionEvent> removeItem;
    private List<SearchItem> searchItems;

    public LazAdvSearchDialog(String title) {
        super(AlertType.CONFIRMATION);
        vBox = new VBox();
        vBox.setSpacing(5);
        setGraphic(null);
        getDialogPane().setContent(vBox);
        setTitle(title);
        setHeaderText("");
        setResizable(true);
        hplNewCondition = new Hyperlink("Add Condition");
        removeItem = event -> {
            setHeight(getHeight() - 25 - vBox.getSpacing());
            searchItems.remove(event.getSource());
        };
        hplNewCondition.setOnAction(event -> {
            SearchItem searchItem = new SearchItem(type, displayHashCode, displayClass, displayMapsAndCollections, stringAndPrimitivesOnly, forcedDisplayTypes);
            searchItem.setRemoveAction(removeItem);
            vBox.getChildren().add(searchItem);
            searchItems.add(searchItem);
            setHeight(getHeight() + 25 + vBox.getSpacing());
        });
        searchItems = new ArrayList<>();
    }

    public void prepare(Class<?> type, boolean displayHashCode, boolean displayClass, boolean displayMapsAndCollections, boolean stringAndPrimitivesOnly, List<Class<?>> forcedDisplayTypes) {
        if (type != this.type) {
            this.type = type;
            this.displayClass = displayClass;
            this.displayHashCode = displayHashCode;
            this.displayMapsAndCollections = displayMapsAndCollections;
            this.forcedDisplayTypes = forcedDisplayTypes;
            this.stringAndPrimitivesOnly = stringAndPrimitivesOnly;
            vBox.getChildren().clear();
            searchItems.clear();
            vBox.getChildren().add(hplNewCondition);
            SearchItem searchItem = new SearchItem(type, displayHashCode, displayClass, displayMapsAndCollections, stringAndPrimitivesOnly, forcedDisplayTypes);
            searchItem.setRemoveAction(removeItem);
            vBox.getChildren().add(searchItem);
            searchItems.add(searchItem);
        }
    }

    public Predicate<Object> generatePredicate() {
        Predicate<Object> rs = o1 -> true;
        for (SearchItem searchItem : searchItems) {
            rs = rs.and(searchItem.getPredicate());
        }
        return rs;
    }
}
