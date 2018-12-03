/*
 * Record
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * The Record object holds the information about a record, usually held by a Problem.
 *
 * @author Noah Burghardt
 * @see com.example.mdbook.Problem
 * @see com.example.mdbook.GeoLocation
 * @see com.example.mdbook.BodyLocation
 * @see com.example.mdbook.Photo
 * @version 0.0.1
 **/

class Record implements Serializable, Comparable {

    private String title;
    private Date date;
    private String description;
    private GeoLocation geoLocation;
    private ArrayList<BodyLocation> bodyLocations;
    private ArrayList<Photo> photos;
    private String comment;
    private String recordID = "-1";


    /**
     * Generates a new Record object with date and title.
     * Description will be initialized as an empty string and date will be initialized as
     * the current time and date. Photos will be an empty list, comment will be an empty string
     * and all other attributes will be null.
     * @param title Title of record, must be 30 characters or less.
     * @throws IllegalArgumentException if title length is invalid
     */
    //TODO: test this
    public Record(String title) throws IllegalArgumentException {
        if (title.length() < 1 || title.length() > 30){
            throw new IllegalArgumentException();
        }
        else {
            this.title = title;
            this.date = new Date();
            this.description = "";
            this.geoLocation = new GeoLocation();
            this.bodyLocations = new ArrayList<>();
            this.photos = new ArrayList<>();
            this.comment = "";
        }
    }

    /**
     * Generates a new Record object with given title, date and description. Photos will be an empty
     * list, comment will be an empty string and all other attributes will be null.
     * @param title Title of record. Must be 30 characters or less
     * @param date Date associated with record, should be current date/time by default
     * @param description Description of record,
     *                    including anything that might set it apart from previous records
     * @throws IllegalArgumentException if title or description lengths are invalid
     */
    public Record(String title, Date date, String description) throws IllegalArgumentException {
        if (title.length() < 1 || title.length() > 30 || description.length() > 300){
            throw new IllegalArgumentException();
        }
        else {
            this.title = title;
            this.date = date;
            this.description = description;
            this.geoLocation = new GeoLocation();
            this.bodyLocations = new ArrayList<>();
            this.photos = new ArrayList<>();
            this.comment = "";
        }
    }




    /**
     * Add a geographic location to a record
     * @param geoLocation geographic location to be associated with record
     */
    public void setLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * Returns geographic location of record
     * @return The geographic location associated with the object.
     *         Returns null if record has no geolocation.
     */
    public GeoLocation getLocation() {
        return this.geoLocation;
    }

    /**
     * Associate a photo with this record
     * @param photo Photo to be added.
     */
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    /**
     * Removes photo from record and deletes it from app storage.
     * @param photo The photo to be deleted
     * @return True on successful deletion, false if the photo could not be found.
     */
    //TODO: add this to test cases
    public boolean deletePhoto(Photo photo) {
       if(this.photos.contains(photo)){
           this.photos.remove(photo);
           return true;
       }
       else {
           return false;
       }
    }

    /**
     * Returns list of photos associated with the record.
     * @return ArrayList\<Photo\> of the records photos,
     *         including an ArrayList of zero items if there are no associated records.
     */
    public ArrayList<Photo> getPhotos() {
        return(this.photos);
    }

    /**
     * @param bodyLocation The new body location.
     * @see BodyLocation
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
        bodyLocations.add(bodyLocation);
    }

    /**
     * @return Body location of the record, returns null if no body location has been set.
     * @see BodyLocation
     */
    public ArrayList<BodyLocation> getBodyLocation() {
        return this.bodyLocations;
    }


    public void removeBodyLocation(BodyLocation bodylocation){
        if(bodyLocations.contains(bodylocation)){
            bodyLocations.remove(bodylocation);
        }
    }
    /**
     * Sets record comment. Records can only have 1 comment.
     * @param comment The new comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return The comment for the record
     */
    public String getComment() {
        return this.comment;
    }

    /**
     * @return The title of the record. Should never be an empty string.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title The new title of the record.
     * @throws IllegalArgumentException if title is too short or too long
     */
    public void setTitle(String title) {
        if (title.length() < 1 || title.length() > 30){
            throw new IllegalArgumentException();
        }
        else {
            this.title = title;
        }
    }

    /**
     * @return The date the record was recorded on.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * @return The description of the record.
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * @param desc The new description of the record.
     * @throws IllegalArgumentException if description is too long  (>300 chars)
     */
    public void setDescription(String desc) throws IllegalArgumentException {
        if (desc.length() > 300){
            throw new IllegalArgumentException();
        }
        else {
            this.description = desc;
        }
    }

    /**
     * @param geoLocation new geographic location
     */
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * @return Record ID for this record. Will be null if record hasn't already existed in storage.
     */
    public String getRecordID() {
        return recordID;
    }

    /**
     * @param recordID Set recordID, should be generated from database.
     */
    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
