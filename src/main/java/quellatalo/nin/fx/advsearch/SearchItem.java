package quellatalo.nin.fx.advsearch;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import quellatalo.nin.fx.ClassUtils;
import quellatalo.nin.fx.advsearch.condition.ICondition;
import quellatalo.nin.fx.advsearch.searchfield.ISearchField;
import quellatalo.nin.fx.datetime.DateTimePicker;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.function.Predicate;

public class SearchItem extends HBox {
    private final ComboBox<String> field;
    private final ComboBox<ICondition> condition;
    private final Hyperlink hplRemove;
    private final EventHandler<ActionEvent> fieldSelected;
    private final ActionEvent actionEvent;
    private final SortedMap<String, ISearchField> searchFieldMap;
    private Predicate<Object> predicate;
    private Control tfValue;
    private EventHandler<ActionEvent> onRemoveAction;

    public SearchItem(SortedMap<String, ISearchField> searchFields) {
        this.searchFieldMap = searchFields;
        setSpacing(5);
        field = new ComboBox<>();
        field.getItems().addAll(searchFieldMap.keySet());
        condition = new ComboBox<>();
        getChildren().add(field);
        getChildren().add(condition);
        condition.setPrefWidth(200);
        fieldSelected = event -> {
            condition.getItems().setAll(searchFieldMap.get(field.getSelectionModel().getSelectedItem()).getConditions());
            getChildren().remove(tfValue);
            try {
                tfValue = (Control) searchFieldMap.get(field.getSelectionModel().getSelectedItem()).getValueControlType().getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            getChildren().add(2, tfValue);
            int conditionIndex = condition.getSelectionModel().getSelectedIndex();
            if (conditionIndex < 0 || condition.getItems().size() <= conditionIndex)
                condition.getSelectionModel().select(0);
            else
                condition.getSelectionModel().select(conditionIndex);
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
        predicate = o -> {
            boolean b;
            Object subject = searchFieldMap.get(field.getSelectionModel().getSelectedItem()).getSubjectOperator().apply(o);
            Class<?> type = subject.getClass();
            if (ClassUtils.isNumeric(type)) {
                b = condition.getValue().test(((Number) subject).doubleValue(), Double.parseDouble(((TextField) tfValue).getText()));
            } else if (type == LocalDateTime.class) {
                b = condition.getValue().test(subject, ((DateTimePicker) tfValue).getDateTimeValue());
            } else {
                b = condition.getValue().test(subject.toString().toLowerCase(), ((TextField) tfValue).getText().toLowerCase());
            }
            return b;
        };
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
