package org.graphic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;
import org.graphic.dictionary.Question;

import java.net.URL;
import java.util.*;


public class QuizScreenController extends GameScreenController implements Initializable {
    protected static int numberOfQuestions = 10;
    protected static ArrayList<Question> questionList = new ArrayList<>();
    protected static ArrayList<Question> questions = new ArrayList<>();
    protected static String quizDataPath = "src/main/resources/org/graphic/data/quiz-data.txt";
    private static final ArrayList<String> choices = new ArrayList<>();
    protected static int currentQuestion = -1;
    private int score = 0;
    private static String choice;
    @FXML
    private Label title;
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
    private WebView answer;
    @FXML
    private ChoiceBox<String> menu;
    @FXML
    private Button next;
    @FXML
    private Button prev;
    @FXML
    private Button exit;
    private boolean review = false;
    private boolean viewResult = false;
    private final String[] choiceBoxItem = {"5", "10", "15", "20"};

    public void startGame() {
        typeOfData = "Quiz";
    }

    public void exit() throws Exception {
        loadScreen("game", exit);
        resetStatus();
    }

    public void generateQuiz() throws InterruptedException {
        if (Objects.equals(menu.getValue(), "")) return;
        resetStatus();
        LoadDataThread loadThread = new LoadDataThread();
        Thread load = new Thread(loadThread);
        load.start();
        load.join();
        displayQuiz();
    }

    public void setWhite() {
        optionA.setStyle("-fx-background-color: #FFFFFF");
        optionB.setStyle("-fx-background-color: #FFFFFF");
        optionC.setStyle("-fx-background-color: #FFFFFF");
        optionD.setStyle("-fx-background-color: #FFFFFF");
    }

    public void setText() {
        optionA.setText(questions.get(currentQuestion).getOptionA());
        optionB.setText(questions.get(currentQuestion).getOptionB());
        optionC.setText(questions.get(currentQuestion).getOptionC());
        optionD.setText(questions.get(currentQuestion).getOptionD());
    }

    public void setTrue(String switcher) {
        switch (switcher) {
            case "A" -> optionA.setStyle("-fx-background-color: #75FF73");
            case "B" -> optionB.setStyle("-fx-background-color: #75FF73");
            case "C" -> optionC.setStyle("-fx-background-color: #75FF73");
            case "D" -> optionD.setStyle("-fx-background-color: #75FF73");
        }
    }

    public void setFalse(String switcher, String part) {
        switch (switcher) {
            case "A" -> {
                if (!part.equals("A")) optionA.setStyle("-fx-background-color: #F2746B");
            }
            case "B" -> {
                if (!part.equals("B")) optionB.setStyle("-fx-background-color: #F2746B");
            }
            case "C" -> {
                if (!part.equals("C")) optionC.setStyle("-fx-background-color: #F2746B");
            }
            case "D" -> {
                if (!part.equals("D")) optionD.setStyle("-fx-background-color: #F2746B");
            }
        }
    }

    public void nextQuestion() {
        if (choices.isEmpty()) {
            if (choice != null) choices.add(choice);
            deleteChoiceEffect();
        }
        else {
            if (viewResult) {
                if (currentQuestion + 1 < choices.size()) {
                    deleteChoiceEffect();
                    String[] parts = questions.get(currentQuestion + 1).getAnswer().split(" ");
                    setTrue(parts[2]);
                    setFalse(choices.get(currentQuestion + 1), parts[2]);
                    answer.setVisible(true);
                    answer.getEngine().setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/org/graphic/css/dictionary.css")).toString());
                    String tmp = questions.get(currentQuestion + 1).getAnswer() + "<br>" +
                            questions.get(currentQuestion + 1).getExplain() + "<br>";
                    answer.getEngine().loadContent(tmp);
                }
            }
            else if (review) {
                if (currentQuestion + 1 < choices.size()) {
                    deleteChoiceEffect();
                    setTrue(choices.get(currentQuestion + 1));
                }
                else {
                    deleteChoiceEffect();
                    if (currentQuestion == choices.size()) {
                        deleteChoiceEffect();
                        if (choice != null) choices.add(choice);
                    }
                    else if (currentQuestion == 0) {
                        deleteChoiceEffect();
                        choices.set(currentQuestion, choice);
                    }
                    else if (choices.get(currentQuestion - 1) == null) {
                        if (choice != null) choices.add(choice);
                        deleteChoiceEffect();
                    } else {
                        if (choice != null) choices.set(currentQuestion, choice);
                    }
                }
            }
            else {
                if (currentQuestion == choices.size()) {
                    deleteChoiceEffect();
                    if (choice != null) choices.add(choice);
                }
                else if (currentQuestion == 0) {
                    deleteChoiceEffect();
                    choices.set(currentQuestion, choice);
                }
                else if (choices.get(currentQuestion - 1) == null) {
                    if (choice != null) choices.add(choice);
                    deleteChoiceEffect();
                } else {
                    if (choice != null) choices.set(currentQuestion, choice);
                }
            }
        }
        System.out.println("Choice size: " + choices.size());
        choices.forEach(x -> System.out.print(x + " "));
        System.out.println();
        if (currentQuestion < numberOfQuestions) {
            if (currentQuestion + 1 < numberOfQuestions) currentQuestion++;
            System.out.println("Current question:" + currentQuestion);
            question.setText("Question " + (currentQuestion + 1) + ": " + questions.get(currentQuestion).getQuestion());
            setText();
        }
        if (next.getText().equals("Finish") && !viewResult) {
            evaluate();
            return;
        }
        if (currentQuestion == numberOfQuestions - 1) next.setText("Finish");
    }

    public void prevQuestion() {
        review = true;
        next.setText("Next");
        if (currentQuestion > 0) {
            if (viewResult) {
                if (currentQuestion - 1 < choices.size()) {
                    deleteChoiceEffect();
                    String[] parts = questions.get(currentQuestion).getAnswer().split(" ");
                    setTrue(parts[2]);
                    setFalse(choices.get(currentQuestion), parts[2]);
                    answer.setVisible(true);
                    answer.getEngine().setUserStyleSheetLocation(Objects.requireNonNull(getClass().getResource("/org/graphic/css/dictionary.css")).toString());
                    String tmp = questions.get(currentQuestion).getAnswer() + "<br>" +
                            questions.get(currentQuestion).getExplain() + "<br>";
                    answer.getEngine().loadContent(tmp);
                }
            }
            else {
                deleteChoiceEffect();
                setTrue(choices.get(currentQuestion - 1));
            }

            currentQuestion--;
            System.out.println("Current question:" + currentQuestion);
            question.setText("Question " + (currentQuestion + 1) + ": " + questions.get(currentQuestion).getQuestion());
            setText();
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
            setWhite();
            choice = "A";
            optionA.setStyle("-fx-background-color: CFE2F3");
        });
        optionB.setOnMouseClicked(event -> {
            setWhite();
            choice = "B";
            optionB.setStyle("-fx-background-color: CFE2F3");
        });
        optionC.setOnMouseClicked(event -> {
            setWhite();
            choice = "C";
            optionC.setStyle("-fx-background-color: CFE2F3");
        });
        optionD.setOnMouseClicked(event -> {
            setWhite();
            choice = "D";
            optionD.setStyle("-fx-background-color: CFE2F3");
        });
    }

    public void deleteChoiceEffect() {
        setWhite();
    }

    public void evaluate() {
        for (int i = 0; i < choices.size(); i++) {
            String[] parts = questions.get(i).getAnswer().split(" ");
            if (choices.get(i).equals(parts[2])) score++;
        }
        String emotion;
        double accuracy = (double) score / numberOfQuestions;
        if (accuracy < 0.3) {
            emotion = " :(";
        }
        else if (accuracy >= 0.3 && accuracy < 0.6) {
            emotion = " -_-";
        }
        else emotion = " :)";
        title.setText(score + "/" + numberOfQuestions + emotion);
        review = false;
        viewResult = true;
        currentQuestion = -1;
        next.setText("Next");
        nextQuestion();
    }

    public void resetStatus() {
        questionList.clear();
        questions.clear();
        currentQuestion = -1;
        choices.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menu.getItems().addAll(choiceBoxItem);
        menu.setValue(choiceBoxItem[1]);
        menu.setOnAction(this::getNumberOfQuestions);
    }
}
