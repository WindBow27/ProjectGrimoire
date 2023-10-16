package org.graphic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class ControllersManager {
    private String currentScreen = "home";

    public void loadScreen(String typeofScreen, Button typeofButton) throws Exception {
        currentScreen = typeofScreen;
        Stage stage = (Stage) typeofButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/" + typeofScreen + "-screen.fxml")));
        stage.getScene().setRoot(root);
        stage.show();
    }

    public String getCurrentScreen() {
        return currentScreen;
    }
}


