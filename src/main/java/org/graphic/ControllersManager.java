package org.graphic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class ControllersManager {
    private static String currentScreen = "home";

    public void loadStage(String typeofScreen, Button typeofButton) throws Exception {
        Stage stage = (Stage) typeofButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/" + typeofScreen + "-screen.fxml")));
        stage.getScene().setRoot(root);
        stage.show();
    }

    public void loadScreen(String typeofScreen, Button typeofButton) throws Exception {
        currentScreen = typeofScreen;
        loadStage(typeofScreen, typeofButton);
    }

    public void loadGameScreen(String typeofScreen, Button typeofButton) throws Exception {
        currentScreen = "game";
        loadStage(typeofScreen, typeofButton);
    }

    public String getCurrentScreen() {
        return currentScreen;
    }
}


