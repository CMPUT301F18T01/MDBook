/*
 * User
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

/**
 * Holds contact information relevant to a user. Has no verification or online sync methods.
 * Class (and subclasses) should never be interacted with directly by the user or activities.
 * The UserManager, UserController and ElasticSearchController classes are tasked with verifying,
 * authenticating, saving and loading user data.
 * The User, Patient and Caregiver classes are simply there to help with organizing and
 * passing data.
 *
 * Acts as superclass to Patient and Caregiver
 *
 * @author Noah Burghardt
 * @see Patient
 * @see Caregiver
 * @see UserController
 * @see UserManager
 * @version 0.0.1
 **/
public abstract class User {

    private String userID;
    private String phone;
    private String email;

    /**
     * Generates a new user object
     * It is up to the UserManager to ensure that the user ID is unique
     * @param userID Unique user ID. User class does not check to make sure it is unique.
     * @param userPhone phone number, stored as a string.
     * @param userEmail email address
     */
    public User(String userID, String userPhone, String userEmail){
        this.userID = userID;
        this.phone = userPhone;
        this.email = userEmail;
    }

    /**
     * Returns this users userID. No setUserID function as there is no requirement for a user to
     * be able to change it and a user cannot be instantiated without a) a unique ID and b) their
     * correct ID
     * @return users ID, as a string
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * @return users phone number, as a string
     */
    public String getPhoneNumber() {
        return this.phone;
    }

    /**
     * @param newphone new phone number
     */
    public void setPhoneNumber(String newphone) {
        this.phone = newphone;
    }

    /**
     * @return users email address, as a string
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email new email address
     */
    public void setEmail(String email){
        this.email = email;
    }
}
