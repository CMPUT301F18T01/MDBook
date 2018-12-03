package com.example.mdbook;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for implementing Geolocation
 * @author Thomas Chan
 */
class GeoLocation implements Serializable {
    private Double Lat;
    private Double Long;
    private String title;

    public GeoLocation(Double Lat, Double Long, String title){
        this.Lat = Lat;
        this.Long = Long;
        this.title = title;
    }

    public Double getLat() {
        return Lat;
    }

    public Double getLong() {
        return Long;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public void setLong(Double aLong) {
        Long = aLong;
    }
}

