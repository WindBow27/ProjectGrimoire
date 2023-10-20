package org.graphic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class LoadMatchingDataThread extends MatchingGameController implements Runnable {
    @Override
    public void run() {
        getDataFromFile();
        loadDataToCards();
    }

    public void getDataFromFile() {
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
                        meaning = line.substring(i+1);
                        break;
                    }
                }
                words.add(new Word(word, meaning));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    public void loadDataToCards() {
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        cards.add(card5);
        cards.add(card6);
        cards.add(card7);
        cards.add(card8);
        cards.add(card9);
        cards.add(card10);
        cards.add(card11);
        cards.add(card12);
        cards.add(card13);
        cards.add(card14);
        chooseRandomWords();
        distributeData();
    }

    public void chooseRandomWords() {
        Random random = new Random();
        int curNumOfWord = 0;
        while (curNumOfWord != numberOfWord){
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
        while (curNumOfCard != numberOfCard - 1){
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
                }
            }
        }
    }
}
