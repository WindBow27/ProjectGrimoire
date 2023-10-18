package org.graphic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ModifyScreenController extends ControllersManager {
    @FXML
    private TextArea textArea;

    public void initialize() {
        Platform.runLater(() -> textArea.requestFocus());
        textArea.setWrapText(true);
    }

    public void readWordFromTextArea() {
    }
}
