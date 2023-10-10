package org.graphic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class App extends Application {
    private final AppConfig appConfig = new AppConfig();

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("fxml/home-screen.fxml")));
        InputStream stream = new FileInputStream("src/main/resources/org/graphic/image/icon.png");
        Image icon = new Image(stream);
        ImageView imageView = new ImageView();

        imageView.setImage(icon);
        primaryStage.setScene(new Scene(root, appConfig.getUIWidth(), appConfig.getUIHeight()));

        if (!icon.isBackgroundLoading()) primaryStage.getIcons().add(icon);
        else System.out.println("Failed to load image");
        primaryStage.setResizable(appConfig.getUIFullscreen());
        primaryStage.show();
    }
}