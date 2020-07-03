package com.michael.afrivac.ui.home;

public class Popular {
    private String country;
    private String name;
    private String description;
    private String image;
    private boolean isFavorite;
    private double rating_number;
    private int review_number;

    public Popular() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public double getRating_number() {
        return rating_number;
    }

    public void setRating_number(double rating_number) {
        this.rating_number = rating_number;
    }

    public int getReview_number() {
        return review_number;
    }

    public void setReview_number(int review_number) {
        this.review_number = review_number;
    }

    public Popular(String country, String name, String description, String image, boolean isFavorite, double rating_number, int review_number) {
        this.country = country;
        this.name = name;
        this.description = description;
        this.image = image;
        this.isFavorite = isFavorite;
        this.rating_number = rating_number;
        this.review_number = review_number;


    }
}
