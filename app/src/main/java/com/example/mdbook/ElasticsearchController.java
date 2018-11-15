package com.example.mdbook;
/*
 * ElasticSearchController
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Sets up the framework for ElasticsearchController but stores all data in cache.
 *
 * @author Noah Burghardt
 * @version 0.0.1
 **/
class ElasticsearchController {

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
     *      "date": Date, though in actual implementation might be different?
     *      "description": String
     *      "geoLocation": GeoLocation, might be different in actual implementation
     *      "bodyLocation": BodyLocation, might be different in actual implementation
     *      "photos": ArrayList of photoIDs (strings)
     *      "comment": String
     * Photos: Photo, might be different in actual implementation, not sure how to store images in
     *         ElasticSearch.
     *
     */
    private HashMap<String, JSONObject> patients = new HashMap<>();
    private HashMap<String, JSONObject> caregivers = new HashMap<>();
    private HashMap<String, JSONObject> problems = new HashMap<>();
    private HashMap<String, JSONObject> records = new HashMap<>();
    private HashMap<String, Photo> photos = new HashMap<>();

    private static ElasticsearchController elasticsearchController = null;

    /**
     * @return Singleton instance of ElasticSearchController
     */
    public static ElasticsearchController getController() {
        if (elasticsearchController == null){
            elasticsearchController = new ElasticsearchController();
        }
        return elasticsearchController;
    }

    /**
     * Adds a new user to either the patients or caregivers table, depending on user type.
     * Only use for new users, i.e. does not save problem+ info.
     * @param user The user object (Patient or Caregiver) containing the new contact info.
     * @throws UserIDNotAvailableException Thrown if userID is already taken.
     */
    public void createUser(User user) throws UserIDNotAvailableException {
        String userID = user.getUserID();
        // ensure userID is unique
        if (this.patients.containsKey(userID) || this.caregivers.containsKey(userID)){
            throw new UserIDNotAvailableException();
        }
        else {
            /* fetch data from user object */
            JSONObject data = new JSONObject();
            try {
                data.put("phone", user.getPhoneNumber());
                data.put("email", user.getEmail());

                /* save data in corresponding table */
                if (user.getClass() == Patient.class) {
                    data.put("problems", new ArrayList<String>());
                    this.patients.put(userID, data);
                } else if (user.getClass() == Caregiver.class) {
                    data.put("patients", new ArrayList<String>());
                    this.caregivers.put(userID, data);
                } else {
                    throw new ClassCastException(
                            "The inputted user must be of the Patient or Caregiver class.");
                }
            } catch (JSONException e){
                throw new RuntimeException(e);
            }
        }
    }

    // Adds a patients userID to a caregivers patient list
    public void addPatient(Caregiver caregiver, Patient patient) throws NoSuchUserException {

    }

    // updates user data, i.e. phone, email, userID
    // Throws error if user doesn't already exist in cloud storage,
    // if that is the case use createUser() to create a basic user, then saveUser to update data.
    //TODO
    public void saveUser(User user) throws NoSuchUserException {

    }

    /**
     * Returns the full user object matching the given ID.
     * Includes full records, problems, etc.
     * @param userID userID of a patient or caregiver.
     * @return The full user object.
     * @throws NoSuchUserException Thrown if userID is not connected to a user.
     */
    public User getUser(String userID) throws NoSuchUserException {
        /* Check if userID corresponds with a patient */
        if (this.patients.containsKey(userID)){
            try {
                /* load basic data into patient */
                JSONObject patientJSON = this.patients.get(userID);
                String phone = patientJSON.getString("phone");
                String email = patientJSON.getString("email");
                ArrayList<String> problemIDs = (ArrayList<String>) patientJSON.get("problems");
                Patient patient = new Patient(userID, phone, email);

                /* load problems */
                for (String problemID : problemIDs){
                    patient.addProblem(this.getProblem(problemID));
                }
                return patient;

            } catch (JSONException e){
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
                throw new RuntimeException("User attributes are corrupt.", e);
            }
        }

        /* Check if userID corresponds with a caregiver */
        else if (this.caregivers.containsKey(userID)){
            try {
                /* load basic data into caregiver */
                JSONObject caregiverJSON = this.caregivers.get(userID);
                String phone = caregiverJSON.getString("phone");
                String email = caregiverJSON.getString("email");
                ArrayList<String> patientIDs = (ArrayList<String>) caregiverJSON.get("patients");
                Caregiver caregiver = new Caregiver(userID, phone, email);
                caregiver.setPatientList(patientIDs);
                return caregiver;

            } catch (JSONException e){
                e.printStackTrace();
                throw new RuntimeException("User attributes are corrupt.", e);
            }
        }

        else {
            throw new NoSuchUserException();
        }
    }

    /**
     * Retrieves only the userID, phone and email of a user. Good for giving a profile overview
     * without having to fetch entire user model.
     * @param userID The ID of the user to fetch,
     * @return A ContactUser containing only the contact information.
     */
    public ContactUser getUserContact(String userID) throws NoSuchUserException{
        JSONObject userJSON = null;
        if (this.caregivers.containsKey(userID)) {
            userJSON = caregivers.get(userID);
        } else if (this.patients.containsKey(userID)){
            userJSON = patients.get(userID);
        }
        else {
            throw new NoSuchUserException();
        }

        try {
            return new ContactUser(userID,
                    userJSON.getString("phone"),
                    userJSON.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException("User attributes are corrupt.", e);
        }
    }

    // returns problem matching given problemID
    // Fully fleshed out problem with records, photos etc
    public Problem getProblem(String problemID) throws InvalidKeyException {
        try {
            if (this.problems.containsKey(problemID)) {
                JSONObject problemJSON = this.problems.get(problemID);
                String title = problemJSON.getString("title");
                String description = problemJSON.getString("description");
                Problem problem = new Problem(title, description);

                /* Add records */
                // TODO

                return problem;
            } else {
                throw new InvalidKeyException("Problem does not exist!");
            }
        } catch (JSONException e){
            throw new RuntimeException(e);
        }
    }


    // should add problem with blank record reference and user reference
    // should also add problem id  to user problem id reference list
    // unique id for problem is autogenerated and returned
    public String addProblem(String userID, Problem problem) {
        return null;
    }

    // should add record with parent problem and parent userid references
    // should also add record id to user record id reference list
    // should also add record id to problem record id reference list
    // unique id for record is autogenerated and returned
    public String addRecord(String problemID, String userID, Record record) {
        return null;
    }


    // returns userID of patient who owns given problem
    public String getPatientFromProblem(String problemID) {
        return null;
    }

    // returns all associated records for the given problem
    public ArrayList<String> getRecords(String problemID) {
        return null;
    }

    // returns userID of patient who owns the given record
    public String getPatientFromRecord(String recordID) {
        return null;
    }

    // returns combined list of records and parent problems of records with the given
    // geolocation
    public ArrayList<String> searchGeoLocation(GeoLocation geolocation) {
        return null;
    }

    // returns combined list of records and parent problems of records containing
    // keyword
    public ArrayList<String> searchKeyword(String keyWord) {
        return null;
    }

    // deletes all data belonging to the userID
    public void deleteUser(String userID) {
    }

}

