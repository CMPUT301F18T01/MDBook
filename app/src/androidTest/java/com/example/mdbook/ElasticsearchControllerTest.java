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
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        try {
            Patient patient = userManager.createPatient("patientID", "phone", "email");
        } catch (UserIDNotAvailableException e) {
            fail();
        }



    }
}
