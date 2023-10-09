package org.graphic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class SearchScreenController extends ControllersManager {
    @FXML
    private TextField textField;

    @FXML
    private Label response;

    @FXML
    private Button confirmSearch;

    public void deleteWordInTextField() {
        TextField textField = this.textField;
        textField.setText("");
    }

    public void searchWordFromTextField() throws SQLException {
        TextField textField = this.textField;
        String text = textField.getText();
        Dictionary dictionary = new Dictionary();
        dictionary.init();
        response.setText("Please wait for a moment...");
        String definition = dictionary.findWord(text).toLowerCase();
        response.setText(displayResult(definition));
        //System.out.println(definition);
    }

    public String reFormat(String in) {
        StringBuilder out = new StringBuilder();

        //Split string by "*"
        String[] parts = in.split("\\*");
        for (String part : parts) {
            //Remove "<i><q>" from the head of the string
            if (part.substring(0, 6).equals("<i><q>")) part = part.substring(6);

            //Remove <q/></i> from the tail of the string
            if (part.substring(part.length() - 8,part.length()).equals("</q></i>"))
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

                out.append(line + "\n");
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
                if (lines[i].substring(0, 3).equals("  ")) {
                    lines[i] = lines[i].substring(3);
                    lines[i] = lines[i].substring(0, 1).toUpperCase() + lines[i].substring(1);
                }
                display.append(lines[i] + "\n");
            }
//            if (lines[i + 1].substring(0, 3).equals("  ") || lines[i + 1].charAt(0) == '*') {
//                display.append("___________________________________________________\n");
//            }
        }
        return display.toString();
    }
}
