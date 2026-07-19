package com.michael.afrivac.ui.memories;

public class MemoriesList {

    String Title;
    String Description;

    public MemoriesList(String userTitle, String userDescription) {

        Title = userTitle;
        Description = userDescription;


    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() { return Description; }
}
