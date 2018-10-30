package com.example.mdbook;
// test functionalities of user class and controller
import junit.framework.TestCase;

import org.junit.Test;

public class UserTest extends TestCase {

    // test to make sure patient cannot add patients
    public void testCreatePatientProfile(){

    }

    // test to make sure caregiver can add patients
    public void testCreateCaregiverProfile(){

    }

    // check that patients can add problems
    public void testPatientAddProblem(){

    }

    // check that caregivers can't add problems
    @Test(expected = NoSuchMethodException.class)
    public void testCaregiverAddProblem(){

    }

    // userID should be at least 8 characters
    @Test(expected = IllegalArgumentException.class)
    public void testTooShortUserID(){

    }

    // test to make sure user controller instantiates only one user object
    public void testUserControllerSingleton(){

    }




}
