package com.michael.afrivac;

import android.widget.RatingBar;

public class ReviewDetails {
    private float rating;
    private String describe;
    private String tell;

    public ReviewDetails() {

    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
