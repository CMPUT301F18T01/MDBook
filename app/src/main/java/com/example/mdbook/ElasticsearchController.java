package com.example.mdbook;
/*
 * ElasticSearchController
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.mapping.PutMapping;

/**
 * Keeps DataManager in sync with cloud storage.
 *
 * @author Noah Burghardt
 * @version 0.0.1
 **/

//http://cmput301.softwareprocess.es:8080/cmput301f18t01test
//http://cmput301.softwareprocess.es:8080/cmput301f18t01

class ElasticsearchController {

    private static ElasticsearchController elasticsearchController = null;
    private DataManager dataManager = DataManager.getDataManager();
    private static JestClient client;
    private static String index = "cmput301f18t01test";
    private static HashMap<String, Object> idlists;
    private int availableID;


    /**
     * @return Singleton instance of ElasticSearchController
     */

    public static ElasticsearchController getController() {
        if (elasticsearchController == null) {
            elasticsearchController = new ElasticsearchController();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080")
                    .multiThreaded(true)
                    .defaultMaxTotalConnectionPerRoute(2)
                    .maxTotalConnection(20)
                    .build());
            client = factory.getObject();
        }

        if (idlists == null){
            idlists = new HashMap<>();
            idlists.put("patientIDs", new ArrayList<String>());
            idlists.put("caregiverIDs", new ArrayList<String>());
            idlists.put("problemIDs", new ArrayList<Integer>());
            idlists.put("recordIDs", new ArrayList<Integer>());
            idlists.put("photoIDs", new ArrayList<Integer>());
            idlists.put("AvailableIDs", new ArrayList<Integer>());

        }
        return elasticsearchController;
    }

    private void pushPatients(){
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        ArrayList<String> patientIDList = new ArrayList<>();

        /* Upload new patients */
        for (String patientID : patients.keySet()){
            patientIDList.add(patientID);
            Index jestIndex = new Index.Builder(patients.get(patientID)).index(index).type("patient").id(patientID).build();
            try {
                client.execute(jestIndex);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload patient " + patientID, e);
            }
        }

        /* Delete removed patients */
        for (String patientID : (ArrayList<String>) this.idlists.get("patientIDs")){
            if (!patientIDList.contains(patientID)){
                try {
                    client.execute(new Delete.Builder(patientID)
                    .index(index)
                    .type("patient")
                    .build());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete patient" + patientID, e);
                }
            }
        }

        /* Update patientID list */
        this.idlists.put("patientIDs", patientIDList);
    }

    private void pushCaregivers(){
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();
        for (String caregiversID : caregivers.keySet()){
            Index jestIndex = new Index.Builder(caregivers.get(caregiversID)).index(index + "/caregiver").type("caregivers").id(caregiversID).build();
            Index saveID = new Index.Builder(caregivers.get(caregiversID)).index(index + "/MetaDataCaregiver/list").type("MetadataCaregivers").id(caregiversID).build();
            try{
                client.execute(jestIndex);
                client.execute(saveID);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload caregives " + caregiversID, e);
            }
        }
    }

    private void pushProblems(){
        HashMap<Integer, JSONObject> problems = dataManager.getProblems();
        for (Integer problemsID : problems.keySet()){
            Index jestIndex = new Index.Builder(problems.get(problemsID)).index(index + "/problems").type("problems").id(problemsID.toString()).build();
            Index saveID = new Index.Builder(problems.get(problemsID)).index(index + "/MetaDataProblems/list").type("MetadataProblems").id(problemsID.toString()).build();
            try{
                client.execute(jestIndex);
                client.execute(saveID);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload problem " + problemsID.toString(), e);
            }
        }
    }

    private void pushRecords(){
        HashMap<Integer, JSONObject> records = dataManager.getRecords();
        for (Integer recordsID : records.keySet()){
            Index jestIndex = new Index.Builder(records.get(recordsID)).index(index+"/records").type("records").id(recordsID.toString()).build();
            Index saveID = new Index.Builder(records.get(recordsID)).index(index+"/MetaDataRecords/list").type("MetadataRecords").id(recordsID.toString()).build();
            try{
                client.execute(jestIndex);
                client.execute(saveID);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload records" + recordsID.toString(), e);
            }
        }
    }

    private void pushPhotos(){
        HashMap<Integer, Photo> photos = dataManager.getPhotos();
        for (Integer photosID : photos.keySet()){
            Index jestIndex = new Index.Builder(photos.get(photosID)).index(index +"/photos").type("photos").id(photosID.toString()).build();
            Index saveID = new Index.Builder(photos.get(photosID)).index(index + "MetaDataPhotos/list").type("MetadataPhotos").id(photosID.toString()).build();
            try{
                client.execute(jestIndex);
                client.execute(saveID);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload photo " + photosID.toString(), e);
            }
        }
    }


    public void push() {
        /*
         * Update objects
         * Each method also updates its corresponding id list but doesn't push it
         */
        this.pushPatients();
        this.pushCaregivers();
        this.pushProblems();
        this.pushRecords();
        this.pushPhotos();

        /* push id lists */
        this.pushIDLists();

    }

    /**
     * IDindex structure:
     * index/metadata/idlists:
     *      patientIDs: list of strings
     *      caregiverIDs: list of strings
     *      problemIDs: list of ints
     *      recordIDs: list of ints
     *      photoIDs: list of ints
     *      availableIDs: list of ints
     *      availableID: int
     */
    private void pushIDLists() {
        JSONObject IDJSON = new JSONObject(this.idlists);
        try {
            IDJSON.put("availableID", this.availableID);
        } catch (JSONException e) {
            throw new RuntimeException();
        }

        //Index IDindex = new Index.Builder(patients.get(patiendID)).index(index + "/MetaDataPatient/list").type("MetadataPatient").id(patiendID).build();
    }

    public void pull(){
        ;
    }


}
