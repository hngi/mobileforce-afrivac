package com.michael.afrivac;

public class Place {
    private String mCountryName;
    private String mTownName;
    private int mImage;

    public Place(String townName, String countryName, int image){
        mTownName = townName;
        mCountryName = countryName;
        mImage = image;
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
}
