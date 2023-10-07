package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ControllersManager {
    @FXML
    private Button myDictionary;

    @FXML
    private Button translate;

    @FXML
    private Button search;

    @FXML
    private Button game;

    @FXML
    private Button delete;

    @FXML
    private Button home;

    @FXML
    public void hovered() {
        changeColor(home);
        changeColor(myDictionary);
        changeColor(translate);
        changeColor(search);
        changeColor(game);
        changeColor(delete);
    }

    public void changeColor(Button button) {
        if (button == null) return;
        if (button != delete) {
            button.setOnMouseEntered(event -> {
                // Set the button's text fill color to yellow when the mouse enters the button.
                button.setTextFill(Color.YELLOW);
            });

            button.setOnMouseExited(event -> {
                // Set the button's text fill color to white when the mouse exits the button.
                button.setTextFill(Color.WHITE);
            });
        } else {
            button.setOnMouseEntered(event -> {
                // Set the button's background color to light blue when the mouse enters the button.
                button.setStyle("-fx-background-color : #dff5f5");
                button.setTranslateX(220);
                button.setTranslateY(-85);
            });

            button.setOnMouseExited(event -> {
                // Set the button's background color to white when the mouse exits the button.
                button.setStyle("-fx-background-color : #ffffff");
                button.setTranslateX(220);
                button.setTranslateY(-85);
            });
        }
    }

    public void changeColor(MenuButton button) {
        button.setOnMouseEntered(event -> {
            // Set the button's background color to yellow when the mouse enters the button.
            button.setTextFill(Color.YELLOW);
        });

        button.setOnMouseExited(event -> {
            // Set the button's background color to white when the mouse exits the button.
            button.setTextFill(Color.WHITE);
        });
    }

    public void changeColor(Label label) {
        label.setOnMouseEntered(event -> {
            // Set the button's opacity when the mouse enters the image.
            label.setOpacity(0.9);
        });

        label.setOnMouseExited(event -> {
            // Set the button's opacity when the mouse exits the image.
            label.setOpacity(1);
        });
    }

    public void changeColor(ImageView imageView) {
        imageView.setOnMouseEntered(event -> {
            // Set the button's opacity when the mouse enters the image.
            imageView.setOpacity(0.8);
        });

        imageView.setOnMouseExited(event -> {
            // Set the button's opacity when the mouse exits the image.
            imageView.setOpacity(1);
        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if (event.getSource() == home) {
            stage = (Stage) home.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/main-screen.fxml"));
        } else if (event.getSource() == myDictionary) {
            stage = (Stage) myDictionary.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/input-screen.fxml"));
        } else if (event.getSource() == translate) {
            stage = (Stage) translate.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("fxml/translate-screen.fxml"));
        } else {
            throw new Exception("Unknown button clicked");
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}


//    private String definition;
//
//    public String getDefinition() {
//        return definition;
//    }
//
//    public void setDefinition(String definition) {
//        this.definition = definition;
//    }
//    public void readWordFromTextArea() {
//        TextArea textArea = this.textArea;
//        String text = textArea.getText();
//        DictionaryManagement dictionaryManagement = new DictionaryManagement();
//        dictionaryManagement.insertFromText(text);
//        //dictionaryManagement.showALlWords();
//    }
//
//    public void translateWordFromTextArea() throws IOException, InterruptedException, SQLException {
//        TextArea textArea = this.textArea;
//        String text = textArea.getText();
//        DictionaryManagement dictionaryManagement = new DictionaryManagement();
//        String definition = dictionaryManagement.translateWord(text, tl);
//        response.setText(definition);
//        System.out.println(definition);
//    }
//
//    public void swapLanguage() {
//        //Swap title of translator
//        String tmp = en.getText();
//        en.setText(vi.getText());
//        vi.setText(tmp);
//
//        //Swap content of translator
//        tmp = textArea.getText();
//        textArea.setText(response.getText());
//        response.setText(tmp);
//
//        //Swap language
//        if (tl.equals("vi")) tl = "en";
//        else tl = "vi";
//    }
//
//    public void playSoundEn() {
//        if (tl.equals("vi")) playSoundGoogleTranslate(textArea.getText(), "en");
//        else playSoundGoogleTranslate(textArea.getText(), "vi");
//    }
//
//    public void playSoundVi() {
//        if (response.getText() == null) return;
//        if (tl.equals("vi")) playSoundGoogleTranslate(response.getText(), "vi");
//        else playSoundGoogleTranslate(response.getText(), "en");
//    }
//    public void deleteWordInTextArea() {
//        TextArea textArea = this.textArea;
//        textArea.setText("");
//    }