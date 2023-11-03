package org.graphic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.graphic.dictionary.Question;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class QuizScreenController extends GameScreenController implements Initializable {
    protected static int numberOfQuestions;
    protected static ArrayList<Question> questionList = new ArrayList<>();
    protected static ArrayList<Question> questions = new ArrayList<>();
    private final String[] choiceBoxItem = {"5", "10", "15", "20"};
    private final String dataPath = "src/main/resources/org/graphic/data/quiz-data.txt";
    @FXML
    private Label question;
    @FXML
    private Label optionA;
    @FXML
    private Label optionB;
    @FXML
    private Label optionC;
    @FXML
    private Label optionD;
    @FXML
    private Label answer;
    @FXML
    private ChoiceBox<String> menu;
    @FXML
    private Button next;
    @FXML
    private Button prev;
    @FXML
    private Button exit;

    public void startGame() throws InterruptedException {
        typeOfData = "Quiz";
        LoadDataThread loadThread = new LoadDataThread();
        Thread load = new Thread(loadThread);
        load.start();
        load.join();
        questionList.forEach(System.out::println);
        //handleAction(event);
    }

    public void exit() throws Exception {
        loadScreen("game", exit);
    }

    public void getNumberOfQuestions(ActionEvent event) {
        numberOfQuestions = Integer.parseInt(menu.getValue());
        System.out.println(numberOfQuestions);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu.getItems().addAll(choiceBoxItem);
        menu.setOnAction(this::getNumberOfQuestions);
    }
}
