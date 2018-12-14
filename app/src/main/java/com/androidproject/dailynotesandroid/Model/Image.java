package com.androidproject.dailynotesandroid.Model;

public class Image {

    private int imageId;
    private String imageLocation;

    public Image() {
    }

    public Image(int imageId, String imageLocation) {
        this.imageId = imageId;
        this.imageLocation = imageLocation;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
