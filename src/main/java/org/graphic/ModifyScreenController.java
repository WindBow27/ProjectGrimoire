package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyScreenController extends ControllersManager {
    private final AppConfig appConfig = new AppConfig();
    private final ArrayList<Word> words = new ArrayList<>();
    private final Dictionary dictionary = new Dictionary();
    @FXML
    private TextArea textArea;
    @FXML
    private Label label;

    public void initialize() throws SQLException {
        textArea.setWrapText(true);
        dictionary.init();
    }

    public boolean isEmptyArea() {
        TextArea textArea = this.textArea;
        String text = textArea.getText();

        if (text.isEmpty()) {
            return true;
        }

        if (hasNoSpace(text)) {
            words.add(new Word(text, ""));
            return false;
        }

        // Split the text using regular expressions to handle different newline characters
        String[] lines = text.split("\n");
        for (String line : lines) {
            // Split each line into words using space as a delimiter
            String[] wordsInLine = line.split(" ");

            if (wordsInLine.length == 1) {
                words.add(new Word(wordsInLine[0], ""));
            } else if (wordsInLine.length > 1) {
                String key = wordsInLine[0];
                String meaning = line.substring(key.length()).trim();
                Word word = new Word(key, meaning);
                words.add(word);
            }
        }
        for (Word word : words) {
            System.out.println(word.getWordTarget() + " " + word.getWordExplain());
        }
        return false;
    }

    public boolean hasNoSpace(String sample) {
        for (int i = 0; i < sample.length(); i++) {
            if (sample.charAt(i) == ' ' || sample.charAt(i) == '\t') return false;
        }
        return true;
    }

    public void successAction(String message) {
        label.setText(message);
        label.setTextFill(Color.rgb(125, 255, 126));
    }

    public void failAction(String message) {
        label.setText(message);
        label.setTextFill(Color.rgb(255, 89, 89));
    }

    public boolean isEmpty(String message) {
        if (isEmptyArea()) {
            failAction("No word to " + message);
            return true;
        }
        return false;
    }

    public boolean isExist(Word word) {
        if (dictionary.findWord(word) == null
                || dictionary.findWord(word).equals(appConfig.getDBError())) {
            failAction("Word does not exist");
            return false;
        }
        failAction("Word already exists");
        return true;
    }

    public boolean hasNoMeaning(Word word) {
        if (word.getWordExplain().isEmpty()) {
            failAction("Word has no meaning");
            return true;
        }
        return false;
    }

    public void addWord() {
        words.clear();
        if (isEmpty("add")) return;
        for (Word word : words) {
            if (isExist(word)) return;
            if (hasNoMeaning(word)) return;
            if (!dictionary.addWord(word)) {
                failAction("Failed to add word");
                return;
            }
        }
        successAction("Added successfully");
    }

    public void deleteWord() {
        words.clear();
        if (isEmpty("delete")) return;
        for (Word word : words) {
            if (!isExist(word)) return;
            if (!dictionary.deleteWord(word)) {
                failAction("Failed to delete word");
                return;
            }
        }
        successAction("Deleted successfully");
    }

    public void updateWord() {
        words.clear();
        if (isEmpty("update")) return;
        for (Word word : words) {
            if (hasNoMeaning(word)) return;
            if (!dictionary.updateWord(word)) {
                failAction("Failed to update word");
                return;
            }
        }
        successAction("Updated successfully");
    }
}
