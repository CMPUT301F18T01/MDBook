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

    // create new patient
    public void createPatient(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException {
        Patient patient = new Patient(userID, userPhone, userEmail);
        this.esc.createUser(patient);
    }
    // create new Caregiver
    public void createCaregiver(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException {
        Caregiver caregiver = new Caregiver(userID,userPhone, userEmail);
        this.esc.createUser(caregiver);
    }

    // verify user exists through elasticsearchcontroller
    // attempt login
    // load user into usercontroller on success
    // return true on success, else return false
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

    // load data of user into new User object, return User object
    public User fetchUser (String Userid) {
        return(new Patient("userID","userPhone", "userEmail"));
    }

    // deconstruct user and update through elasticsearchcontroller
    public void saveUser(User user) {

    }

    // deconstruct user and remove all associated data through elasticsearchcontroller
    public void deleteUser(Patient u) {
    }
}
