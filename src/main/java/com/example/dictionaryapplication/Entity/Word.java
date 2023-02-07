package com.example.dictionaryapplication.Entity;

public class Word {
    private String wordTarget;
    private String wordExplain;

    private String phonetic;

    public Word(String wordTarget, String wordExplain, String phonetic) {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.phonetic = phonetic;
    }

    public String getWordTarget() {
        return wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public String getWordExplain() {
        return wordExplain;
    }

    public void setWordExplain(String wordExplain) {
        this.wordExplain = wordExplain;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }
}
