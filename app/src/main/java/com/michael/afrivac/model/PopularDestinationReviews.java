package com.michael.afrivac.model;

public class PopularDestinationReviews {
    private String date;
    private String message_description;
    private String message_title;
    private String name;
    private String profile_image;
    private String star_num;

    public PopularDestinationReviews(String date, String message_description, String message_title, String name, String profile_image, String star_num) {
        this.date = date;
        this.message_description = message_description;
        this.message_title = message_title;
        this.name = name;
        this.profile_image = profile_image;
        this.star_num = star_num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage_description() {
        return message_description;
    }

    public void setMessage_description(String message_description) {
        this.message_description = message_description;
    }

    public String getMessage_title() {
        return message_title;
    }

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getStar_num() {
        return star_num;
    }

    public void setStar_num(String star_num) {
        this.star_num = star_num;
    }
}
