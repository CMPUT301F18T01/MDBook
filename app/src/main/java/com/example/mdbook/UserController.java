package com.example.mdbook;

// singleton object for managing currently logged in user
class UserController {

    private User user;

    // load user into user object from UserManager
    public void loadUser(String userID) {

    }

    // to be accessed via lazy singleton
    // i.e TODO: make singleton
    public static UserController getUserController() {
        return new UserController();
    }

    // return currently logged in user
    public User getUser() {
        return(this.user);
    }
}
