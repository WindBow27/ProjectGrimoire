package org.graphic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

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

    @FXML
    private void playGame(ActionEvent event) throws Exception {
        if (event.getSource() == quiz) {
            loadGameScreen("quiz", quiz);
            typeOfData = "Quiz";
            QuizScreenController game = new QuizScreenController();
            game.startGame();
            return;
        }
        if (event.getSource() == hangman) {
            loadGameScreen("hangman", hangman);
            typeOfData = "Hangman";
            return;
        }
        if (event.getSource() == wordle) {
            loadGameScreen("wordle", wordle);
            return;
        }
        throw new Exception("Unknown button clicked");
    }
}
