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

import java.util.ArrayList;

/**
 * Provides a singleton interface for managing users and user login / logout.
 * On login, the logged in user object is to be interacted with through the UserController.
 * Relies on ElasticsearchController for data lookup and management.
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
    private ElasticsearchController esc;


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
        this.esc = ElasticsearchController.getController();
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
        Patient patient = new Patient(userID, userPhone, userEmail);
        this.esc.createUser(patient);
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
        Caregiver caregiver = new Caregiver(userID,userPhone, userEmail);
        this.esc.createUser(caregiver);
    }

    // verify user exists through elasticsearchcontroller
    // attempt login
    // load user into usercontroller on success
    // return true on success, else return false

    /**
     * 
     * @param userid
     * @return
     */
    public boolean login(String userid) {
        try {
            User user = this.esc.getUser(userid);
            UserController.getController().loadUser(user);
            return true;
        } catch (NoSuchUserException e){
            return false;
        }
    }

    // clear out data in usercontroller
    public void logout() {
        UserController.getController().clearUser();
    }

    // load data of arbitrary user into new User object, return User object
    public User fetchUser (String userID) throws NoSuchUserException {
        return this.esc.getUser(userID);
    }

    // save data of arbitrary user
    public void saveUser(User user) throws NoSuchUserException {
        this.esc.saveUser(user);
    }

    // delete arbitrary user
    public void deleteUser(User user) {
        this.esc.deleteUser(user);
    }
}
