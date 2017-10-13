package com.sreevidya.learnmarathi;


class Word {

    private static final int NO_IMAGE_PROVIDED = -1;

    private String mMaratiWord;
    private String mEnglishWord;
    private int mImageResource  = NO_IMAGE_PROVIDED;
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

    String getmMaratiWord() {
        return mMaratiWord;
    }

    String getmEnglishWord() {
        return mEnglishWord;
    }

    int getmImageResource() {
        return mImageResource;
    }

    int getmAudioResource() {
        return mAudioResource;
    }

    boolean hasWordImage(){
        return mImageResource != NO_IMAGE_PROVIDED;
    }
}
