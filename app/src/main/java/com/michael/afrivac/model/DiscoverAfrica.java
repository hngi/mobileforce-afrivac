package com.michael.afrivac.model;

public class DiscoverAfrica {
    private String destination, image,name;

    public DiscoverAfrica(String destination, String image, String name) {
        this.destination = destination;
        this.image = image;
        this.name = name;
    }
    public DiscoverAfrica() {
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }
}
