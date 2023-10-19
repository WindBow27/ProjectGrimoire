package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class MatchingGameController extends GameScreenController {
    private final int numberOfCard = 14;
    private final int limitLength = 10;
    private final ArrayList<String> words = new ArrayList<>();
    private final ArrayList<String> meanings = new ArrayList<>();
    private final ArrayList<Button> buttons = new ArrayList<>();
    private int count = 2;
    private Button selected1;
    private Button selected2;
    private boolean endGame = false;
    private boolean startTimer = false;
    private double startTime;
    private double endTime;
    @FXML
    private Button playAgain;
    @FXML
    private Button restart;
    @FXML
    private Button exit;
    @FXML
    private Label message;

    public void timer() {
        while (!endGame) {
            Label message = this.message;
            endTime = (double) System.currentTimeMillis() / 1000;
            if (message != null) message.setText(String.valueOf(endTime - startTime));
        }
//        System.out.println(this.message.getText());
    }

    public void getDataFromDB() throws SQLException, IOException, InterruptedException {
        for (int i = 0; i < numberOfCard; i++) {

            //Random a number to choose a word from DB
            Random random = new Random();
            int temp = random.nextInt(0, 1000);
            Dictionary dictionary = new Dictionary();
            dictionary.init();

            //Find word by ID
            String result = dictionary.findWordByID(temp);
            String meaning = dictionary.translateWord(result, "en");
            words.add(result);
            meanings.add(meaning);
        }
        for (String x : words) System.out.println(x);
        for (String x : meanings) System.out.println(x);
    }

    public boolean checkMatch(String text1, String text2) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(text1) && meanings.get(i).equals(text2)
                    || words.get(i).equals(text2) && meanings.get(i).equals(text1)) return true;
        }
        return false;
    }

    public void handleAction(ActionEvent event) throws Exception {
        if (!startTimer) {
            startTime = (double) System.currentTimeMillis() / 1000;
        }
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
        if (words.isEmpty() && meanings.isEmpty()) {
            Label message = this.message;
            startTimer = false;
            endTime = (double) System.currentTimeMillis() / 1000;
            message.setText("GAME OVER\n" + (endTime - startTime));
            if (event.getSource() == playAgain || event.getSource() == restart) {
                message.setText("Ready !");
                //getDataFromDB();
                endGame = false;
            }
            if (event.getSource() == exit) {
                endGame = true;
                loadScreen("game", exit);
            }
        }
    }

    public void removeCards(Button card1, Button card2) {
        removeCard(card1);
        removeCard(card2);
    }

    public void removeCard(Button card) {
        words.remove(card.getText());
        meanings.remove(card.getText());
        card.setVisible(false);
    }
}
