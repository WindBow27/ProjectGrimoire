package org.graphic.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameScreenController extends ControllersManager {
    protected static String typeOfData;
    @FXML
    private ImageView loading;
    @FXML
    private Button quiz;
    @FXML
    private Button hangman;
    @FXML
    private Button wordle;
    private Logger logger = Logger.getLogger(GameScreenController.class.getName());

    private void future(String typeOfScreen, Button typeOfButton) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                loadGameScreen(typeOfScreen, typeOfButton);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An exception occurred", e);
            }
        });
        future.thenRun(() -> Platform.runLater(() -> loading.setVisible(false)));
    }

    @FXML
    private void playGame(ActionEvent event) throws Exception {
        loading.setVisible(true);
        if (event.getSource() == quiz) {
//            future("quiz", quiz);
            loadGameScreen("quiz", quiz);
            return;
        }
        if (event.getSource() == hangman) {
//            future("hangman", hangman);
            loadGameScreen("hangman", hangman);
            return;
        }
        if (event.getSource() == wordle) {
//            future("wordle", wordle);
            loadGameScreen("wordle", wordle);
            return;
        }
        throw new Exception("Unknown button clicked");
    }
}
