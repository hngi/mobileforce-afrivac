package com.michael.afrivac.model;

public class Wallet {

    String cardnumber;
    String validuntil;
    long cvv;
    String cardname;

    public Wallet() {
    }

    public Wallet(String cardnumber, String validuntil, long cvv, String cardname) {
        this.cardnumber = cardnumber;
        this.validuntil = validuntil;
        this.cvv = cvv;
        this.cardname = cardname;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getValiduntil() {
        return validuntil;
    }

    public void setValiduntil(String validuntil) {
        this.validuntil = validuntil;
    }

    public long getCvv() {
        return cvv;
    }

    public void setCvv(long cvv) {
        this.cvv = cvv;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "cardnumber=" + cardnumber +
                ", validuntil='" + validuntil + '\'' +
                ", cvv=" + cvv +
                ", cardname='" + cardname + '\'' +
                '}';
    }
}
