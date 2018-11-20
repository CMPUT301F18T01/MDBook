/*
 * ElasticsearchControllerTest
 *
 * Version 1.0.0
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;


import com.google.gson.internal.LinkedTreeMap;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/**
 * ElasticSearchControllerTest
 *
 * Tests ElasticSearchController functions by manually setting and checking data with
 * the Jest Client.
 * tests are a bit wordy, most of this stuff will be wrapped up cleanly in usermanager
 * In order for tests to run, Elasticsearch index must be at cmput301f18t01test
 * @author Noah Burghardt
 * @author Thomas Chan
 * @see ElasticsearchController
 * @see UserManager
 *
 * @version 1.0.0
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

    /**
     * Tests that the patient object is uploaded to the es server
     */
    public void testAddPatientTask(){
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        ElasticsearchController elasticsearchController = ElasticsearchController.getController();
        JestClient client = this.getJestClient();
        elasticsearchController.pull();
        /* Create and push new patient */
        String patientID = "testPatientID";
        String testPhone = "testPhone";
        String testEmail = "testEmail";
        try {
            Patient patient = userManager.createPatient(patientID, testPhone, testEmail);
        } catch (UserIDNotAvailableException e) {
            fail();
        }
        elasticsearchController.push();

        // verify the above patient is created at http://cmput301.softwareprocess.es:8080/cmput301f18t01test/patient/patientID
        // fetch object at given url
        JestResult result = null;
        try {
            result = client.execute(new Get.Builder(index, patientID)
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

        try {
            result = client.execute(new Get.Builder(index, "idlists")
                    .type("metadata")
                    .build());
            JSONObject idlistJSON = result.getSourceAsObject(JSONObject.class);
            ArrayList<String> idlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON.get("patientIDs")).get("values");

            assertTrue(idlist.contains(patientID));
        } catch (IOException e) {
            fail();
        } catch (JSONException e) {
            fail();
        }


    }

    /**
     * Tests that the patient object is fetched from the es server
     */
    public void testGetPatientTask() throws JSONException {
        /* Set up testing environment */
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        ElasticsearchController elasticsearchController = ElasticsearchController.getController();
        JestClient client = this.getJestClient();
        /* Create and upload new patient */
        String patientID = "testGetPatientID";
        String testPhone = "testGetPhone";
        String testEmail = "testGetEmail";
        ArrayList<String> patientidlist = null;
        JSONObject idlistJSON = null;
        JSONObject patientJSON = new JSONObject();

        /* Upload the new patient */
        try {
            patientJSON.put("phone",testPhone);
            patientJSON.put("email",testEmail);
            patientJSON.put("problems", new ArrayList<String>());

        } catch (JSONException e) {
            fail();
        }


        Index jestindex = new Index.Builder(patientJSON)

                .index(index)
                .type("patient")
                .id(patientID)
                .build();

        try {
            client.execute(jestindex);
        } catch (IOException e) {
            fail();
        }

        LinkedTreeMap<String, ArrayList<String>> patientIDListJSON = null;
        try {
            JestResult result = client.execute(new Get.Builder(index, "idlists")
                    .type("metadata")
                    .build());

            idlistJSON = result.getSourceAsObject(JSONObject.class);
            patientIDListJSON = (LinkedTreeMap<String, ArrayList<String>>) idlistJSON.get("patientIDs");
            patientidlist = patientIDListJSON.get("values");



        } catch (JSONException e) {
            //throw new RuntimeException("failed here 2", e);
            fail();
        } catch (IOException e) {
            //throw new RuntimeException("failed here 3", e);
            fail();
        }


        /*Add id to patient list then it should push this new list to ES*/
        patientidlist.add(patientID);
        patientIDListJSON.put("values", patientidlist);

        idlistJSON.put("patientIDs", patientIDListJSON);
        Index JestID1 = new Index.Builder(idlistJSON).index(index)
                .type("metadata")
                .id("idlists")
                .build();
        try {
            client.execute(JestID1);
        } catch (IOException e) {
            //throw new RuntimeException("failed here 1", e);
            fail();
        }




        /* attempt to pull into datamanager/usermanager via esc */
        elasticsearchController.pull();

        /* verify data was loaded via the usermanager */
        assertTrue(userManager.login(patientID));
    }

}
