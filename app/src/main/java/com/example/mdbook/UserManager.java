package com.example.mdbook;

import android.content.Context;

public class UserManager {

    // create proper singleton of self
    public static void initManager(Context context) {

    }

    // convert user to string through json
    public static String userToString(User user) {
        return("");
    }

    //convert string to user through json
    public static User userFromString(String uString) {
        return(new User("uid", "uphone", "uemail"));
    }

    // deconstruct user and update through elasticsearchcontroller
    public void saveUser(User user) {

    }

    // return singleton of self
    public static UserManager getManager() {
        return(new UserManager());
    }

    // deconstruct user and add through elasticsearchcontroller
    public void addUser(Patient u) {
    }

    // verify user exists through elasticsearchcontroller
    // attempt login
    // load user into usercontroller on success
    // return true on success
    public boolean login(String userid) {
        return(true);
    }

    // clear out data in usercontroller
    public void logout() {

    }

    // load data of user into new User object, return User object
    public User loadUser(String Userid) {
        return(new User("userID","userPhone", "userEmail"));
    }

    // deconstruct user and remove all associated data through elasticsearchcontroller
    public void deleteUser(Patient u) {
    }
}
