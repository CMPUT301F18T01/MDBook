package com.example.mdbook;

import java.util.ArrayList;
import java.util.Date;

// class to hold record objects
class Record {

    public Record(String Title, Date Date) {
    }

    // allow record to be generated with description
    public Record(String title, Date date, String description) {
    }

    // add a geographic location to record
    public void addLocation(GeoLocation geoLocation) {

    }

    // returns geographic location of record
    public GeoLocation getLocation() {
        return(new GeoLocation());
    }

    // append photo to records photo list
    public void addPhoto(Photo photo) {

    }

    // returns list of photos associated with record
    public ArrayList<Photo> getPhotos() {
        return(new ArrayList<>());
    }

    // add body location to record
    public void addBodyLocation(BodyLocation bodyLocation) {
    }

    // get location of associated record on body
    public BodyLocation getBodyLocation() {
        return(new BodyLocation("arm"));
    }

    // add a comment to a record
    public void addComment(String comment) {
    }

    // return records comment
    public String getComment() {
        return("");
    }

    // return records title
    public String getTitle() {
        return("");
    }

    // return date record was recorded
    public Date getDate() {
        return(new Date());
    }
}
