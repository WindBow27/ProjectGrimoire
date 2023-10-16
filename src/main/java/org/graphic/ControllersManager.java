package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Objects;

public class ControllersManager {
    private static String currentScreen = "home";
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
        if (event.getSource() == translate) {
            loadScreen("translate", translate);
            return;
        }
        if (event.getSource() == search) {
            loadScreen("search", search);
            return;
        }
        if (event.getSource() == input) {
            loadScreen("input", input);
            return;
        }
        if (event.getSource() == game) {
            loadScreen("game", game);
            return;
        }
        throw new Exception("Unknown button clicked");
    }

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


