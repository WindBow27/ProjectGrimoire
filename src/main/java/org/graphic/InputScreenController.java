package org.graphic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class InputScreenController extends ControllersManager {
    @FXML
    private Button confirm;
    @FXML
    private TextArea textArea;


    public void initialize() {
        Platform.runLater(() -> textArea.requestFocus());
       textArea.setWrapText(true);
    }
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
