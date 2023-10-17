package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameScreenController extends ControllersManager {
    @FXML
    private Button quiz;
    @FXML
    private Button matchingCard;
    @FXML
    private Button wordle;

    @FXML
    private void playGame(ActionEvent event) throws Exception {
        if (event.getSource() == quiz) {
            //loadGameScreen("quiz", quiz);
            return;
        }
        if (event.getSource() == matchingCard) {
            loadGameScreen("matchingCard", matchingCard);
            return;
        }
        if (event.getSource() == wordle) {
            //loadGameScreen("wordle", wordle);
            return;
        }
        throw new Exception("Unknown button clicked");
    }
}
