/*
 * Problem
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * The Problem object holds the information about a Problem, including its records.
 *
 * @author Noah Burghardt
 * @see Record
 * @version 0.0.1
 **/
class Problem implements Serializable {

    private ArrayList<Record> records;
    private String title;
    private String description;
    private ArrayList<String> comments;
    private String problemID = "-1";
    private Date date;



    /**
     * Generates new problem object with given parameters. Comments is initialized as an empty list.
     * @param title Problem title (<=30 chars)
     * @param description Problem description (<= 300 chars)
     * @throws IllegalArgumentException thrown if title or description are of invalid length
     */
    public Problem(String title, String description) throws IllegalArgumentException  {
        if (title.length() < 1 || title.length() > 30 || description.length() > 300){
            throw new IllegalArgumentException();
        }
        else {
            this.records = new ArrayList<>();
            this.title = title;
            this.description = description;
            this.comments = new ArrayList<>();
        }
    }

    /**
     * @return Problem description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @param description New description. Must be 300 characters or less.
     * @throws IllegalArgumentException if description is of invalid length
     */
    public void setDescription(String description) throws IllegalArgumentException {
        if (description.length() > 300){
            throw new IllegalArgumentException();
        }
        else {
            this.description = description;
        }
    }

    /**
     * @param comment comment to be appended to Problems list of comments.
     *                Different than record comments. Only adds comment if length is >= 1.
     */
    public void addComment(String comment) {
        if (comment.length() > 0){
            this.comments.add(comment);
        }
    }

    /**
     * Returns list of comments (strings). If there are no comments, returns an empty list.
     * @return ArrayList<String> comments
     */
    public ArrayList<String> getComments() {
        return this.comments;
    }

    /** Deletes comment matching the input string. Returns true on successful deletion, false
     * if comment was not found in list.
     * @param comment a string matching the comment to be deleted
     * @return true if comment was found and deleted, false otherwise
     */
    public Boolean deleteComment(String comment) {
        if (this.comments.contains(comment)){
            this.comments.remove(comment);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return List of associated record objects. If there are none, returns an empty list.
     */
    public ArrayList<Record> getRecords() {
        return this.records;
    }

    /**
     * Add the given record to this problems record list.
     * A record should not be associated with multiple problems.
     * @param record Record object to be added.
     */
    public void addRecord(Record record) {
        this.records.add(0,record);
    }

    /**
     * Remove record from this problems record list
     * @param record the record to be removed
     * @return True on successful deletion, False if the record was not found
     */
    public Boolean removeRecord(Record record) {
        if (this.records.contains(record)){
            this.records.remove(record);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Fetches a list comprised of all photos associated with all records associated with problem.
     * Fetches photos from each record by method call every time this method is called,
     * does not keep running list in Problem object.
     * @return ArrayList of all photos of all associated records.
     */
    public ArrayList<Photo> getPhotos() {
        ArrayList<Photo> photos = new ArrayList<>();
        for (Record record : this.records){
            photos.addAll(record.getPhotos());
        }

        return photos;
    }

    /**
     * @return problem title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return Problem ID, can be null.
     */
    public String getProblemID(){
        return this.problemID;
    }

    /**
     * @param problemID Problem ID
     */
    public void setProblemID(String problemID) {
        this.problemID = problemID;
    }

    public Date getDate(){return this.date;}

    public void setDate(Date date){
        this.date = date;
    }
}
