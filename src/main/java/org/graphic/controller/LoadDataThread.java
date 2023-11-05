package org.graphic.controller;

import org.graphic.dictionary.Question;
import org.graphic.dictionary.Word;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

import static org.graphic.controller.QuizScreenController.*;

public class LoadDataThread extends MatchingGameController implements Runnable {
    protected final int numberOfWord = 7;
    protected final int numberOfCard = 14;
    private String dataPath;

    public LoadDataThread() {
        //this.cards = cards;
    }

    @Override
    public void run() {
        System.out.println("Loading data thread is running");
        if (typeOfData.equals("Matching")) {
            dataPath = matchingDataPath;
            getMatchingData();
            chooseRandomWords();
            distributeData();
        } else {
            dataPath = quizDataPath;
            getQuizData();
            chooseRandomQuestions();
        }
        System.out.println("Loading data thread finished");
    }

    public void getMatchingData() {
        if (!words.isEmpty()) return;
        File file = new File(dataPath);
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String word = "", meaning = "";
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ' ') {
                        word = line.substring(0, i);
                        meaning = line.substring(i + 1);
                        break;
                    }
                }
                words.add(new Word(word, meaning));
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
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(questions.get(i).getQuestion());
            System.out.println(questions.get(i).getOptionA());
            System.out.println(questions.get(i).getOptionB());
            System.out.println(questions.get(i).getOptionC());
            System.out.println(questions.get(i).getOptionD());
            System.out.println(questions.get(i).getAnswer());
            System.out.println(questions.get(i).getExplain());
        }
    }

    public void chooseRandomWords() {
        Random random = new Random();
        int curNumOfWord = 0;
        while (curNumOfWord != numberOfWord) {
            int randomNum = random.nextInt(0, words.size());
            if (words.get(randomNum) != null) {
                targets.add(words.get(randomNum).getWordTarget());
                definitions.add(words.get(randomNum).getWordExplain());
                words.remove(randomNum);
                curNumOfWord++;
            }
        }
        for (String x : targets) System.out.println(x);
        for (String x : definitions) System.out.println(x);
    }

    public void distributeData() {
        Random random = new Random();
        int curNumOfCard = 0;
        while (curNumOfCard != numberOfCard) {
            while (!targets.isEmpty()) {
                int ranNum = random.nextInt(0, numberOfCard);
                System.out.println(ranNum);
                if (cards.get(ranNum) != null) {
                    if (cards.get(ranNum).getText().equals("")) {
                        cards.get(ranNum).setText(targets.get(curNumOfCard % targets.size()));
                        System.out.println(targets.get(curNumOfCard % targets.size()));
                        targets.remove(curNumOfCard % targets.size());
                        curNumOfCard++;
                    }
                } else {
                    System.out.println("Card is null");
                    return;
                }
            }
            while (!definitions.isEmpty()) {
                int ranNum = random.nextInt(0, numberOfCard);
                System.out.println(ranNum);
                if (cards.get(ranNum) != null) {
                    if (cards.get(ranNum).getText().equals("")) {
                        cards.get(ranNum).setText(definitions.get(curNumOfCard % definitions.size()));
                        System.out.println(definitions.get(curNumOfCard % definitions.size()));
                        definitions.remove(curNumOfCard % definitions.size());
                        curNumOfCard++;
                    }
                } else {
                    System.out.println(cards.get(ranNum));
                    System.out.println("Card is null");
                    return;
                }
            }
        }
    }
}
