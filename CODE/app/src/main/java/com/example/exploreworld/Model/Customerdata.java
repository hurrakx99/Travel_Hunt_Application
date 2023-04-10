package com.example.exploreworld.Model;

public class Customerdata {
    public String fullname;
    public String username;
    public String phone;
    public String imageuri;

    public Customerdata() {
    }

    public Customerdata(String fullname, String username, String phone, String imageuri) {
        this.fullname = fullname;
        this.username = username;
        this.phone = phone;
        this.imageuri = imageuri;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}