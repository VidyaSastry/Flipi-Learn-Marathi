package com.sreevidya.learnmarathi;


class Word {

    private static final int NO_IMAGE_PROVIDED = -1;

    private String mMaratiWord;
    private String mEnglishWord;
    private int mImageResource  = NO_IMAGE_PROVIDED;;
    private int mAudioResource;

    Word(String maratiWord, String englishWord, int audioResource) {
        this.mMaratiWord = maratiWord;
        this.mEnglishWord = englishWord;
        this.mAudioResource = audioResource;
    }

    Word(String maratiWord, String englishWord, int audioResource, int imageResource) {
        this.mMaratiWord = maratiWord;
        this.mEnglishWord = englishWord;
        this.mImageResource = imageResource;
        this.mAudioResource = audioResource;
    }

    public String getmMaratiWord() {
        return mMaratiWord;
    }

    public String getmEnglishWord() {
        return mEnglishWord;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public int getmAudioResource() {
        return mAudioResource;
    }

    boolean hasWordImage(){
        return mImageResource != NO_IMAGE_PROVIDED;
    }
}
