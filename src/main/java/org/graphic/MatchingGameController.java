package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;

public class MatchingGameController extends GameScreenController {
    protected final String dataPath = "src/main/resources/org/graphic/matching-data.txt";
    protected final int numberOfWord = 7;
    protected final int numberOfCard = 14;
    protected final ArrayList<String> targets = new ArrayList<>();
    protected final ArrayList<String> definitions = new ArrayList<>();
    protected final ArrayList<Word> words = new ArrayList<>();
    protected final ArrayList<Button> cards = new ArrayList<>();
    protected int count = 2;
    protected Button selected1;
    protected Button selected2;
    protected boolean endGame = false;
    protected boolean startTimer = false;
    protected double startTime;
    protected double endTime;
    @FXML
    protected Button playAgain;
    @FXML
    protected Button restart;
    @FXML
    protected Button exit;
    @FXML
    protected Label message;
    @FXML
    protected Button card1 = new Button();
    @FXML
    protected Button card2 = new Button();
    @FXML
    protected Button card3 = new Button();
    @FXML
    protected Button card4 = new Button();
    @FXML
    protected Button card5 = new Button();
    @FXML
    protected Button card6 = new Button();
    @FXML
    protected Button card7 = new Button();
    @FXML
    protected Button card8 = new Button();
    @FXML
    protected Button card9 = new Button();
    @FXML
    protected Button card10 = new Button();
    @FXML
    protected Button card11 = new Button();
    @FXML
    protected Button card12 = new Button();
    @FXML
    protected Button card13 = new Button();
    @FXML
    protected Button card14 = new Button();

    public void startGame() {
        LoadMatchingDataThread loadThread = new LoadMatchingDataThread();
        Thread load = new Thread(loadThread);
        TimerThread timerThread = new TimerThread();
        Thread clock = new Thread(timerThread);
        load.start();
        clock.start();
    }
    public boolean checkMatch(String text1, String text2) {
        Word word1 = new Word(text1, text2);
        Word word2 = new Word(text2, text1);
        if (words.contains(word1) || words.contains(word2))  {
            return true;
        }
        return false;
    }

    public void handleAction(ActionEvent event) throws Exception {
        startTimer = true;
        System.out.println("clicked");
        if (count == 2) {
            selected1 = (Button) event.getSource();
            count--;
        }
        if (count == 1) {
            selected2 = (Button) event.getSource();
            if (checkMatch(selected1.getText(), selected2.getText())) {
                removeCards(selected1, selected2);
            }
            count = 2;
        }
//        if (words.isEmpty()) {
//            Label message = this.message;
//            startTimer = false;
//            if (event.getSource() == playAgain || event.getSource() == restart) {
//                message.setText("Ready !");
//                endGame = false;
//            }
//            if (event.getSource() == exit) {
//                endGame = true;
//                loadScreen("game", exit);
//            }
//        }
    }

    public void removeCards(Button card1, Button card2) {
        removeCard(card1);
        removeCard(card2);
    }

    public void removeCard(Button card) {
        card.setVisible(false);
    }
}
