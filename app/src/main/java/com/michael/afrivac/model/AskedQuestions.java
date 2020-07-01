package com.michael.afrivac.Model;

public class AskedQuestions {
    private String title;
    private String description;

    public AskedQuestions(){

    }

    public AskedQuestions(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
