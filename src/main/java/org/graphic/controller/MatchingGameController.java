package org.graphic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.graphic.dictionary.Word;

import java.util.ArrayList;

public class MatchingGameController extends GameScreenController {
    protected final String dataPath = "src/main/resources/org/graphic/data/matching-data.txt";
    protected final ArrayList<Word> words = new ArrayList<>();
    protected static ArrayList<String> targets = new ArrayList<>();
    protected static ArrayList<String> definitions = new ArrayList<>();
    protected static ArrayList<Button> cards = new ArrayList<>();
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
    protected Button card1;
    @FXML
    protected Button card2;
    @FXML
    protected Button card3;
    @FXML
    protected Button card4;
    @FXML
    protected Button card5;
    @FXML
    protected Button card6;
    @FXML
    protected Button card7;
    @FXML
    protected Button card8;
    @FXML
    protected Button card9;
    @FXML
    protected Button card10;
    @FXML
    protected Button card11;
    @FXML
    protected Button card12;
    @FXML
    protected Button card13;
    @FXML
    protected Button card14;

    public void startGame() throws InterruptedException {
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        cards.add(card8);
        cards.add(card9);
        cards.add(card10);
        cards.add(card11);
        cards.add(card12);
        cards.add(card13);
        cards.add(card14);
        LoadDataThread loadThread = new LoadDataThread();
        Thread load = new Thread(loadThread);
        load.start();
        load.join();
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
        if (event.getSource() == exit) {
            loadScreen("game", exit);
            clock.interrupt();
            return;
        }
        System.out.println(cards.size());
        if (count == 2) {
            selected1 = (Button) event.getSource();
            System.out.println(selected1);
            count--;
            return;
        }
        if (count == 1) {
            selected2 = (Button) event.getSource();
            System.out.println(selected2);
            if (checkMatch(selected1.getText(), selected2.getText())) {
                removeCards(selected1, selected2);
            }
            count = 2;
            return;
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
