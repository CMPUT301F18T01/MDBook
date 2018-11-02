package com.example.mdbook;

import java.util.ArrayList;

class ElasticsearchController {

    // return singleton version of ElasticsearchController
    // (lazy singleton)
    public static ElasticsearchController getController() {
        return null;
    }

    // add patient to patients object and push to elasticsearch
    public void addPatient(Patient patient) {

    }

    // returns a basic user object with only id, phone and email
    // according to elasticsearch for that userID
    public User getUser(String userID) {
        return(new User("id", "phone", "email"));
    }

    // should add problem with blank record reference and user reference
    // should also add problem id  to user problem id reference list
    // unique id for problem is autogenerated and returned
    public String addProblem(String userID, Problem problem) {
        return null;
    }

    // should add record with parent problem and parent userid references
    // should also add record id to user record id reference list
    // should also add record id to problem record id reference list
    // unique id for record is autogenerated and returned
    public String addRecord(String problemID, String userID, Record record) {
        return null;
    }

    // returns list of problemIDs for given patienID
    public ArrayList<String> getProblems(String patientID) {
        return null;
    }

    // returns userID of patient who owns given problem
    public String getPatientFromProblem(String problemID) {
        return null;
    }

    // returns all associated records for the given patient
    public ArrayList<String> getRecords(String patientID) {
        return null;
    }

    // returns userID of patient who owns the given record
    public String getPatientFromRecord(String recordID) {
        return null;
    }

    // returns combined list of records and parent problems of records with the given
    // geolocation
    public ArrayList<String> searchGeoLocation(GeoLocation geolocation) {
        return null;
    }

    // returns combined list of records and parent problems of records containing
    // keyword
    public ArrayList<String> searchKeyword(String keyWord) {
        return null;
    }
}
