package com.example.mdbook;

import android.graphics.Bitmap;
import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

// class to hold photo objects

class Photo implements Serializable {

    private String photoid = "-1";

    private  String filepath;

    public Photo(String path) {
        this.filepath = path;

        }
        public String getPhotoid() {
                return photoid;
            }

            public void setPhotoid(String photoid) {

                this.photoid = photoid;
            }

            public String getFilepath() {

                return filepath;
            }

            public void setFilepath(String path) {

                this.filepath = path;
            }


        }

