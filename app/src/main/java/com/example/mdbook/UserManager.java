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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides a singleton interface for managing users and user login / logout.
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
     * Builds user object for the given userID from database. Returns said user object.
     * Does not load into UserController
     * @param userID The userID of the user to load.
     * @return A patient or caregiver object built from the userID.
     * @throws NoSuchUserException Thrown if there is no user with the given userID in the database.
     */
    public User fetchUser (String userID) throws NoSuchUserException {
        return this.esc.getUser(userID);
    }

    /**
     * Builds user object containing only userID, phone and email. Returns said user object.
     * Does not load into UserController. Good for showing an overview of a User.
     * @param userID The ID of the user to fetch.
     * @return A patient or caregiver object with only the contact information stored.
     * @throws NoSuchUserException Thrown if
     */
    public ContactUser fetchUserContact (String userID) throws NoSuchUserException {
        return this.esc.getUserContact(userID);
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
    public void saveUser(User user) throws NoSuchUserException, IllegalArgumentException {
        if (user.getClass() != Patient.class && user.getClass() != Caregiver.class){
            throw new IllegalArgumentException("User is not a patient or a Caregiver!");
        }
        else {
            this.esc.saveUser(user);
        }
    }

    /**
     * Delete all data belonging to the user associated with the given userID from the database.
     * @param userID The userID of the user to be cleared out.
     */
    public void deleteUser(String userID) {
        this.esc.deleteUser(userID);
    }

}
