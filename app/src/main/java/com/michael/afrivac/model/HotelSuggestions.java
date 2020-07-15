package com.michael.afrivac.model;

import androidx.annotation.DrawableRes;

public class HotelSuggestions {
    int icon;
    String text;

    public HotelSuggestions(@DrawableRes int icon, String text) {
        this.icon = icon;
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }
}
