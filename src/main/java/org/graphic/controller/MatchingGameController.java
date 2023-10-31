package org.graphic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.graphic.LoadMatchingDataThread;
import org.graphic.TimerThread;
import org.graphic.dictionary.Word;

import java.util.ArrayList;

public class MatchingGameController extends GameScreenController {
    protected final String dataPath = "src/main/resources/org/graphic/matching-data.txt";
    protected final ArrayList<Word> words = new ArrayList<>();
    protected ArrayList<Button> cards = new ArrayList<>();
    protected int count = 2;
    protected Button selected1;
    protected Button selected2;
    protected boolean isRunning = false;
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
    protected Label message = new Label();
    @FXML
    protected volatile Button card1 = new Button();
    @FXML
    protected volatile Button card2 = new Button();
    @FXML
    protected volatile Button card3 = new Button();
    @FXML
    protected volatile Button card4 = new Button();
    @FXML
    protected volatile Button card5 = new Button();
    @FXML
    protected volatile Button card6 = new Button();
    @FXML
    protected volatile Button card7 = new Button();
    @FXML
    protected volatile Button card8 = new Button();
    @FXML
    protected volatile Button card9 = new Button();
    @FXML
    protected volatile Button card10 = new Button();
    @FXML
    protected volatile Button card11 = new Button();
    @FXML
    protected volatile Button card12 = new Button();
    @FXML
    protected volatile Button card13 = new Button();
    @FXML
    protected volatile Button card14 = new Button();

    public void startGame() throws InterruptedException {
        LoadMatchingDataThread loadThread = new LoadMatchingDataThread();
        Thread load = new Thread(loadThread);
        load.start();
        //load.join();
    }

    public boolean checkMatch(String text1, String text2) {
        Word word1 = new Word(text1, text2);
        Word word2 = new Word(text2, text1);
        return words.contains(word1) || words.contains(word2);
    }

    public void handleAction(ActionEvent event) throws Exception {
        TimerThread timerThread = new TimerThread(message);
        Thread clock = new Thread(timerThread);
        if (!isRunning) {
            clock.start();
            isRunning = true;
        }
        System.out.println(cards.size());
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
        if (cards.isEmpty()) {
//            Label message = this.message;
//            message.setText("Game over !");
            //startTimer = false;
            //isRunning = false;
            //System.out.println("Timer stopped !");
            if (event.getSource() == playAgain || event.getSource() == restart) {
                //message.setText("Ready !");
                startGame();
            }
            if (event.getSource() == exit) {
                loadScreen("game", exit);
            }
        }
    }

    public void removeCards(Button card1, Button card2) {
        removeCard(card1);
        removeCard(card2);
    }

    public void removeCard(Button card) {
        card.setVisible(false);
    }
}
