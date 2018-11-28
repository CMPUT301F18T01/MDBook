package com.example.mdbook;

import android.graphics.Bitmap;

// class to hold photo objects
class Photo{

    private int photoid;
    public Bitmap image;

    public Photo(Bitmap image){
        this.image = image;
    }

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }
}

