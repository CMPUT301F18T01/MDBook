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

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;

/**
 * test for elasticsearchcontroller
 * tests are a bit wordy, most of this stuff will be wrapped up cleanly in usermanager
 * In order for tests to run, Elasticsearch index must be at cmput301f18t01test
 * @author Noah Burghardt
 * @author James Aina
 *
 * @version 0.0.1
 **/
public class ElasticsearchControllerTest extends  TestCase{

    private String index = "cmput301f18t01test";

    private JestClient getJestClient(){
        JestClientFactory factory = new JestClientFactory();
        factory.setDroidClientConfig(new DroidClientConfig
                .Builder("http://cmput301.softwareprocess.es:8080")
                .multiThreaded(true)
                .defaultMaxTotalConnectionPerRoute(2)
                .maxTotalConnection(20)
                .build());
        return factory.getObject();
    }

    public void testAddPatientTask(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        ElasticsearchController elasticsearchController = ElasticsearchController.getController();
        JestClient client = this.getJestClient();
        /* Create and push new patient */
        String patientID = "testPatientID";
        String testPhone = "testPhone";
        String testEmail = "testEmail";
        try {
            Patient patient = userManager.createPatient(patientID, "test-phone", "testemail");
        } catch (UserIDNotAvailableException e) {
            fail();
        }
        elasticsearchController.push();

        // TODO: set up jest controller in test
        // verify the above patient is created at http://cmput301.softwareprocess.es:8080/cmput301f18t01test/patient/patientID
        // fetch object at given url
        DocumentResult result = null;
        try {
            result = client.execute(new Get.Builder(patientID,index)
                    .type("patient")
                    .build());
            JSONObject resultJSON = result.getSourceAsObject(JSONObject.class);

            assertEquals(testEmail, resultJSON.getString("email"));
            assertEquals(testPhone, resultJSON.getString("phone"));
        } catch (IOException e) {
            fail();
        } catch (JSONException e) {
            fail();
        }


    }

}
