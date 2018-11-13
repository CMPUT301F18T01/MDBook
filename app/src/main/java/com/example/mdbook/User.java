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
 * Holds information relevant to a user, including a list of problems and contact information.
 * Acts as superclass to Patient and Caregiver
 *
 * @author Noah Burghardt
 * @see com.example.mdbook.Problem
 * @see Patient
 * @see Caregiver
 * @see UserController
 * @version 0.0.1
 **/
public abstract class User {

    private String userID;
    private String phone;
    private String email;
    private ProblemList problems;

    // generate a new user object

    /**
     * Generates a new user object, with a ProblemList of size 0.
     * It is up to the UserManager to ensure that the user ID is unique
     * @param userID
     * @param userPhone
     * @param userEmail
     */
    public User(String userID, String userPhone, String userEmail){
        this.userID = userID;
        this.phone = userPhone;
        this.email = userEmail;
    }

    // return this users userID
    public String getUserID() {
        return("");
    }

    // return this users phone number
    public String getPhoneNumber() {
        return("");
    }

    // update this users phone number
    public void setPhoneNumber(String newphone) {

    }

    // return this users Email
    public String getEmail() {
        return("");
    }
}
