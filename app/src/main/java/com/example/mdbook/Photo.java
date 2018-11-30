package com.example.mdbook;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

// class to hold photo objects
class Photo implements Parcelable {

    private int photoid;
    private String path;
    ArrayList<Photo> photoList;

    public Photo(){
        if (photoList == null) {
            photoList = new ArrayList<>();
        }
    }

    public Photo(Parcel in){

        photoid = in.readInt();
        path = in.readString();

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(path);
        dest.writeInt(photoid);

    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>(){
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}

