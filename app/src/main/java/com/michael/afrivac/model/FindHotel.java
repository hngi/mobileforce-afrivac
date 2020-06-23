package com.michael.afrivac.model;

public class FindHotel {
    private int hotelImage;
    private String nameText1;
    private String nameText2;
    private int icon1;
    private int icon2;
    private int icon3;
    private int icon4;
    private int icon5;
    private boolean isFavorite;
    private double rating;
     private int numberRate;

    public FindHotel(int hotelImage, String nameText1, String nameText2, int icon1, int icon2, int icon3, int icon4, int icon5, boolean isFavorite, double rating, int numberRate) {
        this.hotelImage = hotelImage;
        this.nameText1 = nameText1;
        this.nameText2 = nameText2;
        this.icon1 = icon1;
        this.icon2 = icon2;
        this.icon3 = icon3;
        this.icon4 = icon4;
        this.icon5 = icon5;
        this.isFavorite = isFavorite;
        this.rating = rating;
        this.numberRate = numberRate;
    }


    public int getHotelImage() {
        return hotelImage;
    }

    public String getNameText1() {
        return nameText1;
    }

    public String getNameText2() {
        return nameText2;
    }
    public void setHotelImage(int hotelImage) {
        this.hotelImage = hotelImage;
    }

    public void setNameText1(String nameText1) {
        this.nameText1 = nameText1;
    }

    public void setNameText2(String nameText2) {
        this.nameText2 = nameText2;
    }

    public int getIcon1() {
        return icon1;
    }

    public int getIcon2() {
        return icon2;
    }

    public int getIcon3() {
        return icon3;
    }

    public int getIcon4() {
        return icon4;
    }

    public int getIcon5() {
        return icon5;
    }

    public void setIcon1(int icon1) {
        this.icon1 = icon1;
    }

    public void setIcon2(int icon2) {
        this.icon2 = icon2;
    }

    public void setIcon3(int icon3) {
        this.icon3 = icon3;
    }

    public void setIcon4(int icon4) {
        this.icon4 = icon4;
    }

    public void setIcon5(int icon5) {
        this.icon5 = icon5;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberRate() {
        return numberRate;
    }

    public void setNumberRate(int numberRate) {
        this.numberRate = numberRate;
    }

}
