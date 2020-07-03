package com.michael.afrivac.Adapter;

public class PopularPlace {
    private String country, destination, description, image;
    private boolean isFavorite;
    private double rating;
    private int engagement;

    public PopularPlace() {
    }

    public PopularPlace(String country, String destination, String description, String image, boolean isFavorite, double rating, int engagement) {
        this.country = country;
        this.destination = destination;
        this.description = description;
        this.image = image;
        this.isFavorite = isFavorite;
        this.rating = rating;
        this.engagement = engagement;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setEngagement(int engagement) {
        this.engagement = engagement;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getCountry() {
        return country;
    }

    public String getDestination() {
        return destination;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public double getRating() {
        return rating;
    }

    public int getEngagement() {
        return engagement;
    }

    public String toString() {
        return "Is Favourite " + isFavorite;
    }
}