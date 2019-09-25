import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import io.github.quellatalo.fx.TableViewX;

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
    }

    public void setContent(List<T> content) {
        tableViewX.setContent(content);
    }

    public void showFilter() {
        tableViewX.openFilter();
    }

    public TableViewX<T> getTableViewX() {
        return tableViewX;
    }
}
