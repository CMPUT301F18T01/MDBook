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

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
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
    private HashMap<String, ArrayList<String>> idlists;
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
        return elasticsearchController;
    }

    private void pushPatients(){
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        for (String patiendID : patients.keySet()){
            Index jestIndex = new Index.Builder(patients.get(patiendID)).index(index).type("patient").id(patiendID).build();
            Index saveID = new Index.Builder(patients.get(patiendID)).index(index + "/MetaDataPatient/list").type("MetadataPatient").id(patiendID).build();
            try {
                client.execute(jestIndex);
                client.execute(saveID);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload patient " + patiendID, e);
            }
        }
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
        this.pushPatients();
        this.pushCaregivers();
        this.pushProblems();
        this.pushRecords();
        this.pushPhotos();

    }

    public void pull(){
        ;
    }


}
