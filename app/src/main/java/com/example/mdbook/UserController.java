/*
 * UserController
 *
 * Version 1.0.0
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

/**
 * Provides a singleton interface for the currently logged in user.
 * Should only be loaded by the UserManager,
 * which fills it in on login and clears it out on logout.
 *
 * @author Noah Burghardt
 * @see User
 * @see UserManager
 * @version 1.0.0
 **/
class UserController {

    private User user;
    private static UserController userController;


    /**
     * @return Singleton instance of UserController.
     */
    public static UserController getController() {
        if(userController == null){
            userController = new UserController();
        }
        return userController;
    }

    /**
     * Load the given user into the controller for global access.
     * Most likely called by UserManager.login().
     * @param user The user object to be logged in and globally accessible.
     */
    public void loadUser(User user) {
        this.user = user;
    }

    /**
     * Clears the data out of the user controller.
     * Most likely called by UserManager.logout().
     */
    public void clearUser(){
        this.user = null;
    }

    /**
     * Returns the instance of the currently logged in user.
     * Will return null if there is no logged in user.
     * @return The currently logged in user.
     */
    public User getUser() {
        return this.user;
    }
}
