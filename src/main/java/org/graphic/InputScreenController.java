package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class InputScreenController extends ControllersManager {
    @FXML
    private TextArea textArea;

    @FXML
    private void handleSearchButtonAction() throws IOException {
        // Handle the search button action here
    }

    public void readWordFromTextArea() {
        TextArea textArea = this.textArea;
        String text = textArea.getText();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromText(text);
        dictionaryManagement.showAllWords();
    }
}
