import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import quellatalo.nin.fx.TableViewX;

import java.io.IOException;
import java.util.List;

public class Display<T> extends AnchorPane {

    @FXML
    private TableViewX<T> tableViewX;

    public Display() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Display.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        tableViewX.setStringAndPrimitivesOnly(false);
        tableViewX.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F3) {
                tableViewX.openFilter();
            }
        });
    }

    public void setContent(List<T> content) {
        tableViewX.setContent(content);
    }

    public void showFilter() {
        tableViewX.openFilter();
    }
}
