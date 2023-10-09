package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class ControllersManager {
    private static String currentScreen;
    private final AppConfig appConfig = new AppConfig();
    @FXML
    private Button input;
    @FXML
    private Button translate;
    @FXML
    private Button search;
    @FXML
    private Button game;
    @FXML
    private Button delete;
    @FXML
    private Button home;
//    private final String[] backgroundImages = {
//            "/org/graphic/image/background-0.jpg",
//            "/org/graphic/image/background-1.jpg",
//            "/org/graphic/image/background-2.jpg",
//            "/org/graphic/image/background-3.jpg",
//            "/org/graphic/image/background-4.jpg"
//    };

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if (event.getSource() == home) {
            currentScreen = "home";
            stage = (Stage) home.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/home-screen.fxml"));
        } else if (event.getSource() == input) {
            currentScreen = "input";
            stage = (Stage) input.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/input-screen.fxml"));
        } else if (event.getSource() == translate) {
            currentScreen = "translate";
            stage = (Stage) translate.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/translate-screen.fxml"));
        } else if (event.getSource() == search) {
            currentScreen = "search";
            stage = (Stage) search.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/search-screen.fxml"));
        } else if (event.getSource() == game) {
            currentScreen = "game";
            stage = (Stage) game.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/game-screen.fxml"));
        } else {
            throw new Exception("Unknown button clicked");
        }

        Scene scene = new Scene(root, appConfig.getUIWidth(), appConfig.getUIHeight());
        scene.getStylesheets().add(getClass().getResource("css/background.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public String getCurrentScreen() {
        return currentScreen;
    }

//    public void setRandomBackground(BorderPane container) {
//        if (container == null) return;
//        Random random = new Random();
//        int randomIndex = random.nextInt(backgroundImages.length);
//        String backgroundImagePath = backgroundImages[randomIndex];
//        Image image = new Image(getClass().getResourceAsStream(backgroundImagePath));
//
//        // Create a new Image with the desired dimensions.
//        Image scaledImage = new Image(getClass().getResourceAsStream(backgroundImagePath), appConfig.getUIWidth(), appConfig.getUIHeight(), true, true);
//
//        // Create a Background with the scaled image.
//        BackgroundImage backgroundImage = new BackgroundImage(
//                scaledImage,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.DEFAULT,
//                BackgroundSize.DEFAULT
//        );
//
//        Background background = new Background(backgroundImage);
//        container.setBackground(background);
//    }
}


