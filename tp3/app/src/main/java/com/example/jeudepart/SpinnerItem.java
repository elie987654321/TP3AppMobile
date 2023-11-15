package com.example.jeudepart;

public class SpinnerItem {
    private String text;
    private int imageResId;

    public SpinnerItem(String text, int imageResId) {
        this.text = text;
        this.imageResId = imageResId;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }
}
