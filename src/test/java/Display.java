import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import quellatalo.nin.fx.TableViewX;

import java.io.IOException;
import java.util.List;

public class Display extends AnchorPane {

    @FXML
    private TableViewX<Sample> tableViewX;

    public Display() {
        System.out.println(getClass().getClassLoader().getResource("Display.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Display.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setContent(List<Sample> content) {
        tableViewX.setContent(content);
        tableViewX.getColumns().forEach(sampleTableColumn -> System.out.println(sampleTableColumn.getText()));
    }
}
