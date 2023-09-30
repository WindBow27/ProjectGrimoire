package org.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class App extends Application {
    static int width = 1280;
    static int length = 720;
    static BorderPane root = new BorderPane();
    static Scene scene = new Scene(root, length, width);

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("main-screen.fxml")));
        InputStream stream = new FileInputStream("src/main/resources/org/graphic/icon.png");
        Image icon = new Image(stream);
        ImageView imageView = new ImageView();

        imageView.setImage(icon);
        primaryStage.setScene(new Scene(root, width, length));

        if (!icon.isBackgroundLoading()) primaryStage.getIcons().add(icon);
        else System.out.println("Failed to load image");

        primaryStage.show();
    }

    public void init(Stage primaryStage) {
        primaryStage.setTitle("Dictionary v1.0");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
    }
}