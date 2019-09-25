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
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(1);
        integers.add(2);
        integers.add(3);
        List<String> strings = new ArrayList<>();
        strings.add("");
        strings.add("1");
        strings.add("");
        strings.add("3");
        strings.add("1");
        System.out.println(strings);
        samples.get(0).setList(strings);
        String[] arr = {"a","b"};
        samples.get(1).setArgs(arr);
        System.out.println(List.of(arr));
        Display display = new Display();
        display.setContent(samples);
        Scene scene = new Scene(display);
//        scene.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.F3) {
//                display.showFilter();
//            }
//        });
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
