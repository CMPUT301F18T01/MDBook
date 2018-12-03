/*
 * UserManager
 *
 * Version 3.0.0
 *
 * 2018-12-02
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
 * Brings together the controllers for local storage, cache storage and cloud storage for managing
 * data.
 *
 * Based (loosely) off the StudentListManager in the student-picker app by Abram Hindle
 * https://github.com/abramhindle/student-picker
 *
 * @author Noah Burghardt
 * @see UserController
 * @see ElasticsearchController
 * @see LocalStorageController
 * @see DataManager
 * @version 3.0.0
 **/
public class UserManager {

    private static UserManager userManager;
    private DataManager dataManager;
    private ElasticsearchController elasticsearchController;
    private LocalStorageController localStorageController;
    private UserController userController;
    private UserDecomposer decomposer;

    /**
     * Initialize singleton instance and fetch controllers.
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
     * Auto initializes if not already done
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
            elasticsearchController.addPatient(decomposer.decompose(patient));
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
            elasticsearchController.addCaregiver(decomposer.decompose(caregiver));
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
     * Attempt to login based on local data.
     * @return True if there is a local copy of user data and login based on that was successful.
     */
    public boolean localLogin() {
        User localUser = localStorageController.loadMe();
        if (localUser != null){

            // TODO: solve this race condition
            // As is, cloud based changes will not overwrite the local copy, at all (no additions)
            if(elasticsearchController.isConnected()){
                try {
                    User cloudUser = this.fetchUser(localUser.getUserID());

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
     * Clears all data out of cache and local storage.
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
     * @throws NetworkErrorException Thrown if app is unable to reach Elasticsearch server.
     */
    public User fetchUser (String userID) throws NoSuchUserException, NetworkErrorException {

        /* Check if userID corresponds with a patient */
        if (elasticsearchController.existsPatient(userID)){
            return decomposer.compose(elasticsearchController.getPatientDecomposition(userID));
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
     * Take the data in the given user object, deconstructs it and queues it up for upload.
     * If internet is available, also uploads all changes from queue to Elasticsearch.
     *
     * @param user The user object to be synced into the database.
     */
    public void saveUser(User user) {
        if (userController.getUser() != null) {
            if (user.getUserID().equals(userController.getUser().getUserID())) {
                dataManager.saveMe(user);
            }
        }
        dataManager.addToQueue(user);

        // TODO: this should also be triggered whenever there is an internet connection
        if (elasticsearchController.isConnected()){
            ArrayList<User> toupload = (ArrayList<User>) dataManager.getPushQueue().clone();
            for (User user1 : toupload){

                UserDecomposer.Decomposition userDecomp;
                try {
                    userDecomp = decomposer.decompose(user1);
                    if (user1.getClass() == Patient.class) {
                        if (elasticsearchController.pushPatient(userDecomp)) {
                            dataManager.removeFromQueue(user1);
                        }
                    }
                    else {
                        if (elasticsearchController.pushCaregiver(userDecomp)) {
                            dataManager.removeFromQueue(user1);
                        }
                    }

                } catch (NetworkErrorException e) {
                    break;
                }
            }
        }
    }

    /**
     * Delete all data belonging to the user associated with the given userID from the database.
     * Does not delete patients of caregiver when caregiver is deleted.
     * @param userID The userID of the user to be cleared out.
     */
    public void deleteUser(String userID) throws NoSuchUserException, NetworkErrorException {
        if (elasticsearchController.existsPatient(userID)) {
            /* Replace patient with empty patient
             * Effectively deletes all problems, records and photos
             */
            Patient patient = new Patient(userID, "", "");
            userManager.saveUser(patient);
        } else if (!elasticsearchController.existsCaregiver(userID)) {
            throw new NoSuchUserException();
        }

        /* Delete what remains of the user */
        elasticsearchController.deleteUser(userID);
    }





}