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
import java.util.Objects;
import java.util.ResourceBundle;


public class QuizScreenController extends GameScreenController implements Initializable {
    protected static int numberOfQuestions;
    protected static ArrayList<Question> questionList = new ArrayList<>();
    protected static ArrayList<Question> questions = new ArrayList<>();
    protected static String quizDataPath = "src/main/resources/org/graphic/data/quiz-data.txt";
    private static final ArrayList<String> choices = new ArrayList<>();
    private static int currentQuestion = -1;
    private static String choice;
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
    private final String[] choiceBoxItem = {"5", "10", "15", "20"};

    public void startGame() {
        typeOfData = "Quiz";
    }

    public void exit() throws Exception {
        loadScreen("game", exit);
    }

    public void generateQuiz() throws InterruptedException {
        if (Objects.equals(menu.getValue(), "")) return;
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
            question.setText("Question " + (currentQuestion + 1) + ": " + questions.get(currentQuestion).getQuestion());
            optionA.setText(questions.get(currentQuestion).getOptionA());
            optionB.setText(questions.get(currentQuestion).getOptionB());
            optionC.setText(questions.get(currentQuestion).getOptionC());
            optionD.setText(questions.get(currentQuestion).getOptionD());
        }
        if (currentQuestion == numberOfQuestions - 1) next.setText("Finish");
        if (choices.isEmpty()) {
            choices.add(choice);
        } else {
            if (choices.get(currentQuestion - 1) == null) {
                choices.add(choice);
            } else {
                choices.set(currentQuestion - 1, choice);
            }
        }
        choices.forEach(x -> System.out.print(x + " "));
        System.out.println();
    }

    public void prevQuestion() {
        next.setText("Next");
        if (currentQuestion > 0) {
            currentQuestion--;
            System.out.println(currentQuestion);
            question.setText("Question " + (currentQuestion + 1) + ": " + questions.get(currentQuestion).getQuestion());
            optionA.setText(questions.get(currentQuestion).getOptionA());
            optionB.setText(questions.get(currentQuestion).getOptionB());
            optionC.setText(questions.get(currentQuestion).getOptionC());
            optionD.setText(questions.get(currentQuestion).getOptionD());
        }
        if (!choices.isEmpty()) {
            if (choices.get(currentQuestion) == null) choices.add(choice);
            else choices.set(currentQuestion, choice);
        } else {
            deleteChoiceEffect();
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

    public void getChoice() {
        optionA.setOnMouseClicked(event -> {
            optionA.setStyle("-fx-background-color: #FFFFFF");
            optionB.setStyle("-fx-background-color: #FFFFFF");
            optionC.setStyle("-fx-background-color: #FFFFFF");
            optionD.setStyle("-fx-background-color: #FFFFFF");
            choice = "A";
            optionA.setStyle("-fx-background-color: #50ABC7");
        });
        optionB.setOnMouseClicked(event -> {
            optionA.setStyle("-fx-background-color: #FFFFFF");
            optionB.setStyle("-fx-background-color: #FFFFFF");
            optionC.setStyle("-fx-background-color: #FFFFFF");
            optionD.setStyle("-fx-background-color: #FFFFFF");
            choice = "B";
            optionB.setStyle("-fx-background-color: #50ABC7");
        });
        optionC.setOnMouseClicked(event -> {
            optionA.setStyle("-fx-background-color: #FFFFFF");
            optionB.setStyle("-fx-background-color: #FFFFFF");
            optionC.setStyle("-fx-background-color: #FFFFFF");
            optionD.setStyle("-fx-background-color: #FFFFFF");
            choice = "C";
            optionC.setStyle("-fx-background-color: #50ABC7");
        });
        optionD.setOnMouseClicked(event -> {
            optionA.setStyle("-fx-background-color: #FFFFFF");
            optionB.setStyle("-fx-background-color: #FFFFFF");
            optionC.setStyle("-fx-background-color: #FFFFFF");
            optionD.setStyle("-fx-background-color: #FFFFFF");
            choice = "D";
            optionD.setStyle("-fx-background-color: #50ABC7");
        });
    }

    public void deleteChoiceEffect() {
        optionA.setStyle("none");
        optionB.setStyle("none");
        optionC.setStyle("none");
        optionD.setStyle("none");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu.getItems().addAll(choiceBoxItem);
        menu.setValue(choiceBoxItem[1]);
        menu.setOnAction(this::getNumberOfQuestions);
    }
}
