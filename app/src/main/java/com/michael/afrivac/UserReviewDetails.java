package com.michael.afrivac;

public class UserReviewDetails {

    private String usersName;
    private String date;
    private String title;
    private String review;

    public UserReviewDetails(String usersName, String date, String title, String review) {
        this.usersName = usersName;
        this.date = date;
        this.title = title;
        this.review = review;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
