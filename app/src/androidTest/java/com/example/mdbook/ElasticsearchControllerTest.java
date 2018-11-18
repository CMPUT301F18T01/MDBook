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
        Patient patient = new Patient("patientid", "userphone",
                "useremail@test.com");
        ElasticsearchController.AddPatientTask addPatient = new ElasticsearchController.AddPatientTask();
        addPatient.execute(patient);
    }
}
