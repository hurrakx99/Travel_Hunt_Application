package com.example.exploreworld.Model;

public class ScreenItem {
    public String IntroTitle, IntroDescription;
    public String ScreenImg;

    public ScreenItem() {
    }

    public ScreenItem(String introTitle, String introDescription, String screenImg) {
        IntroTitle = introTitle;
        IntroDescription = introDescription;
        ScreenImg = screenImg;
    }

    public String getIntroTitle() {
        return IntroTitle;
    }

    public void setIntroTitle(String introTitle) {
        IntroTitle = introTitle;
    }

    public String getIntroDescription() {
        return IntroDescription;
    }

    public void setIntroDescription(String introDescription) {
        IntroDescription = introDescription;
    }

    public String getScreenImg() {
        return ScreenImg;
    }

    public void setScreenImg(String screenImg) {
        ScreenImg = screenImg;
    }
}
