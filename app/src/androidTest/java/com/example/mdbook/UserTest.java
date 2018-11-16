/*
 * UserManagerTest
 *
 * Version 0.0.1
 *
 * UserTest
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;


import junit.framework.TestCase;

/**
 * test functionalities of user class and controller
 *
 * @author Noah Burghardt
 * @author James Aina
 *
 * @see Patient
 * @see com.example.mdbook.Caregiver
 * @see com.example.mdbook.UserController
 * @see com.example.mdbook.UserManager
 *
 * @version 0.0.1
 */
public class UserTest extends TestCase {
    /**
     *  check that caregivers can add patients
     */
    public void testAddPatient(){
        Patient patient = new Patient("patientid", "userphone",
                "useremail@test.com");

        Caregiver  caregiver = new Caregiver("caregiverid", "userphone",
                "useremail@test.com");

        Patient testPatient = new Patient("testpatient", "userphone",
                "useremail@test.com");

         // try adding patient to caregiver
        caregiver.addPatient(testPatient);
        assertTrue(caregiver.getPatientList().contains(testPatient.getUserID()));
    }

    /**
     * check that patients can add problems
     */
    public void testAddProblem(){
        Patient u = new Patient("patientid", "userphone",
                "useremail@test.com");

        Caregiver  c = new Caregiver("caregiverid", "userphone",
                "useremail@test.com");

        Problem pr = new Problem("title","description");
        Problem pr2 = new Problem("title","description");

        // try adding problem to patient
        u.addProblem(pr);
        assertTrue(u.getProblems().contains(pr));

    }

    /**
     * userID should be at least 8 characters
     * expecting illegal argument exception
     */
    public void testTooShortUserID(){
        try {
            Patient patient = new Patient("userid", "userphone",
                    "useremail@test.com");

            assert(false);
        } catch (IllegalArgumentException e){
            assert (true);
        }
    }

    /**
     * test to make sure user controller instantiates only one user object
     */
    public void testUserControllerSingleton() {
         // create new patient
        Patient patient = new Patient("patientid", "userphone",
                "useremail@test.com");

         // load user into user controller
        UserController userController = UserController.getController();
        userController.loadUser(patient);

         // grab new instance of user
        UserController userController1 = UserController.getController();

        // check loaded user against initial patient
        assert (userController1.getUser() == patient);
    }



}
