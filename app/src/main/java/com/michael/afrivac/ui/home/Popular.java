package com.michael.afrivac.ui.home;

public class Popular {
    private String mCountryName;
    private String mTownName;
    private String mDescription;
    private int mImage;

    public Popular(int image, String townName, String countryName, String description){
        mTownName = townName;
        mCountryName = countryName;
        mImage = image;
        mDescription = description;
    }

    public String getmCountryName() {
        return mCountryName;
    }
    public String getmTownName() {
        return mTownName;
    }
    public int getmImage() {
        return mImage;
    }
    public String getmDescription(){
        return mDescription;
    }
}
