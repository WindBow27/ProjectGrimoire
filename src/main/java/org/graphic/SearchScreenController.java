package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.io.IOException;
import java.sql.SQLException;

public class SearchScreenController extends ControllersManager {
    @FXML
    private TextArea TextArea;

    @FXML
    private WebView response;

    public void deleteWordInTextArea() {
        TextArea TextArea = this.TextArea;
        TextArea.setText("");
    }

    public void searchWordFromTextArea() throws SQLException, IOException {
        TextArea TextArea = this.TextArea;
        String text = TextArea.getText();
        Dictionary dictionary = new Dictionary();
        dictionary.init();
        String definition = dictionary.findWord(text).toLowerCase();
        WebEngine webEngine = response.getEngine();
        webEngine.loadContent(definition, "text/html");
    }
}
