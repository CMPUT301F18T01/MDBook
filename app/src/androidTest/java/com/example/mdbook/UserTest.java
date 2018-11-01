package com.example.mdbook;
// test functionalities of user class and controller

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserTest extends TestCase {


    // check that caregivers can add patients but patients can't
    @Test(expected = IllegalArgumentException.class)
    public void testAddPatient(){
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");
        Caregiver  caregiver = new Caregiver("caregiverid", "userphone", "useremail@test.com");
        Patient testPatient = new Patient("testpatient", "userphone", "useremail@test.com");

        // try adding patient to caregiver
        assertTrue(caregiver.addPatient(testPatient));

        // try adding patient to patient
        patient.addPatient(testPatient);
    }

    // check that patients can add problems but caregivers can't
    public void testAddProblem(){
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        Caregiver  c = new Caregiver("caregiverid", "userphone", "useremail@test.com");
        Problem pr = new Problem("title","description");
        Problem pr2 = new Problem("title","description");
        // try adding problem to patient
        u.addProblem(pr);
        assertTrue(u.getProblems().contains(pr));

        //try adding problem to caregiver
        c.addProblem(pr2);
    }

    // userID should be at least 8 characters
    @Test(expected = IllegalArgumentException.class)
    public void testTooShortUserID(){
        Patient p = new Patient("userid", "userphone", "useremail@test.com");
    }

    // test to make sure user controller instantiates only one user object
    public void testUserControllerSingleton(){
        // init usermanager and new patient
        UserManager.initManager();
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");
        // save user to cloud
        UserManager.saveUser(patient);
        // load user from cloud into user controller
        UserController.loadUser("patientid");
        // check loaded user against initial patient
        assertEquals(UserController.getUser().getUserID(), patient.getUserID());
    }




}
