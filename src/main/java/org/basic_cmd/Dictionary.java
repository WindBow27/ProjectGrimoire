package org.basic_cmd;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private List<Word> words;

    //Constructor
    public Dictionary() {
        words = new ArrayList<>();
    }

    //Add word
    public void addWord(Word word) {
        words.add(word);
    }

    //Get all words
    public List<Word> getWords() {
        return words;
    }

    //Get word by index
    public Word getWord(int index) {
        return words.get(index);
    }

    //Get word by target
    public Word getWord(String target) {
        for (Word word : words) {
            if (word.getWordTarget().equals(target)) {
                return word;
            }
        }
        return null;
    }
}
