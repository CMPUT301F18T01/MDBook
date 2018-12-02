/*
 * NoSuchUserException
 *
 * Version 1.0.0
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

/**
 * An exception to be thrown when an operation is made on a user that doesn't exist.
 * i.e. The userID could not be connected to a user.
 *
 * @author Noah Burghardt
 * @see ElasticsearchController
 * @version 1.0.0
 **/
public class NoSuchUserException extends Exception {
    /**
     * Throws error without error message.
     */
    public NoSuchUserException(){
        super();
    }

    /**
     * @param errorMessage An error message to provide extra information.
     */
    public NoSuchUserException(String errorMessage){
        super(errorMessage);
    }

    public NoSuchUserException(String errorMessage, Throwable cause){ super(errorMessage, cause);}
}
