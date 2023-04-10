package com.example.exploreworld.Model;

public class Admindata {

        public String adminname;
        public String username;
        public String phone;

    public Admindata() {
    }

    public Admindata(String adminname, String username, String phone) {
        this.adminname = adminname;
        this.username = username;
        this.phone = phone;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
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
