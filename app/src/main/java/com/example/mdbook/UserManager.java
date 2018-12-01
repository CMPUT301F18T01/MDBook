/*
 * UserManager
 *
 * Version 2.0.0
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import android.accounts.NetworkErrorException;

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
 * Based (partially) off the StudentListManager in the student-picker app by Abram Hindle
 * https://github.com/abramhindle/student-picker
 *
 * @author Noah Burghardt
 * @see User
 * @see UserController
 * @see ElasticsearchController
 * @version 2.0.0
 **/
public class UserManager {

    static private UserManager userManager;
    private DataManager dataManager;
    private ElasticsearchController elasticsearchController;
    private LocalStorageController localStorageController;
    private UserController userController;
    private UserDecomposer decomposer;

    /**
     * Initialize singleton instance.
     */
    public static void initManager() {
        if (userManager == null){
            userManager = new UserManager();
            userManager.dataManager = DataManager.getDataManager();
            userManager.elasticsearchController = ElasticsearchController.getController();
            userManager.userController = UserController.getController();
            userManager.localStorageController = LocalStorageController.getController();
            userManager.decomposer = new UserDecomposer();
        }
    }

    /**
     * Auto initilaizes if not already done
     * @return Singleton instance of self.
     */
    public static UserManager getManager() {
        if(userManager == null){
            initManager();
        }
        return userManager;
    }

    /**
     * Create new patient profile and save.
     * @param userID The unique ID of the new patient. Must be unique.
     * @param userPhone The phone number of the new patient.
     * @param userEmail The email of the new patient.
     * @throws UserIDNotAvailableException Thrown if the userID is not unique
     * @throws IllegalArgumentException Thrown if the userID is less than 8 characters
     * @throws NetworkErrorException Thrown if there were any network issues creating the user
     * (e.g no internet)
     */
    public void createPatient(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException, IllegalArgumentException, NetworkErrorException {

        /* Ensure userID is unique */
        if (elasticsearchController.existsUser(userID)) {
            throw new UserIDNotAvailableException();
        }

        /* Ensure userID is long enough */
        if (userID.length() < 8) {
            throw new IllegalArgumentException();
        } else {
            Patient patient = new Patient(userID, userPhone, userEmail);
            dataManager.saveMe(patient);
            dataManager.addToQueue(patient);
        }
    }

    /**
     * Create new caregiver profile and save.
     * @param userID The unique ID of the new caregiver. Must be unique.
     * @param userPhone The phone number of the new caregiver.
     * @param userEmail The email of the new caregiver.
     * @throws UserIDNotAvailableException Thrown if the userID is not unique
     * @throws IllegalArgumentException Thrown if the userID is less than 8 characters
     * @throws NetworkErrorException Thrown if there were any network issues creating the user
     */
    public void createCaregiver(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException, IllegalArgumentException, NetworkErrorException {

        /* Ensure userID is unique */
        if (elasticsearchController.existsUser(userID)){
            throw new UserIDNotAvailableException();
        }

        /* Ensure userID is long enough */
        if (userID.length() < 8){
            throw new IllegalArgumentException();
        }

        else {
            Caregiver caregiver = new Caregiver(userID, userPhone, userEmail);
            dataManager.saveMe(caregiver);
            dataManager.addToQueue(caregiver);
        }
    }

    /**
     * Verify user exists and login credentials are valid.
     * Loads user into UserController on success.
     * @param userid The userID of the user trying to log in.
     * @return Returns true on successful login, false if credentials are invalid,
     * user doesn't exist or there is already someone logged in.
     */
    public boolean login(String userid) throws NetworkErrorException {
        /* Check for internet connection */
        if (!elasticsearchController.isConnected()){
            throw new NetworkErrorException("No internet access!");
        }

        /* Attempt login */
        if (userController.getUser() != null){
            return false;
        }
        try {
            User user = this.fetchUser(userid);
            userController.loadUser(user);
            dataManager.saveMe(user);
            return true;

        } catch (NoSuchUserException e){
            return false;
        }
    }

    /**
     * Attempt to login based on local data
     * @return
     */
    public boolean localLogin() {
        User localUser = localStorageController.loadMe();
        if (localUser != null){
            if(elasticsearchController.isConnected()){
                try {
                    localUser = this.fetchUser(localUser.getUserID());
                } catch (NoSuchUserException e) {
                    this.logout();
                    return false;
                } catch (NetworkErrorException e) {
                    this.logout();
                    return false;
                }
            }
            dataManager.saveMe(localUser);
            userController.loadUser(localUser);
            return true;
        }
        return false;
    }

    /**
     * Clears all data out of UserController.
     */
    public void logout() {
        UserController.getController().clearUser();
        dataManager.removeMe();
    }

    /**
     * Builds full user object for the given userID from database. Returns said user object.
     * Does not load into UserController
     * @param userID The userID of the user to load.
     * @return A patient or caregiver object built from the userID.
     * @throws NoSuchUserException Thrown if there is no user with the given userID in the database.
     */
    public User fetchUser (String userID) throws NoSuchUserException, NetworkErrorException {

        /* Check if userID corresponds with a patient */
        if (elasticsearchController.existsPatient(userID)){
                return decomposer.compose(elasticsearchController.getPatientDecomposition(userID))
        }

        /* Check if userID corresponds with a caregiver */
        else if (elasticsearchController.existsCaregiver(userID)){
                return decomposer.compose(elasticsearchController.getCaregiverDecomposition(userID));
        }

        else {
            throw new NoSuchUserException("User does not exist");
        }
    }

    /**
     * Take the data in the given user object, convert its components into the corresponding
     * hashmaps and send them to the esc to be uploaded.
     *
     * @param user The user object to be synced into the database.
     * @throws NoSuchUserException Thrown if there is no user with a matching userID already in the
     * database. Shouldn't happen if users are created through the UserManager.
     * @throws IllegalArgumentException Thrown if the inputted user is not a (full) patient or
     * caregiver, e.g. a ContactUser.
     */
    public void saveUser(User user) throws NoSuchUserException, IllegalArgumentException {
        if (user.getUserID() == userController.getUser().getUserID()){
            dataManager.saveMe(user);
        }
        dataManager.addToQueue(user);

        // TODO: this should also be triggered whenever there is an internet connection
       if (elasticsearchController.isConnected()){
           ArrayList<User> toupload = dataManager.getPushQueue();
           UserDecomposer decomposer = new UserDecomposer();
           for (User user1 : toupload){
               UserDecomposer.Decomposition userDecomp = decomposer.decompose(user1);



           }
       }
    }

    /**
     * Delete all data belonging to the user associated with the given userID from the database.
     * Does not delete patients of caregiver when caregiver is deleted.
     * @param userID The userID of the user to be cleared out.
     */
    public void deleteUser(String userID) throws NoSuchUserException, NetworkErrorException {
        elasticsearchController.deleteUser(userID);
    }

    /**
     * Assistant method for retrieving full problem objects
     * @param problemID The problem ID number
     * @return A fully filled out problem object
     */
    private Problem getProblem(String problemID) throws InvalidKeyException {

        try {
            JSONObject problemJSON = elasticsearchController.getProblem(problemID);
            if (problemJSON != null) {

                String title = problemJSON.getString("title");
                String description = problemJSON.getString("description");
                Problem problem = new Problem(title, description);
                problem.setProblemID(problemID);

                /* Add comments */
                for (String comment : (ArrayList<String>) problemJSON.get("comments")){
                    problem.addComment(comment);
                }

                /* Add records (must convert id to int first) */
                for (String recordID : (ArrayList<String>) problemJSON.get("records")){
                    problem.addRecord(getRecord(recordID));
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
     * @param recordID The record ID number
     * @return A fully filled out problem object
     */
    private Record getRecord(String recordID) throws InvalidKeyException{
        try {
            JSONObject recordJSON = elasticsearchController.getRecord(recordID);
            if (recordJSON != null){

                /* Fetch data */
                String title = recordJSON.getString("title");
                Date date = (Date) recordJSON.get("date");
                String description = recordJSON.getString("description");
                String comment = recordJSON.getString("comment");

                Record record = new Record(title, date, description);

                record.setComment(comment);
                record.setRecordID(recordID);

                if (recordJSON.has("geoLocation")){
                    GeoLocation geoLocation = (GeoLocation) recordJSON.get("geoLocation");
                    record.setGeoLocation(geoLocation);
                }
                if (recordJSON.has("bodyLocation")) {
                    BodyLocation bodyLocation = (BodyLocation) recordJSON.get("bodyLocation");
                    record.setBodyLocation(bodyLocation);
                }
                /* Add photos */
                for (String photoID : (ArrayList<String>) recordJSON.get("photos")){
                    Photo photo = elasticsearchController.getPhoto(photoID);
                    photo.setPhotoid(photoID);
                    record.addPhoto(photo);
                }

                return record;
            }
            else{
                throw new InvalidKeyException("Record does not exist!");
            }
        } catch (JSONException e){
            throw new RuntimeException("User record data is corrupt.", e);
        }
    }

}
