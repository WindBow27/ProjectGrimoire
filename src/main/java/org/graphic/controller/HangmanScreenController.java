package org.graphic.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class HangmanScreenController extends GameScreenController {
    @FXML
    private ImageView img;
    private final Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/1.png")));
    private final Image image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/2.png")));
    private final Image image3 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/3.png")));
    private final Image image4 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/4.png")));
    private final Image image5 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/5.png")));
    private final Image image6 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/6.png")));
    private final Image image7 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/7.png")));
    private final Image image8 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/graphic/image/hangman/8.png")));
    private String word;
    private String hint_str;
    private int letter_size;
    private int current_letter;
    private int life;
    private final List<String> data = new ArrayList<>();
    private final List<String> usedLetter = new ArrayList<>();
    protected static String hangmanDataPath = "src/main/resources/org/graphic/data/hangman-data.txt";

    @FXML
    private Label tf1;
    @FXML
    private Label tf2;
    @FXML
    private Label tf3;
    @FXML
    private Label tf4;
    @FXML
    private Label tf5;
    @FXML
    private  Label tf6;
    @FXML
    private Label tf7;
    @FXML
    private  Label tf8;
    @FXML
    private  Label tf9;
    @FXML
    private Label tf10;
    @FXML
    private TextField input;
    @FXML
    private Label hint;
    @FXML
    private  Label used;
    @FXML
    private  Label lives;
    @FXML
    private Label announce;
    @FXML
    private Label announce_big;
    @FXML
    private  Button exit;

    public void initialize(){
        getData(data);
        setVariable();
        setInvisible();
        input.requestFocus();
    }

    public void exit() throws Exception {
        loadScreen("game", exit);
    }

    public void replayGame() {
        clearTextFields();
        setVariable();
        setInvisible();
        resetImage();
        usedLetter.clear();
        used.setText("Used letter: ");
        hint.setText("");
        lives.setText("Lives: " + life);
        announce.setText("");
        announce_big.setText("");
    }

    public void setText(String text, Label label, int time, String color) {
        switch (color) {
            case "green" -> label.setTextFill(Color.rgb(35,226,0));
            case "red" -> label.setTextFill(Color.rgb(255, 89, 89));
        }
        label.setText(text);
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> Platform.runLater(() -> label.setText("")), time, java.util.concurrent.TimeUnit.SECONDS);
    }

    public void setText(String text, Label label, String color) {
        switch (color) {
            case "green" -> label.setTextFill(Color.rgb(35,226,0));
            case "red" -> label.setTextFill(Color.rgb(255, 89, 89));
        }
        label.setText(text);
    }

    private void resetImage() {
        img.setImage(image1);
        life = 6;
    }

    public void addUsedLetter(){
        String str = input.getText();
        str = str.toUpperCase();
        usedLetter.add(str);
        used.setText("Used letters: ");
        for (String s : usedLetter) {
            used.setText(used.getText() + s + " ");
        }
    }

    public void getData(List<String> data){
        if (!data.isEmpty()) return;
        try {
            Scanner fileScanner = new Scanner(new File(hangmanDataPath));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
        System.out.println(data.size());
    }

    public void setVariable() {
        int random = new Random().nextInt(data.size());
        String word_hint = data.get(random);
        String[] split = word_hint.split(" ", 2);
        word = split[0];
        hint_str = split[1];
        letter_size = word.length();
        life = 6;
        current_letter = 0;
    }

    public void showHint(){
        if (life < 1) return;
        hint.setText(hint_str);
    }

    public void CheckInput(){
        if (life < 1 || !announce_big.getText().isEmpty()) return;
        String str = input.getText();
        str = str.toUpperCase();
        if (str.length() != 1) {
            setText("Please enter a letter!", announce, 1,"red");
            return;
        }
        if (!Character.isLetter(str.charAt(0))) {
            setText("Please enter a letter!", announce, 1,"red");
            return;
        }
        if (usedLetter.contains(str)) {
            setText("You have used this letter!", announce, 1,"red");
            return;
        }
        if (word.contains(str)) {
            int index = 0;
            for(int i=0; i<word.length(); i++) {
                char c = word.charAt(i);
                if (String.valueOf(c).equals(str)) {
                    setLetter(index, Character.toString(c));
                    current_letter++;
                }
                index++;
            }
            if (current_letter == letter_size) {
                setText("You win!", announce_big, "green");
                img.setImage(image8);
            }
        }
        else {
            addUsedLetter();
            setImage();
        }
        input.clear();
        input.requestFocus();
    }

    public void setLetter(int index,String str){
        switch (index) {
            case 0 -> tf1.setText(str);
            case 1 -> tf2.setText(str);
            case 2 -> tf3.setText(str);
            case 3 -> tf4.setText(str);
            case 4 -> tf5.setText(str);
            case 5 -> tf6.setText(str);
            case 6 -> tf7.setText(str);
            case 7 -> tf8.setText(str);
            case 8 -> tf9.setText(str);
            case 9 -> tf10.setText(str);
        }
    }

    public void setImage(){
        life--;
        if (life < 1) {
            setText("You lose!", announce_big, "red");
            setText("The word is: " + word, hint, "red");
            return;
        }
        lives.setText("Lives: " + life);
        switch (life) {
            case 5 -> img.setImage(image2);
            case 4 -> img.setImage(image3);
            case 3 -> img.setImage(image4);
            case 2 -> img.setImage(image5);
            case 1 -> img.setImage(image6);
            case 0 -> {
                img.setImage(image7);
                lives.setTextFill(Color.rgb(255, 89, 89));
                setText("You lose!", announce_big, "red");
                setText("The word is: " + word, hint, "red");
            }
        }
    }

    private void clearTextFields() {
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
        tf6.setText("");
        tf7.setText("");
        tf8.setText("");
        tf9.setText("");
        tf10.setText("");
        tf1.setVisible(true);
        tf2.setVisible(true);
        tf3.setVisible(true);
        tf4.setVisible(true);
        tf5.setVisible(true);
        tf6.setVisible(true);
        tf7.setVisible(true);
        tf8.setVisible(true);
        tf9.setVisible(true);
        tf10.setVisible(true);
        input.clear();
    }

    public void setInvisible(){
        //hint.setText(hint_str);
        switch (letter_size) {
            case 9 -> tf10.setVisible(false);
            case 8 -> {
                tf9.setVisible(false);
                tf10.setVisible(false);
            }
            case 7 -> {
                tf8.setVisible(false);
                tf9.setVisible(false);
                tf10.setVisible(false);
            }
            case 6 -> {
                tf7.setVisible(false);
                tf8.setVisible(false);
                tf9.setVisible(false);
                tf10.setVisible(false);
            }
            case 5 -> {
                tf6.setVisible(false);
                tf7.setVisible(false);
                tf8.setVisible(false);
                tf9.setVisible(false);
                tf10.setVisible(false);
            }
            case 4 -> {
                tf5.setVisible(false);
                tf6.setVisible(false);
                tf7.setVisible(false);
                tf8.setVisible(false);
                tf9.setVisible(false);
                tf10.setVisible(false);
            }
            case 3 -> {
                tf4.setVisible(false);
                tf5.setVisible(false);
                tf6.setVisible(false);
                tf7.setVisible(false);
                tf8.setVisible(false);
                tf9.setVisible(false);
                tf10.setVisible(false);
            }
        }
    }
}
