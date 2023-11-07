package org.graphic.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HomeScreenController extends ControllersManager {
    @FXML
    private Button about;
    @FXML
    private Button exit;

    @FXML
    private void handleAction(ActionEvent event) throws Exception {
        if (event.getSource() == about) {
            loadHomeScreen("about", about);
            return;
        }
        if (event.getSource() == exit) {
            Platform.exit();
            return;
        }
        throw new Exception("Unknown button clicked");
    }
}
