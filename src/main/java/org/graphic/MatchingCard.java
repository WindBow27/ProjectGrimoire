package org.graphic;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class MatchingCard extends GameScreenController {
    private static int count = 2;
    private final int numberOfCard = 10;
    private final ArrayList<String> words = new ArrayList<>();
    private final ArrayList<String> meanings = new ArrayList<>();
    private Button selected1;
    private Button selected2;

    MatchingCard() throws SQLException, IOException {
        startGame();
    }

    public void startGame() throws SQLException, IOException {
        getDataFromDB();
        while (true) {
            if (words.isEmpty() && meanings.isEmpty()) return;
        }
    }

    public void getDataFromDB() throws SQLException, IOException {
        for (int i = 0; i < numberOfCard; i++) {

            //Random a number to choose a word from DB
            Random random = new Random();
            int temp = random.nextInt(80, 138480);
            Dictionary dictionary = new Dictionary();
            dictionary.init();

            //Find word by ID
            String result = dictionary.findWordByID(temp);

            //Extract word and meaning from result
            String word = getWord(result);
            DictionaryManagement dictionaryManagement = new DictionaryManagement();
            String meaning = dictionaryManagement.translateWord(word, "en");
            words.add(word);
            meanings.add(meaning);
        }
    }

    public String getWord(String sample) {
        for (int i = 0; i < sample.length(); i++) {
            if (sample.charAt(i) == '@') {
                int j = i + 1;
                while (sample.charAt(j) != ' ') j++;
                String word = sample.substring(i + 1, j);
                return word;
            }
        }
        return null;
    }

    public boolean checkMatch(String text1, String text2) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(text1) && meanings.get(i).equals(text2)
                    || words.get(i).equals(text2) && meanings.get(i).equals(text1)) return true;
        }
        return false;
    }

    public void handleAction(ActionEvent event) {
        if (count == 2) {
            selected1 = (Button) event.getSource();
            count--;
        }
        if (count == 1) {
            selected2 = (Button) event.getSource();
            if (checkMatch(selected1.getText(), selected2.getText())) {
                removeCard(selected1, selected2);
                if (words.isEmpty() && meanings.isEmpty()) return;
            }
            count = 2;
        }
    }

    public void removeCard(Button card1, Button card2) {
        words.remove(card1.getText());
        words.remove(card2.getText());
        meanings.remove(card1.getText());
        meanings.remove(card2.getText());
        card1.setVisible(false);
        card2.setVisible(false);
    }
}
