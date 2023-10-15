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
            System.out.println("Play quiz");
            MatchingCard matchingCard = new MatchingCard();
            return;
        }
        if (event.getSource() == matchingCard) {
            System.out.println("Play matching cards");
            return;
        }
        if (event.getSource() == wordle) {
            System.out.println("Play wordle");
            return;
        }
        throw new Exception("Unknown button clicked");
    }
}
