package com.example.mdbook;
/*
 * ElasticSearchController
 *
 * Version 1.0.2
 *
 * 2018-11-20
 *
 * Copyright (c) 2018. All rights reserved.
 */


import com.google.gson.internal.LinkedTreeMap;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.Get;
import io.searchbox.core.Index;


/**
 * Elastic search Servers
 * http://cmput301.softwareprocess.es:8080/cmput301f18t01test
 * http://cmput301.softwareprocess.es:8080/cmput301f18t01
 */


/**
 * Keeps DataManager in sync with cloud storage.
 *
 * @author Noah Burghardt
 * @author Thomas Chan
 * @version 1.0.2
 **/
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
            idlists.put("availableIDs", new ArrayList<Integer>());

        }
        return elasticsearchController;
    }

    /**
     * Pushes Patients to Elastic search server
     */
    private void pushPatients(){
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        ArrayList<String> patientIDList = new ArrayList<>();

        /* Upload new patients */
        for (String patientID : patients.keySet()){
            patientIDList.add(patientID);
            Index jestIndex = new Index.Builder(patients.get(patientID)).index(index)
                    .type("patient")
                    .id(patientID)
                    .build();
            try {
                client.execute(jestIndex);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload patient " + patientID, e);
            }
        }

        /* Delete removed patients */
        for (String patientID : (ArrayList<String>) idlists.get("patientIDs")){
            if (!patientIDList.contains(patientID)){
                try {
                    client.execute(new Delete.Builder(patientID)
                            .index(index)
                            .type("patient")
                            .build());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to delete patient " + patientID, e);
                }
            }
        }

        /* Update patientID list */
        idlists.put("patientIDs", patientIDList);
    }


    /**
     * Pushes Caregivers to Elastic search server
     */
    private void pushCaregivers(){
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();
        ArrayList<String> caregiverIDList = new ArrayList<>();
        /* Upload new caregivers */
        for (String caregiverID : caregivers.keySet()){
            caregiverIDList.add(caregiverID);
            Index jestIndex = new Index.Builder(caregivers.get(caregiverID)).index(index)
                    .type("caregiver")
                    .id(caregiverID)
                    .build();
            try{
                client.execute(jestIndex);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload caregive " + caregiverID, e);
            }
        }

        /* Delete removed caregivers */
        for (String caregiverID : (ArrayList<String>) idlists.get("caregiverIDs")){
            if (!caregiverIDList.contains(caregiverID)){
                try{
                    client.execute(new Delete.Builder(caregiverID)
                            .index(index)
                            .type("caregiver")
                            .build());
                } catch (IOException e){
                    throw new RuntimeException("Failed to delete caregiver " + caregiverID, e);
                }
            }
        }

        /* Update caregiverID list */
        idlists.put("caregiverIDs",caregiverIDList);
    }


    /**
     * Pushes Problems to Elastic search server
     */
    private void pushProblems(){
        HashMap<Integer, JSONObject> problems = dataManager.getProblems();
        ArrayList<Integer> problemIDList = new ArrayList<>();
        /* Upload new problems */
        for (Integer problemID : problems.keySet()){
            problemIDList.add(problemID);
            Index jestIndex = new Index.Builder(problems.get(problemID)).index(index)
                    .type("problem")
                    .id(problemID.toString())
                    .build();
            try{
                client.execute(jestIndex);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload problem " + problemID.toString(), e);
            }
        }
        /* Delete removed problems */
        for (Integer problemID : (ArrayList<Integer>) idlists.get("problemIDs")){
            if (!problemIDList.contains(problemID)){
                try{
                    client.execute(new Delete.Builder(problemID.toString())
                            .index(index)
                            .type("problem")
                            .build());
                } catch (IOException e){
                    throw new RuntimeException("Failed to delete problem " + problemID.toString(), e);
                }
            }
        }

        /* Update problemID list */
        idlists.put("problemIDs",problemIDList);

    }


    /**
     * Pushes records to Elastic search server
     */
    private void pushRecords(){
        HashMap<Integer, JSONObject> records = dataManager.getRecords();
        ArrayList<Integer> recordIDList = new ArrayList<>();
        /* Upload new records */
        for (Integer recordID : records.keySet()){
            Index jestIndex = new Index.Builder(records.get(recordID)).index(index)
                    .type("record")
                    .id(recordID.toString())
                    .build();
            try{
                client.execute(jestIndex);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload record" + recordID.toString(), e);
            }
        }
        /* Delete removed records */
        for (Integer recordID : (ArrayList<Integer>) idlists.get("recordIDs")){
            if (!recordIDList.contains(recordID)){
                try{
                    client.execute(new Delete.Builder(recordID.toString())
                            .index(index)
                            .type("record")
                            .build());
                } catch (IOException e){
                    throw new RuntimeException("Failed to delete record " + recordID.toString(), e);
                }
            }
        }

        /* Update recordID list */
        idlists.put("recordIDs",recordIDList);


    }


    /**
     * Pushes Photos to Elastic search server
     */
    private void pushPhotos(){
        HashMap<Integer, Photo> photos = dataManager.getPhotos();
        ArrayList<Integer> photoIDList = new ArrayList<>();
        /* Upload new photos */
        for (Integer photoID : photos.keySet()){
            Index jestIndex = new Index.Builder(photos.get(photoID)).index(index)
                    .type("photo")
                    .id(photoID.toString())
                    .build();
            try{
                client.execute(jestIndex);
            } catch (IOException e){
                throw  new RuntimeException("Failed to upload photo " + photoID.toString(), e);
            }
        }
        /* Delete removed photos */
        for (Integer photoID : (ArrayList<Integer>) idlists.get("photoIDs")){
            if (!photoIDList.contains(photoID)){
                try{
                    client.execute(new Delete.Builder(photoID.toString())
                            .index(index)
                            .type("photo")
                            .build());
                } catch (IOException e){
                    throw new RuntimeException("Failed to delete photo " + photoID.toString(), e);
                }
            }
        }

        /* Update photoID list */
        idlists.put("photoIDs",photoIDList);

    }

    /**
     * Calls all methods to push data to Elastic search server
     */
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
     *
     * Pushes ID lists to es server.
     */
    private void pushIDLists() {
        JSONObject IDJSON = new JSONObject(this.idlists);
        try {
            IDJSON.put("availableID", this.availableID);
        } catch (JSONException e) {
            throw new RuntimeException();
        }
        Index JestID = new Index.Builder(IDJSON).index(index).type("metadata")
                .id("idlists")
                .build();
        try {
            client.execute(JestID);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't push id lists", e);
        }

    }

    /**
     * Pulls users from Elastic search server
     * @param string either "patient" or "caregiver" for the type of user you want to pull.
     * @return HashMap of (userID, userJSON).
     */
    public HashMap<String, JSONObject> pullUsers(String string){
        HashMap<String, JSONObject> users = new HashMap<>();
        for (String userID: (ArrayList<String>) idlists.get(string +"IDs")) {
            try {
                JestResult result = client.execute(new Get.Builder(index, userID)
                        .type(string)
                        .build());
                JSONObject resultJSON = result.getSourceAsObject(JSONObject.class);
                users.put(userID, resultJSON);
            } catch (IOException e) {
                throw new RuntimeException("Failed to Pull " + string + "s" ,e);
            }
        }
        return users;

    }

    /**
     * Pulls problems or records from Elastic search server
     * @param "problem" or "record" depending on which you want to pull
     * @return Hashmap of problems or records
     */
    public HashMap<Integer, JSONObject> pullProblemsRecords(String string){
        HashMap<Integer, JSONObject> items = new HashMap<>();
        for (Integer itemID: (ArrayList<Integer>) idlists.get(string +"IDs")) {
            try {
                JestResult result = client.execute(new Get.Builder(index, itemID.toString())
                        .type(string)
                        .build());
                JSONObject resultJSON = result.getSourceAsObject(JSONObject.class);
                items.put(itemID, resultJSON);
            } catch (IOException e) {
                throw new RuntimeException("Failed to Pull " + string + "s",e);
            }
        }
        return items;

    }

    /**
     * Pulls photos from Elastic search server
     * @return Hashmap of photo objects
     */
    public HashMap<Integer, Photo> pullPhotos(){
        HashMap<Integer, Photo> photos = new HashMap<>();
        for (Integer photoID: (ArrayList<Integer>) idlists.get("photoIDs")) {
            try {
                JestResult result = client.execute(new Get.Builder(index, photoID.toString())
                        .type("photo")
                        .build());
                Photo resultPhoto = result.getSourceAsObject(Photo.class);
                photos.put(photoID, resultPhoto);
            } catch (IOException e) {
                throw new RuntimeException("Failed to Pull photos",e);
            }
        }
        return photos;

    }

    /**
     * Pulls a list of IDs from Elastic search server
     */
    public void pullIDLists() {
        JestResult result = null;

        try {
            result = client.execute(new Get.Builder(index, "idlists")
                    .type("metadata")
                    .build());
            JSONObject idlistJSON = result.getSourceAsObject(JSONObject.class);
            ArrayList<String> patientidlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("patientIDs"))
                    .get("values");
            ArrayList<String> caregiveridlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("caregiverIDs"))
                    .get("values");
            ArrayList<Integer> problemidlist = (ArrayList<Integer>) ((LinkedTreeMap) idlistJSON
                    .get("problemIDs"))
                    .get("values");
            ArrayList<Integer> recordidlist = (ArrayList<Integer>) ((LinkedTreeMap) idlistJSON
                    .get("recordIDs"))
                    .get("values");
            ArrayList<Integer> photoidlist = (ArrayList<Integer>) ((LinkedTreeMap) idlistJSON
                    .get("photoIDs"))
                    .get("values");
            ArrayList<Integer> availableidlist = (ArrayList<Integer>) ((LinkedTreeMap) idlistJSON
                    .get("availableIDs"))
                    .get("values");
            Integer availableID = idlistJSON.getInt("availableID");

            idlists.put("patientIDs", patientidlist);
            idlists.put("caregiverIDs", caregiveridlist);
            idlists.put("problemIDs", problemidlist);
            idlists.put("recordIDs", recordidlist);
            idlists.put("photoIDs", photoidlist);
            idlists.put("availableIDs", availableidlist);
            idlists.put("availableID",availableID);
            dataManager.setAvailableID(availableID);
            dataManager.setAvailableIDs(availableidlist);


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method that calls all other pull methods and sets data back in the dataManager
     */
    public void pull() {
        /*Execute this first to get ID list */
        this.pullIDLists();

        /*pull back data from ES */
        dataManager.setPatients(this.pullUsers("patient"));
        dataManager.setCaregivers(this.pullUsers("caregiver"));
        dataManager.setProblems(this.pullProblemsRecords("problem"));
        dataManager.setRecords(this.pullProblemsRecords("record"));
        dataManager.setPhotos(this.pullPhotos());

    }

}
