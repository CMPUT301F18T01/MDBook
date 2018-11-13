package com.example.mdbook;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

// test for elasticsearchcontroller
// tests are a bit wordy, most of this stuff will be wrapped up cleanly in usermanager
public class ElasticsearchControllerTest extends TestCase {
    // test ability to load and save users, problems and records
    public void testSaveLoad() {
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");
        ElasticsearchController esc = ElasticsearchController.getController();
        esc.addPatient(patient);

        // ensure patient is added and can be loaded by esc
        assertEquals(patient.getEmail(), esc.getUser("patientid").getEmail());

        // add problem, record and connect with patient
        Problem problem = new Problem("Title", "Description");
        Record record = new Record("Title", new Date(), "Description");
        // should add problem with blank record reference and patientID user reference
        // should also add problem/record id  to "patientid" problem/record id reference list
        String problemID = esc.addProblem("patientid", problem);
        String recordID = esc.addRecord(problemID, "patientID", record);


        // ensure esc can retrieve problem from patient
        // esc.getProblems("pid") checks the patients object for a patient matching pid
        // then returns the list of problem ids under the "problemids" key
        ArrayList<String> problemIDs = esc.getProblems("patientid");
        assertTrue(problemIDs.contains(problemID));

        // ensure esc can retrieve patient from problem
        // esc.getPatientFromProblem(prid) checks the problems object for a problem matching prid
        // then returns the value under the "userid" key
        assertEquals("patientid", esc.getPatientFromProblem(problemID));

        // ensure esc can retrieve record from patient and vice versa (similar api)
        ArrayList<String> recordIDs = esc.getRecords("patientid");
        assertTrue(recordIDs.contains(recordID));
        assertEquals("patiendid", esc.getPatientFromRecord(recordID));
    }

    // test ability to search records and problems based on keywords, geolocation or bodylocation
    // TODO: bodylocation
    public void testSearch(){
        // create and add a new patient
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");
        ElasticsearchController esc = ElasticsearchController.getController();
        Problem problem = new Problem("Title", "Description coconut");
        Record record = new Record("Title", new Date(), "Description banana");
        GeoLocation geolocation = new GeoLocation();
        record.setLocation(geolocation);

        esc.addPatient(patient);
        String problemID = esc.addProblem("patientid", problem);
        String recordID = esc.addRecord(problemID, "patientid", record);

        // ensure location search works
        ArrayList<String> georesults = esc.searchGeoLocation(geolocation);
        assertTrue(georesults.contains(recordID) && !georesults.contains(problemID));

        // ensure keyword search works
        ArrayList<String> keywordResults = esc.searchKeyword("coconut");
        assertTrue(keywordResults.contains(problemID) && !keywordResults.contains(recordID));
    }
}
