package org.graphic;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TranslateScreenController extends ControllersManager {
    private final Logger logger = Logger.getLogger(TranslateScreenController.class.getName());
    @FXML
    private Label swapLang;
    @FXML
    private Label response;
    @FXML
    private Label en;
    @FXML
    private Label vi;
    @FXML
    private ImageView soundEnglish;
    @FXML
    private ImageView soundVietnamese;
    @FXML
    private TextArea textArea;
    private String definition;
    private String tl = "vi";

    public void initialize() {
        Platform.runLater(() -> textArea.requestFocus());
        textArea.setWrapText(true);
        response.setWrapText(true);
    }

    public void playSoundGoogleTranslate(String text, String tl) {
        if (text == null || text.isEmpty()) return;
        try {
            String urlStr = "https://translate.google.com/translate_tts?ie=UTF-8&tl=" + tl + "&client=tw-ob&q="
                    + URLEncoder.encode(text, StandardCharsets.UTF_8);

            URL url = new URL(urlStr);
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

    public void translateWordFromTextArea() throws IOException, InterruptedException, SQLException {
        if (textArea.getText() == null || textArea.getText().isEmpty()) return;
        TextArea textArea = this.textArea;
        String text = textArea.getText();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        String definition = dictionaryManagement.translateWord(text, tl);
        response.setText(definition);
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
        if (tl.equals("vi")) tl = "en";
        else tl = "vi";
    }

    public void playSoundEn() {
        if (tl.equals("vi")) playSoundGoogleTranslate(textArea.getText(), "en");
        else playSoundGoogleTranslate(textArea.getText(), "vi");
    }

    public void playSoundVi() {
        if (response.getText() == null) return;
        if (tl.equals("vi")) playSoundGoogleTranslate(response.getText(), "vi");
        else playSoundGoogleTranslate(response.getText(), "en");
    }
}
