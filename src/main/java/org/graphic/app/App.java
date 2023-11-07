package org.graphic.app;

import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class App extends Application {
    private final AppConfig appConfig = new AppConfig();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("/org/graphic/fxml/dictionary-screen.fxml")));
        InputStream stream = new FileInputStream("src/main/resources/org/graphic/image/icon.png");
        Image icon = new Image(stream);

        PreloadService preloadService = getPreloadService(primaryStage);
        preloadService.start();

        primaryStage.setScene(new Scene(root, appConfig.getUIWidth(), appConfig.getUIHeight()));

        if (!icon.isBackgroundLoading()) {
            primaryStage.getIcons().add(icon);
        } else {
            System.out.println("Failed to load image");
        }

        primaryStage.setResizable(appConfig.getUIFullscreen());
        primaryStage.show();
    }

    private PreloadService getPreloadService(Stage primaryStage) {
        PreloadService preloadService = new PreloadService();
        preloadService.setOnSucceeded(event -> {
            try {
                Parent dictionaryRoot = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("/org/graphic/fxml/home-screen.fxml")));
                Scene dictionaryScene = new Scene(dictionaryRoot, appConfig.getUIWidth(), appConfig.getUIHeight());
                primaryStage.setScene(dictionaryScene);
            } catch (IOException e) {
                System.out.println("Failed to load home-screen.fxml");
            }
        });
        return preloadService;
    }

    private static class PreloadService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    return null;
                }
            };
        }
    }
}
