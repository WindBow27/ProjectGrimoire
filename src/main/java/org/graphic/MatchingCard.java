package org.graphic;

import javafx.scene.control.Button;

public class MatchingCard extends GameScreenController {
    private String[] words;
    private String[] meanings;
    private Button selected1;
    private Button selected2;

    MatchingCard() {
        startGame();
    }

    public void startGame() {
    }

    public void getDataFromDB() {

    }

    public boolean checkMatch(String text1, String text2) {
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(text1) && meanings[i].equals(text2)
                    || words[i].equals(text2) && meanings[i].equals(text1)) return true;
        }
        return false;
    }
}
