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

/**
 * The Problem object holds the information about a Problem, including its records.
 *
 * @author Noah Burghardt
 * @see ProblemList
 * @see Record
 * @version 0.0.1
 **/
class Problem {

    private ArrayList<Record> records;
    private String title;
    private String description;


    /**
     * Generates new problem object with given parameters.
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
        }
    }

    // return description of problem
    public String getDescription() {
        return("");
    }

    // update description
    public void setDescription(String newDesc) {

    }

    // add comment to list of comments for problem
    public void addComment(String comment) {

    }

    // return list of comments
    public ArrayList<String> getComments() {
        return(new ArrayList<>());
    }

    // return records for this problem
    public ArrayList<Record> getRecords() {
        return(new ArrayList<>());
    }

    // add a new record to this problems record list
    public void addRecord(Record record) {
    }

    // return a list of all the photos of all the associated records
    public ArrayList<Photo> getPhotos() {
        return(new ArrayList<>());
    }

    // remove record from this problems record list
    public void removeRecord(Record record) {
    }
}
