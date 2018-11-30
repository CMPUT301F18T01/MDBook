package com.example.mdbook;

import android.graphics.Bitmap;

// class to hold photo objects
class Photo{

    private int photoid;
    public Bitmap image;
    public String path;

    public Photo(Bitmap image){
        this.image = image;
    }

    public int getPhotoid() {
        return photoid;
    }

    public void setPhotoid(int photoid) {
        this.photoid = photoid;
    }

    public String getPath(){
        return path;
    }

    public void setPath(String path){
        this.path = path;
    }
}

