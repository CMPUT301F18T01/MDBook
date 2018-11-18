/*
 * ElasticsearchControllerTest
 *
 * Version 0.0.1
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import junit.framework.TestCase;

import org.junit.Test;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.fail;

/**
 * test for elasticsearchcontroller
 * tests are a bit wordy, most of this stuff will be wrapped up cleanly in usermanager
 *
 * @author Noah Burghardt
 * @author James Aina
 *
 * @version 0.0.1
 **/
public class ElasticsearchControllerTest {

    @Test
    public void testAddPatientTask(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        ElasticsearchController elasticsearchController = ElasticsearchController.getController();

        /* Create and push new patient */
        try {
            Patient patient = userManager.createPatient("patientID", "testphone", "testemail");
        } catch (UserIDNotAvailableException e) {
            fail();
        }
        elasticsearchController.push();

        // TODO: set up jest controller in test
        // verify the above patient is created at http://cmput301.softwareprocess.es:8080/cmput301f18t01test/patient/patientID

    }
}
