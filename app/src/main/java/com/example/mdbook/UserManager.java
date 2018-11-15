/*
 * UserManager
 *
 * Version 1.0.0
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Provides a singleton interface for managing users, user data and user login / logout.
 * Also provides interface for creating and deleting users. Activities should use userManager
 * instead of going directly through the database controller or the Patient / Caregiver classes.
 * On login, the logged in user object is to be interacted with through the UserController.
 * Relies on DataManager for data lookup and management.
 *
 * Based off the StudentListManager in the student-picker app by Abram Hindle
 * https://github.com/abramhindle/student-picker
 *
 * @author Noah Burghardt
 * @see User
 * @see UserController
 * @see ElasticsearchController
 * @version 1.0.0
 **/
public class UserManager {

    static private UserManager userManager = null;
    private DataManager dataManager = null;


    /**
     * Initialize singleton instance.
     */
    public static void initManager() {
        if (userManager == null){
            userManager = new UserManager();
        }
    }

    /**
     * @return Singleton instance of self.
     * @throws IllegalStateException Thrown if initManager() has not already been called.
     */
    public static UserManager getManager() {
        if(userManager == null){
            throw new IllegalStateException("UserManager has not been initialized!");
        }
        return userManager;
    }

    /**
     * Constructor, called only by initManager() method.
     */
    private UserManager(){
        this.dataManager = DataManager.getDataManager();
    }

    /**
     * Create new patient profile and save.
     * @param userID The unique ID of the new patient. Must be unique.
     * @param userPhone The phone number of the new patient.
     * @param userEmail The email of the new patient.
     * @throws UserIDNotAvailableException Thrown if the userID is not unique
     */
    public void createPatient(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException {
        /* Fetch fresh copy of patient list */
        HashMap patients = dataManager.getPatients();

        /* Ensure userID is unique */
        if (patients.containsKey(userID) || dataManager.getCaregivers().containsKey(userID)){
            throw new UserIDNotAvailableException();
        }
        else {
            /* store data */
            JSONObject data = new JSONObject();
            try {
                data.put("phone", userPhone);
                data.put("email", userEmail);
                data.put("problems", new ArrayList<String>());

                /* save data in patients table */
                patients.put(userID, data);

            } catch (JSONException e){
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Create new caregiver profile and save.
     * @param userID The unique ID of the new caregiver. Must be unique.
     * @param userPhone The phone number of the new caregiver.
     * @param userEmail The email of the new caregiver.
     * @throws UserIDNotAvailableException Thrown if the userID is not unique
     */
    public void createCaregiver(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException {
        /* Fetch fresh copy of patient list */
        HashMap caregivers = dataManager.getCaregivers();

        /* Ensure userID is unique */
        if (caregivers.containsKey(userID) || dataManager.getPatients().containsKey(userID)){
            throw new UserIDNotAvailableException();
        }
        else {
            /* store data */
            JSONObject data = new JSONObject();
            try {
                data.put("phone", userPhone);
                data.put("email", userEmail);
                data.put("patients", new ArrayList<String>());

                /* save data in patients table */
                caregivers.put(userID, data);

            } catch (JSONException e){
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Verify user exists and login credentials are valid.
     * Loads user into UserController on success.
     * @param userid The userID of the user trying to log in.
     * @return Returns true on successful login, false if credentials are invalid,
     * user doesn't exist or there is already someone logged in.
     */
    public boolean login(String userid) {
        UserController userController = UserController.getController();
        if (userController.getUser() != null){
            return false;
        }
        try {
            User user = this.fetchUser(userid);
            userController.loadUser(user);
            return true;
        } catch (NoSuchUserException e){
            return false;
        }
    }

    /**
     * Clears all data out of UserController.
     */
    public void logout() {
        UserController.getController().clearUser();
    }

    /**
     * Builds user object containing only userID, phone and email. Returns said user object.
     * Does not load into UserController. Good for showing an overview of a User.
     * @param userID The ID of the user to fetch.
     * @return A patient or caregiver object with only the contact information stored.
     * @throws NoSuchUserException Thrown if the userID couldn't be found.
     */
    public ContactUser fetchUserContact (String userID) throws NoSuchUserException {
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();
        JSONObject userJSON;

        if (caregivers.containsKey(userID)) {
            userJSON = caregivers.get(userID);
        } else if (patients.containsKey(userID)){
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
            throw new RuntimeException("User attributes are corrupt.", e);
        }
    }

    /**
     * Builds full user object for the given userID from database. Returns said user object.
     * Does not load into UserController
     * @param userID The userID of the user to load.
     * @return A patient or caregiver object built from the userID.
     * @throws NoSuchUserException Thrown if there is no user with the given userID in the database.
     */
    public User fetchUser (String userID) throws NoSuchUserException {
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();

        /* Check if userID corresponds with a patient */
        if (patients.containsKey(userID)){
            try {
                /* load basic data into patient */
                JSONObject patientJSON = patients.get(userID);
                String phone = patientJSON.getString("phone");
                String email = patientJSON.getString("email");
                ArrayList<String> problemIDs = (ArrayList<String>) patientJSON.get("problems");
                Patient patient = new Patient(userID, phone, email);

                /* Get problem method also loads in records, photos, etc */
                for (String problemID : problemIDs){
                    patient.addProblem(getProblem(problemID));
                }

                return patient;

            } catch (JSONException e){
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException("The user data is corrupt", e);
            }
        }

        /* Check if userID corresponds with a caregiver */
        else if (caregivers.containsKey(userID)){
            try {
                /* load basic data into caregiver */
                JSONObject caregiverJSON = caregivers.get(userID);
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
     * Assistant method for retrieving full problem objects
     * @param problemID The problem ID number
     * @return A fully filled out problem object
     */
    private Problem getProblem(String problemID) throws InvalidKeyException {
        HashMap<String, JSONObject> problems = dataManager.getProblems();
        try {
            if (problems.containsKey(problemID)) {
                JSONObject problemJSON = problems.get(problemID);
                String title = problemJSON.getString("title");
                String description = problemJSON.getString("description");
                Problem problem = new Problem(title, description);

                /* Add comments */
                for (String comment : (ArrayList<String>) problemJSON.get("comments")){
                    problem.addComment(comment);
                }

                /* Add records */
                for (String recordID : (ArrayList<String>) problemJSON.get("records")){
                    problem.addRecord(this.getRecord(recordID));
                }

                return problem;
            } else {
                throw new InvalidKeyException("Problem does not exist!");
            }
        } catch (JSONException e){
            throw new RuntimeException("User data is corrupt.", e);
        }
    }

    /**
     * Assistant method for retrieving full record objects
     * @param recordID The problem ID number
     * @return A fully filled out problem object
     */
    private Record getRecord(String recordID) throws InvalidKeyException{
        HashMap<String, JSONObject> records = dataManager.getRecords();
        HashMap<String, Photo> photos = dataManager.getPhotos();
        try {
            if (records.containsKey(recordID)){
                JSONObject recordJSON = records.get(recordID);
                /* Fetch data */
                String title = recordJSON.getString("title");
                Date date = (Date) recordJSON.get("date");
                String description = recordJSON.getString("description");
                GeoLocation geoLocation = (GeoLocation) recordJSON.get("geoLocation");
                BodyLocation bodyLocation = (BodyLocation) recordJSON.get("bodyLocation");
                String comment = recordJSON.getString("comment");

                Record record = new Record(title, date, description);
                record.setGeoLocation(geoLocation);
                record.setBodyLocation(bodyLocation);
                record.setComment(comment);

                /* Add photos */
                for (String photoID : (ArrayList<String>) recordJSON.get("photos")){
                    record.addPhoto(photos.get(photoID));
                }
            }
            else{
                throw new InvalidKeyException("Record does not exist!");
            }
        } catch (JSONException e){
            throw new RuntimeException("User data is corrupt.", e);
        }

    }

    /**
     * Take the data in the given user object, find the entry in the database with a matching userID
     * and update the database.
     * @param user The user object to be synced into the database.
     * @throws NoSuchUserException Thrown if there is no user with a matching userID already in the
     * database. Shouldn't happen if users are created through the UserManager.
     * @throws IllegalArgumentException Thrown if the inputted user is not a (full) patient or
     * caregiver, e.g. a ContactUser. 
     */
    //TODO
    public void saveUser(User user) throws NoSuchUserException, IllegalArgumentException {
        if (user.getClass() != Patient.class && user.getClass() != Caregiver.class){
            throw new IllegalArgumentException("User is not a patient or a Caregiver!");
        }
        else {
            this.esc.saveUser(user);
        }
    }

    /**
     * Assistant method to saveUser method. Saves Problem data.
     * @param problemID
     * @param problem
     */
    //TODO
    private void setProblem(String problemID, Problem problem){

    }

    /**
     * Assistant method to setProblem and saveUser methods. Saves record data.
     * @param recordID
     * @param record
     */
    //TODO
    private void setRecord(String recordID, Record record){

    }

    /**
     * Delete all data belonging to the user associated with the given userID from the database.
     * @param userID The userID of the user to be cleared out.
     */
    //TODO
    public void deleteUser(String userID) {
        this.esc.deleteUser(userID);
    }

}
