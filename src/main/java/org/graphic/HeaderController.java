package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class HeaderController extends ControllersManager {
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
    private Rectangle home_line;
    @FXML
    private Rectangle translate_line;
    @FXML
    private Rectangle search_line;
    @FXML
    private Rectangle input_line;
    @FXML
    private Rectangle game_line;

    public void initialize() {
        //Don't use list, it could be null
        home_line.setVisible(false);
        input_line.setVisible(false);
        translate_line.setVisible(false);
        search_line.setVisible(false);
        game_line.setVisible(false);

        String currentScreen = getCurrentScreen();

        switch (currentScreen) {
            case "home" -> home_line.setVisible(true);
            case "input" -> input_line.setVisible(true);
            case "translate" -> translate_line.setVisible(true);
            case "search" -> search_line.setVisible(true);
            case "game" -> game_line.setVisible(true);
        }
    }

    @FXML
    private void changeScreen(ActionEvent event) throws Exception {
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
}
