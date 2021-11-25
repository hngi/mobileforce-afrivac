package com.michael.afrivac.model;

public class PopularPlaces {
    private String country, destination, description, imageUrl;
    private boolean isFavorite;
    private double rating;
    private int engagement;

    public PopularPlaces(String country, String destination, String description, String imageUrl, boolean isFavorite, double rating, int engagement) {
        this.country = country;
        this.destination = destination;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isFavorite = isFavorite;
        this.rating = rating;
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

    public String getImageUrl() {
        return imageUrl;
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
