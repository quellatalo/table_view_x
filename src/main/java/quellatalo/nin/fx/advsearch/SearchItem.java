package quellatalo.nin.fx.advsearch;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import quellatalo.nin.fx.advsearch.condition.ICondition;
import quellatalo.nin.fx.datetime.DateTimePicker;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.function.Predicate;

public class SearchItem extends HBox {
    private final ComboBox<String> field;
    private final ComboBox<ICondition> condition;
    private final Hyperlink hplRemove;
    private final EventHandler<ActionEvent> fieldSelected;
    private final ActionEvent actionEvent;
    private final SortedMap<String, SearchField> searchFieldMap;
    private Predicate<Object> predicate;
    private Control tfValue;
    private EventHandler<ActionEvent> onRemoveAction;

    public SearchItem(SortedMap<String, SearchField> searchFields) {
        this.searchFieldMap = searchFields;
        setSpacing(5);
        field = new ComboBox<>();
        field.getItems().addAll(searchFieldMap.keySet());
        condition = new ComboBox<>();
        getChildren().add(field);
        getChildren().add(condition);
        condition.setPrefWidth(200);
        fieldSelected = event -> {
            Class<?> returnType = searchFieldMap.get(field.getSelectionModel().getSelectedItem()).getType();
            condition.setItems(FXCollections.observableArrayList(ICondition.getConditions(returnType)));
            getChildren().remove(tfValue);
            tfValue = returnType == LocalDateTime.class ? new DateTimePicker() : new TextField();
            getChildren().add(2, tfValue);
            if (condition.getSelectionModel().getSelectedIndex() < 0)
                condition.getSelectionModel().select(0);
        };
        field.setOnAction(fieldSelected);
        field.getSelectionModel().select(0);
        fieldSelected.handle(null);
        hplRemove = new Hyperlink("Remove");
        actionEvent = new ActionEvent(this, null);
        hplRemove.setOnAction(event -> {
            if (getParent() instanceof Pane) {
                Pane pr = (Pane) getParent();
                pr.getChildren().remove(this);
                if (onRemoveAction != null) onRemoveAction.handle(actionEvent);
            }
        });
        getChildren().add(hplRemove);
        predicate = o -> ICondition.generateBoolean(
                searchFieldMap.get(field.getSelectionModel().getSelectedItem()).getSubject().apply(o),
                condition.getValue(),
                tfValue);
    }

    public Predicate<Object> getPredicate() {
        return predicate;
    }

    public void setPredicate(Predicate<Object> predicate) {
        this.predicate = predicate;
    }

    public EventHandler<ActionEvent> getOnRemoveAction() {
        return onRemoveAction;
    }

    public void setOnRemoveAction(EventHandler<ActionEvent> onRemoveAction) {
        this.onRemoveAction = onRemoveAction;
    }
}
