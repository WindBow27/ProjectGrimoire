package org.graphic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TranslateScreenController extends ControllersManager {
    private final Logger logger = Logger.getLogger(TranslateScreenController.class.getName());
    @FXML
    private Label response;
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

    public void playSoundGoogleTranslate(String text, String tl) {
        if (text == null || text.isEmpty()) return;
        try {
            String soundAPI = "https://translate.google.com/translate_tts?ie=UTF-8&tl=";
            String urlStr = soundAPI + tl + "&client=tw-ob&q="
                    + URLEncoder.encode(text, StandardCharsets.UTF_8);

            URI uri = URI.create(urlStr);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream audio = connection.getInputStream();
            new Player(audio).play();
            connection.disconnect();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An exception occurred", e);
        }
    }

    public void deleteWordInTextArea() {
        TextArea textArea = this.textArea;
        textArea.setText("");
        response.setText("");
    }

    public void translateWordFromTextArea() throws IOException {
        if (textArea.getText() == null || textArea.getText().isEmpty()) return;
        TextArea textArea = this.textArea;
        String text = textArea.getText();
        Dictionary dictionary = new Dictionary();
        String definition = dictionary.translateWord(text, tl);
        response.setText(definition.toLowerCase());
        System.out.println(definition);
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

