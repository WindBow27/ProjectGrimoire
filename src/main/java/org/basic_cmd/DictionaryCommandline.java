package org.basic_cmd;

import java.util.Scanner;

public class DictionaryCommandline {
    private DictionaryManagement dictionaryManagement;

    public DictionaryCommandline() {
        dictionaryManagement = new DictionaryManagement();
    }

    //Show all words
    public void showALlWords() {
        System.out.println("No\t|English\t\t|Vietnamese");
        for (int i = 0; i < dictionaryManagement.getDictionary().getWords().size(); i++) {
            System.out.println(i + 1
                    + "\t|" + dictionaryManagement.getDictionary().getWord(i).getWordTarget()
                    + "\t\t\t|" + dictionaryManagement.getDictionary().getWord(i).getWord_explain());
        }
    }

    //The basic dictionary
    public void dictionaryBasic() {
        dictionaryManagement.insertFromCommandline();
        showALlWords();
    }

    //Advanced dictionary with buttons and stuffs
    public void dictionaryAdvanced() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            showMenu();
            String action = scanner.nextLine();

            switch (action) {
                case "0": //Exit
                    System.out.println("Goodbye!");
                    return;
                case "1": //Add
                    dictionaryManagement.insertFromCommandline();
                    break;
                case "2": //Remove
                    dictionaryManagement.deleteWord();
                    break;
                case "3": //Update
                    dictionaryManagement.updateWord();
                    break;
                case "4": //Display
                    showALlWords();
                    break;
                case "5": //Lookup
                    dictionaryManagement.dictionaryLookup();
                    break;
                case "6": //Search
                    dictionaryManagement.dictionarySearcher();
                    break;
                case "7": //Game

                    break;
                case "8": //Import from file
                    dictionaryManagement.insertFromFile();
                    break;
                case "9": //Export to file
                    dictionaryManagement.dictionaryExportToFile();
                    break;
                default:
                    System.out.println("Action not supported");
                    break;
            }
        }
    }

    public void showMenu() {
        System.out.println("Welcome to My Application!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Update");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Game");
        System.out.println("[8] Import from file");
        System.out.println("[9] Export to file");
        System.out.print("Your action: ");
    }
}