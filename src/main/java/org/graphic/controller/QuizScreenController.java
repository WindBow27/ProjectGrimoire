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
    private String[] choiceBoxItem = {"5", "10", "15", "20"};
    protected static int numberOfQuestions;
    protected static ArrayList<Question> questionList = new ArrayList<>();
    protected static ArrayList<Question> questions = new ArrayList<>();
    private static int currentQuestion = -1;
    protected static String quizDataPath = "src/main/resources/org/graphic/data/quiz-data.txt";

    public void startGame() throws InterruptedException {
        typeOfData = "Quiz";
    }

    public void generateQuiz() throws InterruptedException {
        LoadDataThread loadThread = new LoadDataThread();
        Thread load = new Thread(loadThread);
        load.start();
        load.join();
        displayQuiz();
    }

    public void nextQuestion() {
        if (currentQuestion <= numberOfQuestions) {
            if (currentQuestion + 1 < numberOfQuestions) currentQuestion++;
            System.out.println(currentQuestion);
            question.setText("Question " + (currentQuestion + 1) + ": " +questions.get(currentQuestion).getQuestion());
            optionA.setText(questions.get(currentQuestion).getOptionA());
            optionB.setText(questions.get(currentQuestion).getOptionB());
            optionC.setText(questions.get(currentQuestion).getOptionC());
            optionD.setText(questions.get(currentQuestion).getOptionD());
        }
    }

    public void prevQuestion() {
        if (currentQuestion > 0) {
            currentQuestion--;
            System.out.println(currentQuestion);
            question.setText("Question " + (currentQuestion + 1) + ": " +questions.get(currentQuestion).getQuestion());
            optionA.setText(questions.get(currentQuestion).getOptionA());
            optionB.setText(questions.get(currentQuestion).getOptionB());
            optionC.setText(questions.get(currentQuestion).getOptionC());
            optionD.setText(questions.get(currentQuestion).getOptionD());
        }
    }

    public void displayQuiz() {
        question.setVisible(true);
        optionA.setVisible(true);
        optionB.setVisible(true);
        optionC.setVisible(true);
        optionD.setVisible(true);
        next.setVisible(true);
        prev.setVisible(true);
        nextQuestion();
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

    public void handleAction(ActionEvent event) {

    }
}
