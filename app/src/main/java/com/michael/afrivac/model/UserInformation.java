package com.michael.afrivac.model;

public class UserInformation {

    public String name;
    public String phoneno;

    public UserInformation(){
    }

    public UserInformation(String name, String phoneno){
        this.name = name;
        this.phoneno = phoneno;
    }
    public String getUserName() {
        return name;
    }

    public String getUserPhoneno() {
        return phoneno;
    }
}