package com.example.mdbook;

import org.json.JSONObject;

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
    private HashMap<String, JSONObject> problems;
    private HashMap<String, JSONObject> records;
    private HashMap<String, Photo> photos;

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
}
