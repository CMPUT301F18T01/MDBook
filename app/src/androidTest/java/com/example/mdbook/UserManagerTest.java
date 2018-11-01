package com.example.mdbook;
// test functionalities of usermanager and elastisearch controller


import android.content.Context;
import android.os.UserManager;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Test;

public class UserManagerTest extends TestCase implements {

    // verify that UserManager only instantiates one instance of itself
    public void testSingleton(){
        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(u);
        UserManager um2  = UserManager.getManager();
        assertTrue(um2.login("userid"));
        um.logout();
    }

    // test loading and saving user data via elastisearch
    // depends on elastisearch controller
    public void testLoadSaveDeleteUser(){

        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        // create new profile
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(u);
        // load profile
        Patient u2 = um.loadUser("patientid");
        assertEquals(u.getPhoneNumber(), u2.getPhoneNumber());

        // modify and save profile
        u.setPhoneNumber("newphone");
        um.saveUser(u);

        // check changes were loaded
        u2 = um.loadUser("patientid");
        assertEquals("newphone", u2.getPhoneNumber());

        // delete profile
        um.deleteUser(u);

        // check to make sure patient doest exist
        assertFalse(um.login("patientid"));

    }

    // converting user object to and from a string
    public void testToFromStringUser(){
        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        String uString = UserManager.userToString(u);
        Patient u2 = UserManager.userFromString(uString);
        assertEquals(u.getUserID(), u2.getUserID());

    }

    // test for expected output when logging in
    // test to make sure data isn't loaded into user object upon failed login
    public void testLoginFail(){
        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        Patient p = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(p);
        // test that login fails
        assertFalse(um.login("patientid_"));
        // test that data is not loaded into usercontroller
        User u = UserController.getUser();
        assertNotSame("patientid", u.getUserID());
        assertNotSame("patientid_", u.getUserID());
    }

    // test to make sure user data is loaded into user object upon login
    // requires use of usercontroller singleton
    public void testUserLogin(){
        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(u);
        Patient u2 = new Patient("patientid2", "userphone", "useremail2@test.com");
        um.addUser(u2);
        User u = UserController.getUser();
        um.login("patientid");
        assertEquals(u.getUserID(), "patientid");
        um.logout();
        um.login("patientid2");
        assertEquals(u.getUserID(), "patientid2");
        um.logout();

    }

    // test to make sure user data is removed from user object upon logout
    // requires use of usercontroller singleton
    public void testUserLogout(){
        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(u);
        User u = UserController.getUser();
        um.login("patientid");
        // make sure login was successful
        assertEquals(u.getUserID(), "patientid");
        um.logout();
        // make sure usercontroller is wiped on logout
        assertNotSame("patientid", u.getUserID());
    }

    // test to make sure login cannot be completed before logging out
    // requires use of usercontroller singleton
    @Test(expected = IllegalStateException.class)
    public void testInvalidLogin(){
        UserManager.initManager(InstrumentationRegistry.getContext());
        UserManager um = UserManager.getManager();
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(u);
        // verify successful login
        assertTrue(um.login("patientid"));
        // attempt logging in without logging out first
        um.login("patientid");
    }
}
