package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameScreenController extends ControllersManager {
    private static String currentScreen = "home";
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
            return;
        }
        if (event.getSource() == matchingCard) {
            loadScreen("matchingCard", matchingCard);
            //MatchingCard matchingCard = new MatchingCard();
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
