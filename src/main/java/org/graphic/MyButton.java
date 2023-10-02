package org.graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MyButton extends HandleEvent {
    @FXML
    private ImageView find;

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
    private Button confirmTranslate;

    @FXML
    private TextArea textArea;

    @FXML
    public void hovered() {
        changeColor(home);
        changeColor(myDictionary);
        changeColor(translate);
        changeColor(search);
        changeColor(game);
        changeColor(delete);
        changeColor(find);
    }

    public void changeColor(Button button) {
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
            });

            button.setOnMouseExited(event -> {
                // Set the button's background color to white when the mouse exits the button.
                button.setStyle("-fx-background-color : #ffffff");
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

    public void changeColor(ImageView image) {
        image.setOnMouseEntered(event -> {
            // Set the button's opacity when the mouse enters the image.
            image.setOpacity(0.9);
        });

        image.setOnMouseExited(event -> {
            // Set the button's opacity when the mouse exits the image.
            image.setOpacity(1);
        });
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;

        if (event.getSource() == home) {
            stage = (Stage) home.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("main-screen.fxml"));
        } else if (event.getSource() == myDictionary) {
            stage = (Stage) myDictionary.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("search.fxml"));
        } else if (event.getSource() == translate) {
            stage = (Stage) translate.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("translate-screen.fxml"));
        } else {
            throw new Exception("Unknow button clicked");
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void readWordFromTextArea() {
        TextArea textArea = this.textArea;
        String text = textArea.getText();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.insertFromText(text);
        //dictionaryManagement.showALlWords();
    }

    public void translateWordFromTextArea() throws IOException, InterruptedException, SQLException {
        TextArea textArea = this.textArea;
        String text = textArea.getText();
        Dictionary database = new Dictionary();
        database.init();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        String definition = dictionaryManagement.translateWord(text);
        System.out.println(definition);
    }
}
