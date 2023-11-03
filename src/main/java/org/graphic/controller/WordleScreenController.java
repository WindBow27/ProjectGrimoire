package org.graphic.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;

import java.util.Objects;

public class WordleScreenController extends GameScreenController {
    @FXML
    private WebView game;
    @FXML
    private Button exit;

    public void initialize() {
        game.getEngine().load("https://www.nytimes.com/games/wordle/index.html");
        game.getEngine().setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/org/graphic/css/web.css")).toString());
        //Add keyboard input if possible
    }

    public void exit() throws Exception {
        loadScreen("game", exit);
    }
}
