package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class HeaderController extends ControllersManager {
    private final AppConfig appConfig = new AppConfig();
    @FXML
    private HBox container;
    @FXML
    private Rectangle home_line;
    @FXML
    private Rectangle input_line;
    @FXML
    private Rectangle translate_line;
    @FXML
    private Rectangle search_line;
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
        if (currentScreen == null) return;

        switch (currentScreen) {
            case "home" -> home_line.setVisible(true);
            case "input" -> input_line.setVisible(true);
            case "translate" -> translate_line.setVisible(true);
            case "search" -> search_line.setVisible(true);
            case "game" -> game_line.setVisible(true);
        }
    }
}
