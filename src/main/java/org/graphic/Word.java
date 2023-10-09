package org.graphic;

public class Word {

    private String word_explain;
    private String word_target;

    //Constructor
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    //Getter && Setter
    public String getWordTarget() {
        return word_target;
    }

    public void setWordTarget(String word_target) {
        this.word_target = word_target;
    }

    public String getWord_explain() {
        return word_explain;
    }

    public void setWordExplain(String word_explain) {
        this.word_explain = word_explain;
    }
}