package com.michael.afrivac.model;

import androidx.annotation.DrawableRes;

public class DestinationSuggestion {
    int icon;
    String text;

    public DestinationSuggestion(@DrawableRes int icon, String text) {
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
