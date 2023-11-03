package org.graphic.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Objects;
import java.util.function.Consumer;

public class WordleScreenController extends GameScreenController {
    @FXML
    private WebView game;
    @FXML
    private Button exit;
    @FXML
    private ImageView loading;

    public void initialize() {
        loading.setVisible(true);
        game.getEngine().load("https://wafflegame.net/daily");
        game.setZoom(1.2);
        game.getEngine().setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/org/graphic/css/web.css")).toString());

        Consumer<WebEngine> disableScrolling = engine -> {
            engine.executeScript("document.body.style.overflow = 'hidden';");
        };

        game.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                disableScrolling.accept(game.getEngine());
            }
        });
        waitLoading(loading);
    }

    public void exit() throws Exception {
        loadScreen("game", exit);
    }
}
