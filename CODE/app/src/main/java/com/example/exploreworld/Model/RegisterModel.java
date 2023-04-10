package com.example.exploreworld.Model;

public class RegisterModel {
    public String fullname;
    public String username,phone;

    public RegisterModel() {
    }

    public RegisterModel(String fullname, String username, String phone) {
        this.fullname = fullname;
        this.username = username;
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
