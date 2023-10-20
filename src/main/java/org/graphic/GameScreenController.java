package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameScreenController extends ControllersManager {
    @FXML
    private Button quiz;
    @FXML
    private Button matching;
    @FXML
    private Button wordle;

    @FXML
    private void playGame(ActionEvent event) throws Exception {
        if (event.getSource() == quiz) {
            //loadGameScreen("quiz", quiz);
            return;
        }
        if (event.getSource() == matching) {
            loadGameScreen("matching", matching);
            MatchingGameController game = new MatchingGameController();
            game.startGame();
            return;
        }
        if (event.getSource() == wordle) {
            //loadGameScreen("wordle", wordle);
            return;
        }
        throw new Exception("Unknown button clicked");
    }
}
