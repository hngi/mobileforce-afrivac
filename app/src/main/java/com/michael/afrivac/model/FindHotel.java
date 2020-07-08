package com.michael.afrivac.model;

public class FindHotel {

    private String district, image, name, town, icon1;
    private int viewNumber;
    private boolean mFavorite;
    private double mRating;

    public FindHotel() {
    }

    public FindHotel(String district, String image, String name, String town, String icon1, int viewNumber, boolean mFavorite, double mRating) {
        this.district = district;
        this.image = image;
        this.name = name;
        this.town = town;
        this.icon1 = icon1;
        this.viewNumber = viewNumber;
        this.mFavorite = mFavorite;
        this.mRating = mRating;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHotelName() {
        return name;
    }

    public void setHotelName(String name) {
        this.name = name;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    // icons getter
    public String getIcon1() {
        return icon1;
    }

    // icon setters
    public void setIcon1(String icon1) {
        this.icon1 = icon1;
    }


    //view_number

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean mFavorite) {
        this.mFavorite = mFavorite;
    }

    public String toString() {
        return "Is Favourite " + mFavorite;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double mRating) {
        this.mRating = mRating;
    }
}
