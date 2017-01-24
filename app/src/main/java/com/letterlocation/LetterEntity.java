package com.letterlocation;

public class LetterEntity {

    private String mContent;
    private String mLetter;
    private String mPinyin;

    public String getContent() {
        return mContent;
    }
    public void setContent(String content) {
        this.mContent = content;
    }
    public String getLetter() {
        return mLetter;
    }
    public void setLetter(String letter) {
        this.mLetter = letter;
    }
    public String getPinyin() {
        return mPinyin;
    }
    public void setPinyin(String pinyin) {
        this.mPinyin = pinyin;
    }
}
