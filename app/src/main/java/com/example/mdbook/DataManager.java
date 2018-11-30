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
    private HashMap<String, JSONObject> patients;
    private HashMap<String, JSONObject> caregivers;
    private HashMap<Integer, JSONObject> problems;
    private HashMap<Integer, JSONObject> records;
    private HashMap<Integer, Photo> photos;
    private ArrayList<Integer> availableIDs;
    private int availableID = 0; // An unused id number
    private ElasticsearchController elasticsearchController;
    private LocalStorageController localStorageController;


    private static DataManager dataManager = null;

    /**
     * @return Singleton instance of DataManager
     */
    public static DataManager getDataManager() {
        if (dataManager == null){
            dataManager = new DataManager();
            dataManager.elasticsearchController = ElasticsearchController.getController();
            dataManager.localStorageController = LocalStorageController.getController();
        }
        return dataManager;
    }

    private DataManager(){
        this.patients = new HashMap<>();
        this.caregivers = new HashMap<>();
        this.problems = new HashMap<>();
        this.records = new HashMap<>();
        this.photos = new HashMap<>();
        this.availableIDs = new ArrayList<>();
    }

    public HashMap<String, JSONObject> getCaregivers() {
        return caregivers;
    }

    public HashMap<String, JSONObject> getPatients() {
        return patients;
    }

    public HashMap<Integer, JSONObject> getProblems() {
        return problems;
    }

    public HashMap<Integer, JSONObject> getRecords() {
        return records;
    }

    public HashMap<Integer, Photo> getPhotos() {
        return photos;
    }

    public void setCaregivers(HashMap<String, JSONObject> caregivers) {
        this.caregivers = caregivers;
    }

    public void setPatients(HashMap<String, JSONObject> patients) {
        this.patients = patients;
    }

    public void setPhotos(HashMap<Integer, Photo> photos) {
        this.photos = photos;
    }

    public void setProblems(HashMap<Integer, JSONObject> problems) {
        this.problems = problems;
    }

    public void setRecords(HashMap<Integer, JSONObject> records) {
        this.records = records;
    }

    public void setAvailableIDs(ArrayList<Integer> availableIDs){
        this.availableIDs = availableIDs;
    }

    public ArrayList<Integer> getAvailableIDs(){
        return availableIDs;
    }

    public void setAvailableID(int id){
        this.availableID = id;
    }

    public int getAvaialbleID(){
        return this.availableID;
    }

    public void push(){
//        localStorageController.push();
//        elasticsearchController.push();
    }

    public void pull(){
//        elasticsearchController.pull();
//        localStorageController.loadData();
    }

    /**
     * If there are unused IDs preceding the currently available one, return and remove one of
     * those. Otherwise return fresh ID and increment counter.
     * @return
     */
    public int generateID(){
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
