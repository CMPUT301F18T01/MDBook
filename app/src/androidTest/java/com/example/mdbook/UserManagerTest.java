package com.example.mdbook;
// test functionalities of usermanager


import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;

public class UserManagerTest extends TestCase {
    // verify that UserManager only instantiates one instance of itself
    public void testSingleton(){
        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        User u = new User("patient", "userid", "userphone", "useremail@test.com");
        um.addUser(u);
        UserManager um2  = UserManager.getManager();
        assertTrue(um2.login("userid"));
    }

    // test loading and saving user data via elastisearch
    public void testLoadSaveUser(){

    }

    // converting user object to and from a string
    public void testToFromStringUser(){

    }

    // test for expected output when logging in
    // test to make sure data isn't loaded into user object upon failed login
    public void testLoginFail(){
    }

    // test to make sure user data is loaded into user object upon login
    // requires use of usercontroller singleton
    public void testUserLogin(){

    }

    // test to make sure user data is removed from user object upon logout
    // requires use of usercontroller singleton
    public void testUserLogout(){

    }

    // test to make sure login cannot be completed before logging out
    // requires use of usercontroller singleton
    @Test(expected = IllegalStateException.class)
    public void testInvalidLogin(){

    }
}
