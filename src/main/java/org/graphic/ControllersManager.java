package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class ControllersManager {
    private static String currentScreen = "home";
    private final AppConfig appConfig = new AppConfig();
    @FXML
    private Button home;
    @FXML
    private Button translate;
    @FXML
    private Button search;
    @FXML
    private Button input;
    @FXML
    private Button game;
    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        if (event.getSource() == home) {
            loadScreen("home", home);
            return;
        }
        if (event.getSource() == input) {
            loadScreen("input", input);
            return;
        }
        if (event.getSource() == translate) {
            loadScreen("translate", translate);
            return;
        }
        if (event.getSource() == search) {
            loadScreen("search", search);
            return;
        }
        if (event.getSource() == game) {
            loadScreen("game", game);
            return;
        }
        throw new Exception("Unknown button clicked");
    }

    private void loadScreen(String typeofScreen, Button typeofButton) throws Exception {
        currentScreen = typeofScreen;
        Stage stage = (Stage) typeofButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/" + typeofScreen + "-screen.fxml")));
        loadStage(stage, root);
    }

    private void loadStage(Stage stage, Parent root) {
        Scene scene = new Scene(root, appConfig.getUIWidth(), appConfig.getUIHeight());
        stage.setScene(scene);
        stage.show();
    }

    public String getCurrentScreen() {
        return currentScreen;
    }

//    private final String[] backgroundImages = {
//            "/org/graphic/image/background-0.jpg",
//            "/org/graphic/image/background-1.jpg",
//            "/org/graphic/image/background-2.jpg",
//            "/org/graphic/image/background-3.jpg",
//            "/org/graphic/image/background-4.jpg"
//    };
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


