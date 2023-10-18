package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModifyScreenController extends ControllersManager {
    private ArrayList<String> words = new ArrayList<>();
    private ArrayList<String> meanings = new ArrayList<>();
    private boolean successful = true;
    @FXML
    private TextArea textArea;
    @FXML
    private Label label;

    public void initialize() {
        textArea.setWrapText(true);
    }

    public boolean getWordFromTextArea() {
        TextArea textArea = this.textArea;
        String text = textArea.getText();
        //System.out.println(text);
        if (text.length() < 1) {
            return false;
        }

        if (!containSpace(text)) {
            words.add(text);
            return true;
        }

        // Split the text using regular expressions to handle different newline characters
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (!containSpace(line)) {
                words.add(line);
                break;
            }
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == ' ') {
                    words.add(line.substring(0, i));
                    meanings.add(line.substring(i+1));
                    break;
                }
            }
        }
        return true;
    }

    public boolean containSpace(String sample) {
        for (int i = 0; i < sample.length(); i++) {
            if (sample.charAt(i) == ' ' || sample.charAt(i) == '\t') return true;
        }
        return false;
    }

    public void addWord() throws SQLException, IOException {
        if (!getWordFromTextArea()) {
            label.setText("Failed to add words");
            label.setTextFill(Color.rgb(255, 89, 89));
            return;
        }
        Dictionary dictionary = new Dictionary();
        dictionary.init();

        for (int i = 0; i < words.size(); i++) {
            if(!dictionary.addWord(words.get(i), meanings.get(i))) {
                label.setText("Failed to add words");
                label.setTextFill(Color.rgb(255, 89, 89));
                return;
            }
        }
        label.setText("Added words successfully");
        label.setTextFill(Color.rgb(125, 255, 126));
        words.clear();
        meanings.clear();
    }

    public void deleteWord() throws SQLException, IOException {
        if (!getWordFromTextArea()) {
            label.setText("Failed to delete words");
            label.setTextFill(Color.rgb(255, 89, 89));
            return;
        }
        Dictionary dictionary = new Dictionary();
        dictionary.init();

        for (int i = 0; i < words.size(); i++) {
            if(!dictionary.deleteWord(words.get(i))) {
                label.setText("Failed to delete words");
                label.setTextFill(Color.rgb(255, 89, 89));
                words.clear();
                return;
            }
        }
        label.setText("Deleted words successfully");
        label.setTextFill(Color.rgb(125, 255, 126));
        words.clear();
    }

    public void updateWord() throws SQLException, IOException {
        if (!getWordFromTextArea()) {
            label.setText("Failed to update words");
            label.setTextFill(Color.rgb(255, 89, 89));
            return;
        }
        Dictionary dictionary = new Dictionary();
        dictionary.init();

        for (int i = 0; i < words.size(); i++) {
            if(!dictionary.updateWord(words.get(i), meanings.get(i))) {
                label.setText("Failed to update words");
                label.setTextFill(Color.rgb(255, 89, 89));
                return;
            }
        }
        label.setText("Updated words successfully");
        label.setTextFill(Color.rgb(125, 255, 126));
        words.clear();
        meanings.clear();
    }
}
