package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Family Tree App");
        stage.getIcons().add(new Image("D:\\УЧЕБА\\FamilyTreeApp\\src\\main\\resources\\ui\\tree.png"));
        showMainMenu(stage);
    }

    public void showMainMenu(Stage stage) throws IOException {
        stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("start.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
}
