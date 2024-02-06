import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Testiohjelma extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Luodaan tekstiolio ja asetetaan siihen teksti
        Text text = new Text("OhSyTe 2024");

        // Luodaan pinolayout ja lisätään tekstiolio siihen
        StackPane root = new StackPane();
        root.getChildren().add(text);

        // Luodaan näkymä, jossa on pinolayout, ja asetetaan sen koko
        Scene scene = new Scene(root, 300, 200);

        // Asetetaan näkymä ikkunalle ja näytetään ikkuna
        primaryStage.setTitle("Testiohjelma");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Käynnistetään JavaFX-sovellus
        launch(args);
    }
}
