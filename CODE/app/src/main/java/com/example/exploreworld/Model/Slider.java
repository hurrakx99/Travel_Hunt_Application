package com.example.exploreworld.Model;

public class Slider {
    public String title,subtitle;
    public String imageview;

    public Slider() {
    }

    public Slider(String title, String subtitle, String imageview) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageview = imageview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageview() {
        return imageview;
    }

    public void setImageview(String imageview) {
        this.imageview = imageview;
    }
}
