package org.graphic.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import org.graphic.app.AppConfig;
import org.graphic.dictionary.Dictionary;
import org.graphic.dictionary.Word;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class DictionaryScreenController {
    private final AppConfig appConfig = new AppConfig();
    private final ArrayList<Word> words = new ArrayList<>();
    private final Dictionary dictionary = new Dictionary();
    @FXML
    private ListView<String> suggestions;
    @FXML
    private TextField searchField;
    @FXML
    private WebView response;
    @FXML
    private TextArea wordArea;
    @FXML
    private TextArea meaningArea;
    @FXML
    private Label announceLabel;


    public void initialize() throws SQLException {
        dictionary.init();
    }

    public void deleteWordSearchField() {
        searchField.setText("");
        displaySuggestions();
        searchWord("");
    }

    public void displaySuggestions() {
        String text = searchField.getText();
        suggestions.getItems().clear();
        if (text.isEmpty()) {
            response.getEngine().loadContent("", "text/html");
            return;
        }
        suggestions.getItems().addAll(dictionary.suggestWords(text));
        if (suggestions.getItems().isEmpty()) {
            response.getEngine().loadContent("", "text/html");
            return;
        }
        searchWord(suggestions.getItems().get(0));
    }

    public void searchWord(String text) {
        if (text.isEmpty()) {
            response.getEngine().loadContent("", "text/html");
            return;
        }
        Word word = new Word(text, "");
        word.removeSpace(word);
        String definition;
        if (dictionary.findWordHTML(word) != null) {
            definition = dictionary.findWordHTML(word);
        } else {
            definition = dictionary.findWord(word);
        }
        response.getEngine().loadContent(definition, "text/html");
    }

    public void successAction(String message) {
        announceLabel.setText(message);
        announceLabel.setTextFill(Color.rgb(125, 255, 126));
    }

    public void failAction(String message) {
        announceLabel.setText(message);
        announceLabel.setTextFill(Color.rgb(255, 89, 89));
    }

    public void clearAction() {
        wordArea.setText("");
        meaningArea.setText("");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            Platform.runLater(() -> {
                announceLabel.setText("");
            });
        }, 3, java.util.concurrent.TimeUnit.SECONDS);
    }

    public boolean isEmptyArea() {
        String wordAreaText = wordArea.getText();
        String meaningAreaText = meaningArea.getText();

        if (wordAreaText.isEmpty() && meaningAreaText.isEmpty()) {
            return true;
        }

        List<String> wordAreaLines = List.of(wordAreaText.split("\n"));
        List<String> meaningAreaLines = List.of(meaningAreaText.split("\n"));

        for (int i = 0; i < wordAreaLines.size(); i++) {
            String word = wordAreaLines.get(i);
            String meaning = meaningAreaLines.get(i);
            Word newWord = new Word(word, meaning);
            words.add(newWord);
        }

        for (Word word : words) {
            System.out.println(word.getWordTarget() + " " + word.getWordExplain());
        }
        System.out.println("passed test");
        return false;
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
        searchWord(searchField.getText());
        successAction("Added successfully");
        clearAction();
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
        searchWord(searchField.getText());
        successAction("Deleted successfully");
        clearAction();
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
        searchWord(searchField.getText());
        successAction("Updated successfully");
        clearAction();
    }
}

