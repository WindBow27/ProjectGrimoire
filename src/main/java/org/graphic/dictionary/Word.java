package org.graphic.dictionary;

public class Word {

    private String word_explain;
    private String word_target;

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public String getWordTarget() {
        return word_target;
    }

    public void setWordTarget(String word_target) {
        this.word_target = word_target;
    }

    public String getWordExplain() {
        return word_explain;
    }

    public void setWordExplain(String word_explain) {
        this.word_explain = word_explain;
    }

    public void removeSpace(Word word) {
        word_target = word_target.trim();
        word_explain = word_explain.trim();
    }
}