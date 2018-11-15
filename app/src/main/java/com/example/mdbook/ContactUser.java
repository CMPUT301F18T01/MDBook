/*
 * ContactUser
 *
 * Version 1.0.0
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

/**
 * User object for only holding contact information. Good for giving overviews of a user profile.
 *
 * @author Noah Burghardt
 * @see User
 * @see UserManager
 * @version 1.0.0
 **/
public class ContactUser extends User {

    /**
     * Generates a new user object
     * It is up to the UserManager to ensure that the user ID is unique
     *
     * @param userID    Unique user ID. User class does not check to make sure it is unique.
     * @param userPhone phone number, stored as a string.
     * @param userEmail email address
     */
    public ContactUser(String userID, String userPhone, String userEmail) {
        super(userID, userPhone, userEmail);
    }
}
