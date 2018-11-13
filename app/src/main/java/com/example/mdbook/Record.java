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
class Record {
    
    /**
     * Generates a new Record object with date and title.
     * Description will be initialized as an empty string and date will be initialized as
     * the current time and date.
     * @param Title Title of record, must be 30 characters or less
     */
    //TODO: test this
    public Record(String Title) {

    }

    /**
     * Generates a new Record object with description
     * @param title Title of record. Must be 30 characters or less
     * @param date Date associated with record, should be current date/time by default
     * @param description Description of record,
     *                    including anything that might set it apart from previous records
     */
    public Record(String title, Date date, String description) {
    }

    /**
     * Add a geographic location to a record
     * @param geoLocation geographic location to be associated with record
     */
    public void setLocation(GeoLocation geoLocation) {

    }

    /**
     * Returns geographic location of record
     * @return The geographic location associated with the object.
     *         Returns null if record has no geolocation.
     */
    public GeoLocation getLocation() {
        return(new GeoLocation());
    }

    /**
     * Associate a photo with this record
     * @param photo Photo to be added.
     */
    public void addPhoto(Photo photo) {

    }

    /**
     * Removes photo from record and deletes it from app storage.
     * @param photo The photo to be deleted
     * @return True on successful deletion, false if the photo could not be found.
     */
    //TODO: add this to test cases
    public boolean deletePhoto(Photo photo) {
       return false;
    }

    /**
     * Returns list of photos associated with the record.
     * @return ArrayList\<Photo\> of the records photos,
     *         including an ArrayList of zero items if there are no associated records.
     */
    public ArrayList<Photo> getPhotos() {
        return(new ArrayList<>());
    }

    /**
     * @param bodyLocation The new body location.
     * @see BodyLocation
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
    }

    /**
     * @return Body location of the record
     * @see BodyLocation
     */
    public BodyLocation getBodyLocation() {
        return(new BodyLocation("arm"));
    }

    /**
     * Adds a comment to the record. Records can only have 1 comment.
     * @param comment The new comment.
     */
    public void setComment(String comment) {
    }

    /**
     * @return The comment for the record
     */
    public String getComment() {
        return("");
    }

    // return records title

    /**
     * @return The title of the record.
     */
    public String getTitle() {
        return("");
    }

    /**
     * @param title The new title of the record.
     */
    public void setTitle(String title) {

    }

    /**
     * @return The date the record was recorded on.
     */
    public Date getDate() {
        return(new Date());
    }

    /**
     * @return The description of the record.
     */
    public String getDescription(){
        return null;
    }

    /**
     * @param desc The new description of the record.
     */
    public void setDescription(String desc) {

    }
}
