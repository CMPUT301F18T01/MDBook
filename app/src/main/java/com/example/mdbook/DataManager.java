package com.example.mdbook;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides global access point for data storage.
 *
 * @author Noah Burghardt
 * @version 0.0.1
 **/
public class DataManager {
    /**
     * Patient:
     *      "phone": String
     *      "email": String
     *      "problems": ArrayList of problemIDs (strings)
     * Caregiver:
     *      "phone": String
     *      "email": String
     *      "patient": ArrayList of patient userIDs (strings)
     * Problem:
     *      "title": String
     *      "description": String
     *      "comments": ArrayList of comments (strings)
     *      "records": ArrayList of recordIDs (strings)
     * Record:
     *      "title": String
     *      "date": Date
     *      "description": String
     *      "geoLocation": GeoLocation
     *      "bodyLocation": BodyLocation
     *      "photos": ArrayList of photoIDs (strings)
     *      "comment": String
     * Photos: Photo
     *
     */
    private HashMap<String, JSONObject> patients;
    private HashMap<String, JSONObject> caregivers;
    private HashMap<Integer, JSONObject> problems;
    private HashMap<Integer, JSONObject> records;
    private HashMap<Integer, Photo> photos;
    private ArrayList<Integer> availableIDs;
    private int availableID = 0;


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
        this.patients = new HashMap<>();
        this.caregivers = new HashMap<>();
        this.problems = new HashMap<>();
        this.records = new HashMap<>();
        this.photos = new HashMap<>();
    }

    public HashMap<String, JSONObject> getCaregivers() {
        return caregivers;
    }

    public HashMap<String, JSONObject> getPatients() {
        return patients;
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

    public void setCaregivers(HashMap<String, JSONObject> caregivers) {
        this.caregivers = caregivers;
    }

    public void setPatients(HashMap<String, JSONObject> patients) {
        this.patients = patients;
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

    /**
     * If there are unused IDs preceding the currently available one, return and remove one of
     * those. Otherwise return fresh ID and increment counter.
     * @return
     */
    public int getAvailableID(){
        if (availableIDs.size() == 0){
            availableID += 1;
            return availableID-1;
        }
        else {
            int id = availableIDs.get(0);
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
    public void addAvailableID(int availableID){
        this.availableIDs.add(availableID);
    }
}
