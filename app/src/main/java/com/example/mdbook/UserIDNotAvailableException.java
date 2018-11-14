/*
 * UserIDNotAvailableException
 *
 * Version 1.0.0
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

/**
 * An exception to be thrown when requesting to create and account with a UserID that is already
 * taken.
 *
 * @author Noah Burghardt
 * @see UserManager
 * @version 1.0.0
 **/
public class UserIDNotAvailableException extends Exception {
    /**
     * Throws error without error message.
     */
    public UserIDNotAvailableException(){
        super();
    }

    /**
     * @param errorMessage An error message to provide extra information.
     */
    public UserIDNotAvailableException(String errorMessage){
        super(errorMessage);
    }
}
