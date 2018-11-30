package com.example.mdbook;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Holds data for currently logged in user
 *
 * @author Noah Burghardt
 * @version 0.0.1
 **/
public class DataManager {
    /**
     * PatientID:
     *      "phone": String
     *      "email": String
     *      "problems": ArrayList of problemIDs (ints)
     * CaregiverID:
     *      "phone": String
     *      "email": String
     *      "patients": ArrayList of patient userIDs (strings)
     * ProblemID:
     *      "title": String
     *      "description": String
     *      "comments": ArrayList of comments (strings)
     *      "records": ArrayList of recordIDs (ints)
     * RecordID:
     *      "title": String
     *      "date": Date
     *      "description": String
     *      "geoLocation": GeoLocation
     *      "bodyLocation": BodyLocation
     *      "photos": ArrayList of photoIDs (ints)
     *      "comment": String
     * PhotoID: Photo
     *
     *
     */
    private JSONObject user;
    private HashMap<String, JSONObject> problems;
    private HashMap<String, JSONObject> records;
    private HashMap<String, Photo> photos;
    private ArrayList<String> availableIDs;
    private String availableID = "0"; // An unused id number
    private boolean needsPush = false;


    private static DataManager dataManager = null;

    /**
     * @return Singleton instance of DataManager
     */
    public static DataManager getDataManager() {
        if (dataManager == null){
            dataManager = new DataManager();
        }
        return dataManager;
    }

    private DataManager(){
        this.user = new JSONObject();
        this.problems = new HashMap<>();
        this.records = new HashMap<>();
        this.photos = new HashMap<>();
        this.availableIDs = new ArrayList<>();
    }

    public JSONObject getUser(){
        return user;
    }

    public HashMap<String, JSONObject> getProblems() {
        return problems;
    }

    public HashMap<String, JSONObject> getRecords() {
        return records;
    }

    public HashMap<String, Photo> getPhotos() {
        return photos;
    }

    public void setUser(JSONObject user) {
        this.user = user;
    }

    public void setPhotos(HashMap<String, Photo> photos) {
        this.photos = photos;
    }

    public void setProblems(HashMap<String, JSONObject> problems) {
        this.problems = problems;
    }

    public void setRecords(HashMap<String, JSONObject> records) {
        this.records = records;
    }

    public void setAvailableIDs(ArrayList<String> availableIDs){
        this.availableIDs = availableIDs;
    }

    public ArrayList<String> getAvailableIDs(){
        return availableIDs;
    }

    public void setAvailableID(String id){
        this.availableID = id;
    }

    public String getAvailableID(){
        return this.availableID;
    }

    public void push(){
        elasticsearchController.push();
        localStorageController.push();
    }


    public void pull(){
        if (elasticsearchController.isConnected()) {
            elasticsearchController.pull();
            localStorageController.push();
        }
        else{
            localStorageController.loadData();
        }
    }

    /**
     * If there are unused IDs preceding the currently available one, return and remove one of
     * those. Otherwise return fresh ID and increment counter.
     * @return
     */
    public String generateID(){
        if (availableIDs.size() == 0){
            String oldid = availableID;
            availableID = Integer.toString(Integer.getInteger(availableID) + 1) ;
            return oldid;
        }
        else {
            String id = availableIDs.get(0);
            availableIDs.remove(0);
            return id;
        }
    }

    /**
     * Adds id number to list of available IDs. This indicates that the corresponding item has
     * been deleted.
     * @param availableID ID number to be added to queue of available IDs. Any information here can
     *                    be eventually overwritten but is not guaranteed to happen immediately or
     *                    at all.
     */
    public void addAvailableID(String availableID){
        this.availableIDs.add(availableID);
    }

    public void setNeedsPush(boolean needsPush) {
        this.needsPush = needsPush;
    }

    public boolean needsPush() {
        return needsPush;
    }
}
