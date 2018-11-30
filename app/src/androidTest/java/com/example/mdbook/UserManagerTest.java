/*
 * UserManagerTest
 *
 * Version 1.1.0
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import android.accounts.NetworkErrorException;

import junit.framework.TestCase;


/**
 * test functionalities of usermanager and elastisearch controller
 *
 * @author Noah Burghardt
 * @author Jayanta Chattergee
 * @author James Aina
 *
 * @see User
 * @see UserController
 * @see UserManager
 *
 * @version 1.1.0
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
     * Test creating a patient
     */
    public void testLoadSaveDeleteUser(){
        /* Set up test environment */
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        um.logout();
        try {
            um.deleteUser("patientid");
        } catch (NoSuchUserException e) {;}
        assertNull(UserController.getController().getUser());

        /* create new user */
        try {
            um.createPatient("patientid", "userphone",
                    "useremail@test.com");

        } catch (UserIDNotAvailableException e){
            fail();
        } catch (NetworkErrorException e) {
            fail();
        }


        // load profile
        try {
            Patient patient = (Patient) um.fetchUser("patientid");
            assertEquals("userphone", patient.getPhoneNumber());

             // modify and save profile
            patient.setPhoneNumber("newphone");
            um.saveUser(patient);

            // check changes were loaded
            patient = (Patient) um.fetchUser("patientid");

            assertEquals("newphone", patient.getPhoneNumber());

             // delete profile
            um.deleteUser(patient.getUserID());

             //check to make sure patient doesn't exist
            assertFalse(um.login("patientid"));
            assertNull(UserController.getController().getUser());

        } catch (NoSuchUserException e){
            fail();
        }
    }


    /**
     * test for expected output when failing to login.
     * test to make sure data isn't loaded into usercontroller upon failed login
     */
    public void testLoginFail(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        um.logout();
        try {
            um.deleteUser("patientid");
        } catch (NoSuchUserException e) {;}
        try {
            um.deleteUser("caregiverid");
        } catch (NoSuchUserException e) {;}
        assertNull(UserController.getController().getUser());

        try {
            // create new patient
            um.createCaregiver("caregiverid", "userphone", "user@email.com");

            // test that login fails
            assertFalse(um.login("patientid"));

            //test that data is not loaded into usercontroller
            UserController userController = UserController.getController();
            User u = userController.getUser();
            assertNull(u);
        } catch (UserIDNotAvailableException e){
            fail();
        }
    }

    /**
     * test to make sure user data is loaded into user object upon login and removed upon logout.
     */
    public void testUserLogin(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        UserController userController = UserController.getController();
        um.logout();
        try {
            um.deleteUser("patientid");
        } catch (NoSuchUserException e) {;}
        try {
            um.deleteUser("patientid2");
        } catch (NoSuchUserException e){;}
        assertNull(UserController.getController().getUser());

        try {
            // create 2 patients
            um.createPatient("patientid", "userphone",
                    "useremail@test.com");

            um.createPatient("patientid2", "userphone",
                    "useremail2@test.com");

             // test logging in to both accounts
            um.login("patientid");
            assertEquals(userController.getUser().getUserID(), "patientid");
            um.logout();
            um.login("patientid2");
            assertEquals(userController.getUser().getUserID(), "patientid2");
            um.logout();
            assertNull(userController.getUser());
        } catch (UserIDNotAvailableException e){
            fail();
        }

    }


    /**
     * Test to make sure login cannot be completed without logging out first.
     */
    public void testInvalidLogin(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        UserController userController = UserController.getController();
        um.logout();
        try {
            um.deleteUser("patientid");
        } catch (NoSuchUserException e) {;}
        try {
            um.deleteUser("patientid1");
        } catch (NoSuchUserException e){;}
        assertNull(UserController.getController().getUser());

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
            fail();
        }
    }

    /**
     * Test fetching of contact information for user.
     */
    public void testContactUser(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        UserController userController = UserController.getController();
        userManager.logout();
        try {
            userManager.deleteUser("contactid");
        } catch (NoSuchUserException e) {;}
        assertNull(UserController.getController().getUser());

        try{
            String userID = "contactid";
            String userPhone = "userPhone";
            String userEmail = "userEmail";
            userManager.createPatient(userID, userPhone, userEmail);
            ContactUser user = userManager.fetchUserContact("contactid");
            assertEquals("userPhone", user.getPhoneNumber());

        } catch (UserIDNotAvailableException e) {
            fail();
        } catch (NoSuchUserException e) {
            fail();
        }
    }

    /**
     * Test creating a user with a taken userID
     * Expecting a UserIDNotAvailable exception
     */
    public void testTakenUser(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        UserController userController = UserController.getController();
        userManager.logout();
        try {
            userManager.deleteUser("takenuserid");
        } catch (NoSuchUserException e) {;}
        assertNull(UserController.getController().getUser());

        // Create two accounts with the same userid
        // Ensure the first one is created successfully and the second one isn't.
        try {
            userManager.createCaregiver("takenuserid", "phone", "email");
        } catch (UserIDNotAvailableException e) {
            fail();
        }

        try {
            userManager.createPatient("takenuserid","phone", "email");
            fail();
        } catch (UserIDNotAvailableException e) {
            assert true;
        }
    }

    /**
     * userID should be at least 8 characters
     * expecting illegal argument exception
     */
    public void testTooShortUserID(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        UserController userController = UserController.getController();
        userManager.logout();
        try {
            userManager.deleteUser("shortid");
        } catch (NoSuchUserException e) {;}
        assertNull(UserController.getController().getUser());

        try {
            userManager.createPatient("shortid", "userphone",
                    "useremail@test.com");
            fail();
        } catch (IllegalArgumentException e){
            assertTrue(true);
        } catch (UserIDNotAvailableException e) {
            fail();
        }
    }

    /**
     * Thorough test of user fetching, checking addition of problems, records and photos
     */
    public void testFetchUser(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        UserController userController = UserController.getController();
        userManager.logout();
        try {
            userManager.deleteUser("patientid");
        } catch (NoSuchUserException e) {;}
        try {
            userManager.deleteUser("caregiverid");
        } catch (NoSuchUserException e){;}
        assertNull(UserController.getController().getUser());

        /* Create a new caregiver, patient, problem, record and photo and connect them */
        Photo photo = new Photo();
        Record record = new Record("recordtitle");
        record.addPhoto(photo);
        Problem problem = new Problem("problemtitle", "description");
        problem.addRecord(record);

        /* Create patient and caregiver */
        String patientID = "patientid";
        String caregiverID = "caregiverid";
        Patient patient = null;
        Caregiver caregiver = null;
        try {
            patient = userManager.createPatient(patientID, "phone", "email");
            caregiver = userManager.createCaregiver(caregiverID, "phone", "email");
        } catch (UserIDNotAvailableException e) {
            fail();
        }
        patient.addProblem(problem);
        caregiver.addPatient(patient);

        /* save changes */
        try {
            userManager.saveUser(patient);
            userManager.saveUser(caregiver);
        } catch (NoSuchUserException e) {
            fail();
        }

        /* Load changes */
        try {
            patient = (Patient) userManager.fetchUser(patientID);
            caregiver = (Caregiver) userManager.fetchUser(caregiverID);
        } catch (NoSuchUserException e) {
            fail();
        }

        /* compare data */
        problem = patient.getProblems().get(0);
        record = problem.getRecords().get(0);
        assertEquals(caregiverID, caregiver.getUserID());
        assertEquals(patientID, patient.getUserID());
        assertEquals(patient.getUserID(), caregiver.getPatientList().get(0));
        assertEquals("problemtitle", problem.getTitle());
        assertEquals("recordtitle", record.getTitle());
        assertEquals(1, record.getPhotos().size());

    }

    /**
     * Test deleting user with data. No test implemented to search corners of database to see if
     * data still exists, but we can test if 1) Everything crashes when you try to delete stuff and
     * 2) if the data is at least detached from the user.
     */
    public void testDeleteData(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        UserController userController = UserController.getController();
        userManager.logout();
        try {
            userManager.deleteUser("patientid");
        } catch (NoSuchUserException e) {;}
        assertNull(UserController.getController().getUser());

        /* Create a new patient, problem, record and photo and connect them */
        Photo photo = new Photo();
        Record record = new Record("recordtitle");
        record.addPhoto(photo);
        Problem problem = new Problem("problemtitle", "description");
        problem.addRecord(record);

        /* Create patient */
        String patientID = "patientid";
        Patient patient = null;
        try {
            patient = userManager.createPatient(patientID, "phone", "email");
        } catch (UserIDNotAvailableException e) {
            fail();
        }
        patient.addProblem(problem);

        /* save changes */
        try {
            userManager.saveUser(patient);
        } catch (NoSuchUserException e) {
            fail();
        }

        /* delete the record */
        problem.removeRecord(record);
        /* save changes */
        try {
            userManager.saveUser(patient);
        } catch (NoSuchUserException e) {
            fail();
        }

        /* Check to see if changes are saved */
        try {
            patient = (Patient) userManager.fetchUser(patientID);
        } catch (NoSuchUserException e) {
            fail();
        }
        assertEquals(0, patient.getProblems().get(0).getRecords().size());

        /* Delete user */
        try {
            userManager.deleteUser(patientID);
        } catch (NoSuchUserException e) {
            fail();
        }

        /* Check if user still exists */
        try {
            userManager.fetchUser(patientID);
            fail();
        } catch (NoSuchUserException e) {
            assertTrue(true);
        }


    }

}
