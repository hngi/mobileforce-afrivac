package com.michael.afrivac.ui.home;

public class Place {
    private String name;
    private String destination;
    private String  image;


    public Place() {
    }


    public Place(String name, String destination, String image) {
        this.name = name;
        this.destination = destination;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
