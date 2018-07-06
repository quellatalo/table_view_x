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
import quellatalo.nin.fx.ClassUtils;
import quellatalo.nin.fx.datetime.DateTimePicker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class SearchItem extends HBox {
    private ComboBox<String> field;
    private ComboBox<ICondition> condition;
    private Control tfValue;
    private Hyperlink hplRemove;
    private Map<String, Method> getters;
    private EventHandler<ActionEvent> fieldSelected;
    private EventHandler<ActionEvent> removeAction;
    private ActionEvent actionEvent;
    private Predicate<Object> predicate;

    public SearchItem(Class<?> type, boolean displayHashCode, boolean displayClass, boolean displayMapsAndCollections, boolean stringAndPrimitivesOnly, List<Class<?>> forcedDisplayTypes) {
        setSpacing(5);
        getters = ClassUtils.getGetters(type);
        field = new ComboBox<>();
        for (Map.Entry<String, Method> set : getters.entrySet()) {
            if (ClassUtils.isEntryQualified(set, displayHashCode, displayClass, displayMapsAndCollections, stringAndPrimitivesOnly, forcedDisplayTypes)) {
                field.getItems().add(set.getKey());
            }
        }
        field.getItems().sort(String::compareTo);
        condition = new ComboBox<>();
        getChildren().add(field);
        getChildren().add(condition);
        condition.setPrefWidth(200);
        fieldSelected = event -> {
            Class<?> returnType = getters.get(field.getSelectionModel().getSelectedItem()).getReturnType();
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
                removeAction.handle(actionEvent);
            }
        });
        getChildren().add(hplRemove);
        predicate = o -> {
            boolean b = true;
            try {
                b = ICondition.generateBoolean(
                        getters.get(field.getSelectionModel().getSelectedItem()).invoke(o),
                        condition.getValue(),
                        tfValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return b;
        };
    }

    public Predicate<Object> getPredicate() {
        return predicate;
    }

    public EventHandler<ActionEvent> getRemoveAction() {
        return removeAction;
    }

    public void setRemoveAction(EventHandler<ActionEvent> removeAction) {
        this.removeAction = removeAction;
    }
}
