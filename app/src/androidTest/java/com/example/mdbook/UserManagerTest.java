/*
 * UserManagerTest
 *
 * Version 0.0.1
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import junit.framework.TestCase;


/**
 * test functionalities of usermanager and elastisearch controller
 *
 * @author Noah Burghardt
 * @author Jayanta Chattergee
 * @author James Aina
 *
 * @see com.example.mdbook.User
 * @see com.example.mdbook.UserController
 * @see com.example.mdbook.ElasticsearchController
 *
 * @version 0.0.1
 */
public class UserManagerTest extends TestCase {
    /**
     *  verify that UserManager only instantiates one instance of itself
      */
    public void testSingleton(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        UserManager.initManager();
        UserManager um2 = UserManager.getManager();

        // check if the returned UserManager is the exact same object, by reference

        assert(um == um2);
    }

    /**
     * test loading and saving user data via elastisearch
     * depends on elastisearch controller
     */
    public void testLoadSaveDeleteUser(){
        // create new profile
        UserManager.initManager();
        UserManager um = UserManager.getManager();

        try {
            um.createPatient("patientid", "userphone",
                    "useremail@test.com");

        } catch (UserIDNotAvailableException e){
            assert false;
        }


        // load profile
        try {
            Patient patient = (Patient) um.fetchUser("patientid");
            assertEquals("userphone", patient.getPhoneNumber());

             // modify and save profile
            patient.setPhoneNumber("newphone");
            um.saveUser(patient);

            //check changes were loaded
            patient = (Patient) um.fetchUser("patientid");

            assertEquals("newphone", patient.getPhoneNumber());
             // delete profile
            um.deleteUser(patient.getUserID());

             //check to make sure patient doesn't exist
            assertFalse(um.login("patientid"));

        } catch (NoSuchUserException e){
            assert false;
        }
    }


    /**
     *  test for expected output when logging in
     *  test to make sure data isn't loaded into user object upon failed login
      */
    public void testLoginFail(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        try {
            um.createPatient("patientid", "userphone", "user@email.com");

            // test that login fails
            assertFalse(um.login("patientid_"));

            //test that data is not loaded into usercontroller
            UserController userController = UserController.getController();
            User u = userController.getUser();
            assertNotSame("patientid", u.getUserID());
            assertNotSame("patientid_", u.getUserID());
        } catch (UserIDNotAvailableException e){
            assert false;
        }
    }

    /**
     *  test to make sure user data is loaded into user object upon login
     *  requires use of usercontroller singleton
     */
    public void testUserLogin(){

        //add 2 users
        UserManager.initManager();
        UserManager um = UserManager.getManager();

        try {
            um.createPatient("patientid", "userphone",
                    "useremail@test.com");

            um.createPatient("patientid2", "userphone",
                    "useremail2@test.com");

            UserController userController = UserController.getController();

             // test logging in to both accounts
            um.login("patientid");
            assertEquals(userController.getUser().getUserID(), "patientid");
            um.logout();
            um.login("patientid2");
            assertEquals(userController.getUser().getUserID(), "patientid2");

            um.logout();
        } catch (UserIDNotAvailableException e){
            assert false;
        }

    }

    /**
     *  test to make sure user data is removed from user object upon logout
     *  requires use of usercontroller singleton
      */
    public void testUserLogout(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        UserController userController = UserController.getController();
        try {
            um.createPatient("patientid", "userphone",
                    "useremail@test.com");

            um.login("patientid");
            Patient patient1 = (Patient) userController.getUser();

             // make sure login was successful
            assertEquals(patient1.getUserID(), "patientid");

            um.logout();

            // make sure usercontroller is wiped on logout
            assertNull(userController.getUser());

        } catch (UserIDNotAvailableException e){
            assert false;
        }
    }

    /**
     *  test to make sure login cannot be completed before logging out
      */
    public void testInvalidLogin(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        try {
            um.createPatient("patientid", "userphone",
                    "useremail@test.com");

            um.createPatient("patientid1", "userphone1",
                    "useremail1@test.com");

            // verify successful login
            assertTrue(um.login("patientid"));

            // attempt logging in without logging out first
            assertFalse(um.login("patientid"));
            assertFalse(um.login("patientid1"));

            // ensure first user is still the one logged in
            assertEquals("patientid", UserController.getController().getUser()
                    .getUserID());

        } catch (UserIDNotAvailableException e) {
            assert false;
        }
    }
}
