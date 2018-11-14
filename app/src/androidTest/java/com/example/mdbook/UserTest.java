package com.example.mdbook;
// test functionalities of user class and controller


import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

public class UserTest extends TestCase {


    // check that caregivers can add patients
    public void testAddPatient(){
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");
        Caregiver  caregiver = new Caregiver("caregiverid", "userphone", "useremail@test.com");
        Patient testPatient = new Patient("testpatient", "userphone", "useremail@test.com");

        // try adding patient to caregiver
        caregiver.addPatient(testPatient);
        assertTrue(caregiver.getPatients().contains(testPatient));
    }

    // check that patients can add problems
    public void testAddProblem(){
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        Caregiver  c = new Caregiver("caregiverid", "userphone", "useremail@test.com");
        Problem pr = new Problem("title","description");
        Problem pr2 = new Problem("title","description");

        // try adding problem to patient
        u.addProblem(pr);
        assertTrue(u.getProblems().contains(pr));

    }

    // userID should be at least 8 characters
    // expecting illegal argument exception
    public void testTooShortUserID(){
        try {
            Patient patient = new Patient("userid", "userphone", "useremail@test.com");
            assert(false);
        } catch (IllegalArgumentException e){
            assert (true);
        }
    }

    // test to make sure user controller instantiates only one user object
    public void testUserControllerSingleton() {
        // create new patient
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");

        // load user into user controller
        UserController userController = UserController.getUserController();
        userController.loadUser(patient);

        // grab new instance of user
        UserController userController1 = UserController.getUserController();

        // check loaded user against initial patient
        assert (userController1.getUser() == patient);
    }





}
