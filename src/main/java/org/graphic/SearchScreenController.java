package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

public class SearchScreenController extends ControllersManager {
    @FXML
    private TextArea TextArea;

    @FXML
    private Label response;

    public void deleteWordInTextArea() {
        TextArea TextArea = this.TextArea;
        TextArea.setText("");
    }

    public void searchWordFromTextArea() throws SQLException {
        TextArea TextArea = this.TextArea;
        String text = TextArea.getText();
        Dictionary dictionary = new Dictionary();
        dictionary.init();
        response.setText("Please wait for a moment...");
        String definition = dictionary.findWord(text).toLowerCase();
        response.setText(reFormat(definition));
        //System.out.println(definition);
    }

    public String reFormat(String in) {
        StringBuilder out = new StringBuilder();

        //Split string by "*"
        String[] parts = in.split("\\*");
        for (String part : parts) {
            //Remove "<i><q>" from the head of the string
            if (part.startsWith("<i><q>")) part = part.substring(6);

            //Remove <q/></i> from the tail of the string
            if (part.endsWith("</q></i>"))
                part = part.substring(0, part.length() - 8);

            //Split string by "<br />"
            String[] lines = part.split("\\<br />");
            for (String line : lines) {
                line = line.replace('+', ':');
                line = line.replace('=', '*');

                // Remove only the first "!" in the string
                if (line.indexOf('!') != -1) {
                    line = line.substring(0, line.indexOf('!')) + line.substring(line.indexOf('!') + 1);
                }

                out.append(line).append("\n");
            }
        }
        System.out.println(out);
        return out.toString();
    }

    public String displayResult(String result) {
        StringBuilder display = new StringBuilder();
        String[] lines = reFormat(result).split("\\n");
        for (int i = 0; i < lines.length - 1; i++) {
            if (lines[i].length() >= 2) {
                display.append(lines[i]).append("\n");
            }
        }
        return display.toString();
    }
}
