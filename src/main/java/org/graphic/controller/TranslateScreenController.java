package org.graphic.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.graphic.dictionary.Dictionary;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TranslateScreenController extends ControllersManager {
    private final Logger logger = Logger.getLogger(TranslateScreenController.class.getName());
    @FXML
    private ImageView loading;
    @FXML
    private TextArea response;
    @FXML
    private Label en;
    @FXML
    private Label vi;
    @FXML
    private TextArea textArea;
    private String tl = "vi";

    public void initialize() {
        Platform.runLater(() -> textArea.requestFocus());
        textArea.setWrapText(true);
        response.setWrapText(true);
    }

    public void deleteWordInTextArea() {
        TextArea textArea = this.textArea;
        textArea.setText("");
        response.setText("");
    }

    public void translateWordFromTextArea() {
        loading.setVisible(true);
        if (textArea.getText() == null || textArea.getText().isEmpty()) return;
        TextArea textArea = this.textArea;
        String text = textArea.getText();
        Dictionary dictionary = new Dictionary();
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                String definition = dictionary.translateWord(text, tl);
                response.setText(definition);
                System.out.println(definition);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An exception occurred", e);
            }
        });
        future.thenRun(() -> Platform.runLater(() -> loading.setVisible(false)));
    }

    public void swapLanguage() {
        //Swap title of translator
        String tmp = en.getText();
        en.setText(vi.getText());
        vi.setText(tmp);

        //Swap content of translator
        tmp = textArea.getText();
        textArea.setText(response.getText());
        response.setText(tmp);

        //Swap language
        tl = tl.equals("vi") ? "en" : "vi";
    }

    public void playSound(String current, String alt, String text) {
        if (text == null) return;
        if (tl.equals("vi")) {
            playSoundGoogleTranslate(text, current);
            return;
        }
        playSoundGoogleTranslate(text, alt);

    }

    public void playSoundLeft() {
        playSound("en", "vi", textArea.getText());
    }

    public void playSoundRight() {
        playSound("vi", "en", response.getText());
    }
}

