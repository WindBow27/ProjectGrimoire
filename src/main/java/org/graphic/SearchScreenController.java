package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

import java.sql.SQLException;
import java.util.Objects;

public class SearchScreenController extends ControllersManager {
    @FXML
    private TextArea TextArea;

    @FXML
    private WebView response;

    public void initialize() {
        response.getEngine().setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("css/web-view.css")).toString());
    }

    public void deleteWordInTextArea() {
        TextArea TextArea = this.TextArea;
        TextArea.setText("");
    }

    public void searchWordFromTextArea() throws SQLException {
        TextArea TextArea = this.TextArea;
        String text = TextArea.getText();
        Word word = new Word(text, "");
        word.removeSpace(word);
        Dictionary dictionary = new Dictionary();
        dictionary.init();
        String definition;
        if (dictionary.findWordHTML(word) != null) {
            definition = dictionary.findWordHTML(word);
        } else {
            definition = dictionary.findWord(word);
        }
        response.getEngine().loadContent(definition, "text/html");
    }
}
