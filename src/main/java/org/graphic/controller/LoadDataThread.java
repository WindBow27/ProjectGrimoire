package org.graphic.controller;

import org.graphic.dictionary.Question;
import org.graphic.dictionary.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import static org.graphic.controller.HangmanScreenController.hangmanDataPath;
import static org.graphic.controller.HangmanScreenController.wordList;
import static org.graphic.controller.QuizScreenController.*;

public class LoadDataThread implements Runnable {
    private String dataPath;

    public LoadDataThread() {
        //this.cards = cards;
    }

    @Override
    public void run() {
        System.out.println("Loading data thread is running");
        if (typeOfData.equals("Hangman")) {
            dataPath = hangmanDataPath;
            getHangmanData();
        } else {
            dataPath = quizDataPath;
            getQuizData();
            chooseRandomQuestions();
        }
        System.out.println("Loading data thread finished");
    }

    public void getHangmanData() {
        if (!wordList.isEmpty()) return;
        try {
            Scanner fileScanner = new Scanner(new File(dataPath));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                wordList.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void getQuizData() {
        if (!questionList.isEmpty()) return;
        File file = new File(dataPath);
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String question = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String optionA = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String optionB = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String optionC = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String optionD = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String answer = fileScanner.hasNextLine() ? fileScanner.nextLine() : "";
                String explain = (fileScanner.hasNextLine() ? fileScanner.nextLine() : "") +
                        "<br>" +
                        (fileScanner.hasNextLine() ? fileScanner.nextLine() : "");
                questionList.add(new Question(question, optionA, optionB, optionC, optionD, answer, explain));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void chooseRandomQuestions() {
        Random random = new Random();
        int curNumOfQues = 0;
        while (curNumOfQues != numberOfQuestions) {
            int randomNum = random.nextInt(0, questionList.size());
            if (questionList.get(randomNum) != null) {
                questions.add(questionList.get(randomNum));
                questionList.remove(randomNum);
                curNumOfQues++;
            }
        }
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            System.out.println(question.getOptionA());
            System.out.println(question.getOptionB());
            System.out.println(question.getOptionC());
            System.out.println(question.getOptionD());
            System.out.println(question.getAnswer());
            System.out.println(question.getExplain());
        }
    }
}
