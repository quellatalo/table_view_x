import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Testing extends Application {
    @Override
    public void start(Stage primaryStage) {
        List<Sample> samples = new ArrayList<>();
        samples.add(new Sample(1, "one"));
        samples.add(new Sample(2, "two"));
        samples.add(new Sample(3, "three"));
        samples.add(new Sample(10, "three"));
        primaryStage.setTitle("Kawaii is Justice!");
        Display display = new Display();
        display.setContent(samples);
        primaryStage.setScene(new Scene(display));
        primaryStage.show();
    }
}
