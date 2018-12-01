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


import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.google.gson.internal.LinkedTreeMap;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
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
 * Data model of server:
 *
 * PatientID:
 *      "phone": String
 *      "email": String
 *      "problems": ArrayList of problemIDs (numeric string)
 * CaregiverID:
 *      "phone": String
 *      "email": String
 *      "patients": ArrayList of patient userIDs (strings)
 * ProblemID:
 *      "title": String
 *      "description": String
 *      "comments": ArrayList of comments (strings)
 *      "records": ArrayList of recordIDs (numeric string)
 * RecordID:
 *      "title": String
 *      "date": Date
 *      "description": String
 *      "geoLocation": GeoLocation
 *      "bodyLocation": BodyLocation
 *      "photos": ArrayList of photoIDs (numeric string)
 *      "comment": String
 *
 * PhotoID: Photo
 *
 *
 * @author Noah Burghardt
 * @author Thomas Chan
 * @version 1.0.2
 **/
class ElasticsearchController {

    private static ElasticsearchController elasticsearchController;
    private ConnectivityManager connectivityManager;
    private DataManager dataManager;
    private static JestClient client;
    private static String index = "cmput301f18t01";
    private HashMap<String, ArrayList<String>> idlists;
    private String availableID;


    /**
     * @return Singleton instance of ElasticSearchController
     */

    public static void init(ConnectivityManager connectivityManager){
        if (elasticsearchController == null) {
            elasticsearchController = new ElasticsearchController();
            elasticsearchController.dataManager = DataManager.getDataManager();
            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(new DroidClientConfig
                    .Builder("http://cmput301.softwareprocess.es:8080")
                    .multiThreaded(true)
                    .defaultMaxTotalConnectionPerRoute(2)
                    .maxTotalConnection(20)
                    .build());
            client = factory.getObject();
            elasticsearchController.connectivityManager = connectivityManager;
            elasticsearchController.dataManager = DataManager.getDataManager();
            HashMap<String, ArrayList<String>> idl = new HashMap<>();
            idl.put("patientIDs", new ArrayList<String>());
            idl.put("caregiverIDs", new ArrayList<String>());
            idl.put("problemIDs", new ArrayList<String>());
            idl.put("recordIDs", new ArrayList<String>());
            idl.put("photoIDs", new ArrayList<String>());
            idl.put("availableIDs", new ArrayList<String>());
            elasticsearchController.idlists = idl;
            elasticsearchController.availableID = "0";
        }

    }


    public static ElasticsearchController getController() {
        if (elasticsearchController == null) {
            throw new RuntimeException("ElasticsearchController has not been initialized!");
        }
        return elasticsearchController;
    }

    // Returns true/false if the given userID exists/is taken
    public boolean existsUser(String userID) throws NetworkErrorException {
        return (this.existsPatient(userID) || this.existsCaregiver(userID));
    }

    /**
     * Pushes Patients to Elastic search server
     */
    private void pushPatients() {
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        ArrayList<String> patientIDList = new ArrayList<>();

        /* Upload new patients */
        for (String patientID : patients.keySet()) {
            patientIDList.add(patientID);
            Index jestIndex = new Index.Builder(patients.get(patientID)).index(index)
                    .type("patient")
                    .id(patientID)
                    .build();
            new jestIndexTask().execute(jestIndex);
        }

        /* Delete removed patients */
        for (String patientID : (ArrayList<String>) idlists.get("patientIDs")) {
            if (!patientIDList.contains(patientID)) {
                Delete delete = new Delete.Builder(patientID)
                        .index(index)
                        .type("patient")
                        .build();
                new jestDeleteTask().execute(delete);
            }
        }

        /* Update patientID list */
        idlists.put("patientIDs", patientIDList);
    }


    /**
     * Pushes Caregivers to Elastic search server
     */
    private void pushCaregivers() {
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();
        ArrayList<String> caregiverIDList = new ArrayList<>();
        /* Upload new caregivers */
        for (String caregiverID : caregivers.keySet()) {
            caregiverIDList.add(caregiverID);
            Index jestIndex = new Index.Builder(caregivers.get(caregiverID)).index(index)
                    .type("caregiver")
                    .id(caregiverID)
                    .build();
            new jestIndexTask().execute(jestIndex);
        }

        /* Delete removed caregivers */
        for (String caregiverID : (ArrayList<String>) idlists.get("caregiverIDs")) {
            if (!caregiverIDList.contains(caregiverID)) {
                Delete delete = new Delete.Builder(caregiverID)
                        .index(index)
                        .type("caregiver")
                        .build();
                new jestDeleteTask().execute(delete);
            }
        }

        /* Update caregiverID list */
        idlists.put("caregiverIDs", caregiverIDList);
    }


    /**
     * Pushes Problems to Elastic search server
     */
    private void pushProblems() {
        HashMap<Integer, JSONObject> problems = dataManager.getProblems();
        ArrayList<Integer> problemIDList = new ArrayList<>();
        /* Upload new problems */
        for (Integer problemID : problems.keySet()) {
            problemIDList.add(problemID);
            Index jestIndex = new Index.Builder(problems.get(problemID)).index(index)
                    .type("problem")
                    .id(problemID.toString())
                    .build();
            new jestIndexTask().execute(jestIndex);
        }
        /* Delete removed problems */
        for (Integer problemID : (ArrayList<Integer>) idlists.get("problemIDs")) {
        if (!problemIDList.contains(problemID)) {
                Delete delete = new Delete.Builder(problemID.toString())
                        .index(index)
                        .type("problem")
                        .build();
                new jestDeleteTask().execute(delete);
            }
        }

        /* Update problemID list */
        idlists.put("problemIDs", problemIDList);

    }


    /**
     * Pushes records to Elastic search server
     */
    private void pushRecords() {
        HashMap<Integer, JSONObject> records = dataManager.getRecords();
        ArrayList<Integer> recordIDList = new ArrayList<>();
        /* Upload new records */
        for (Integer recordID : records.keySet()) {
            Index jestIndex = new Index.Builder(records.get(recordID)).index(index)
                    .type("record")
                    .id(recordID.toString())
                    .build();
            new jestIndexTask().execute(jestIndex);
        }
        /* Delete removed records */
        for (Integer recordID : (ArrayList<Integer>) idlists.get("recordIDs")) {
            if (!recordIDList.contains(recordID)) {
                Delete delete = new Delete.Builder(recordID.toString())
                        .index(index)
                        .type("record")
                        .build();
                new jestDeleteTask().execute(delete);
            }
        }

        /* Update recordID list */
        idlists.put("recordIDs", recordIDList);


    }


    /**
     * Pushes Photos to Elastic search server
     */
    private void pushPhotos() {
        HashMap<Integer, Photo> photos = dataManager.getPhotos();
        ArrayList<Integer> photoIDList = new ArrayList<>();
        /* Upload new photos */
        for (Integer photoID : photos.keySet()) {
            Index jestIndex = new Index.Builder(photos.get(photoID)).index(index)
                    .type("photo")
                    .id(photoID.toString())
                    .build();
            new jestIndexTask().execute(jestIndex);
        }
        /* Delete removed photos */
        for (Integer photoID : (ArrayList<Integer>) idlists.get("photoIDs")) {
            if (!photoIDList.contains(photoID)) {
                Delete delete = new Delete.Builder(photoID.toString())
                        .index(index)
                        .type("photo")
                        .build();
                new jestDeleteTask().execute(delete);
            }
        }

        /* Update photoID list */
        idlists.put("photoIDs", photoIDList);

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
     * patientIDs: list of strings
     * caregiverIDs: list of strings
     * problemIDs: list of numeric strings
     * recordIDs: list of numeric strings
     * photoIDs: list of numeric strings
     * availableIDs: list of numeric strings
     * availableID: numeric string
     * Pushes ID lists to es server.
     */
    //TODO
    private void pushIDLists() {
        JSONObject IDJSON = new JSONObject(this.idlists);
        try {
            IDJSON.put("availableID", dataManager.getAvailableID());
        } catch (JSONException e) {
            throw new RuntimeException();
        }
        Index JestID = new Index.Builder(IDJSON).index(index).type("metadata")
                .id("idlists")
                .build();
        new jestIndexTask().execute(JestID);

    }

    /**
     * Pulls users from Elastic search server
     *
     * @param string either "patient" or "caregiver" for the type of user you want to pull.
     * @return HashMap of (userID, userJSON).
     */
    public HashMap<String, JSONObject> pullUsers(String string) {
        HashMap<String, JSONObject> users = new HashMap<>();
        for (String userID : (ArrayList<String>) idlists.get(string + "IDs")) {
            try {
                Get get = new Get.Builder(index, userID)
                        .type(string)
                        .build();

                jestGetTask jgt = new jestGetTask();
                jgt.execute(get);
                JestResult result = jgt.get();

                JSONObject resultJSON = result.getSourceAsObject(JSONObject.class);
                users.put(userID, resultJSON);
            }catch (InterruptedException e) {
                throw new RuntimeException("Failed to Pull " + string + "s", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Failed to Pull " + string + "s", e);
            }
        }
        return users;

    }

    /**
     * Pulls problems or records from Elastic search server
     *
     * @param "problem" or "record" depending on which you want to pull
     * @return Hashmap of problems or records
     */
    public HashMap<Integer, JSONObject> pullProblemsRecords(String string) {
        HashMap<Integer, JSONObject> items = new HashMap<>();
        for (Integer itemID : (ArrayList<Integer>) idlists.get(string + "IDs")) {
            try {
                Get get = new Get.Builder(index, itemID.toString())
                        .type(string)
                        .build();

                jestGetTask jgt = new jestGetTask();
                jgt.execute(get);
                JestResult result = jgt.get();

                JSONObject resultJSON = result.getSourceAsObject(JSONObject.class);
                items.put(itemID, resultJSON);
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to Pull " + string + "s", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Failed to Pull " + string + "s", e);
            }
        }
        return items;

    }

    /**
     * Pulls photos from Elastic search server
     *
     * @return Hashmap of photo objects
     */
    public HashMap<Integer, Photo> pullPhotos() {
        HashMap<Integer, Photo> photos = new HashMap<>();
        for (Integer photoID : (ArrayList<Integer>) idlists.get("photoIDs")) {
            try {
                Get get = new Get.Builder(index, photoID.toString())
                        .type("photo")
                        .build();

                jestGetTask jgt = new jestGetTask();
                jgt.execute(get);
                JestResult result = jgt.get();

                Photo resultPhoto = result.getSourceAsObject(Photo.class);
                photos.put(photoID, resultPhoto);
            } catch (InterruptedException e) {
                throw new RuntimeException("Failed to Pull photos", e);
            } catch (ExecutionException e) {
                throw new RuntimeException("Failed to Pull photos", e);
            }
        }
        return photos;

    }

    /**
     * Pulls a list of IDs from Elastic search server
     */
    public void pullIDLists() throws NetworkErrorException {
        JestResult result;

        try {
            Get get = new Get.Builder(index, "idlists")
                    .type("metadata")
                    .build();

            jestGetTask jgt = new jestGetTask();
            jgt.execute(get);
            result = jgt.get();

            JSONObject idlistJSON = result.getSourceAsObject(JSONObject.class);

            ArrayList<String> patientidlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("patientIDs"))
                    .get("values");
            ArrayList<String> caregiveridlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("caregiverIDs"))
                    .get("values");
            ArrayList<String> problemidlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("problemIDs"))
                    .get("values");
            ArrayList<String> recordidlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("recordIDs"))
                    .get("values");
            ArrayList<String> photoidlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("photoIDs"))
                    .get("values");
            ArrayList<String> availableidlist = (ArrayList<String>) ((LinkedTreeMap) idlistJSON
                    .get("availableIDs"))
                    .get("values");

            availableID = idlistJSON.getString("availableID");
            idlists.put("patientIDs", patientidlist);
            idlists.put("caregiverIDs", caregiveridlist);
            idlists.put("problemIDs", problemidlist);
            idlists.put("recordIDs", recordidlist);
            idlists.put("photoIDs", photoidlist);
            idlists.put("availableIDs", availableidlist);


        }catch (JSONException e) {
            throw new NetworkErrorException("ID lists are corrupted.", e);
        } catch (InterruptedException e) {
            throw new NetworkErrorException("Interrupted while fetching idlists.", e);
        } catch (ExecutionException e) {
            throw new NetworkErrorException("Interrupted while fetching idlists.", e);
        } catch (NullPointerException e) {
            throw new NetworkErrorException("Unable to fetch idlists.", e);
        }

    }

    /**
     * Method that calls all other pull methods and sets data back in the dataManager
     */
    public void pull() throws  NetworkErrorException {
        if (isConnected()) {
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


    // indicates if given userID is an existing patient
    public boolean existsPatient(String userID) throws NetworkErrorException {
        this.pullIDLists();
        return this.idlists.get("patientIDs").contains(userID);
    }

    // indicates if given userID is an existing caregiver
    public boolean existsCaregiver(String userID) throws NetworkErrorException {
        this.pullIDLists();
        return this.idlists.get("caregiverIDs").contains(userID);
    }

    //TODO
    public void deleteUser(String userID) throws NetworkErrorException, NoSuchUserException {
        this.pullIDLists();
    }

    /**
     * If there are unused IDs preceding the currently available one, return and remove one of
     * those. Otherwise return fresh ID and increment counter.
     * @return
     */

    public String generateID() throws NetworkErrorException {
        this.pullIDLists();

        if (idlists.get("availableIDs").size() == 0){
            String oldid = availableID;
            availableID = Integer.toString(Integer.valueOf(availableID) + 1);
            this.pushIDLists();

            return oldid;
        }
        else {
            String id = idlists.get("availableIDs").get(0);
            idlists.get("availableIDs").remove(0);
            return id;
        }
    }

    // TODO
    public UserDecomposer.Decomposition getPatientDecomposition(String userID) throws NetworkErrorException {
        try {

            UserDecomposer.Decomposition decomposition = new UserDecomposer.Decomposition(userID);
            this.pullIDLists();

            /* Get user JSON */
            Get get = new Get.Builder(index, userID)
                    .type("patient")
                    .build();

            jestGetTask jgt = new jestGetTask();
            jgt.execute(get);
            JestResult result = jgt.get();
            JSONObject userJSON = result.getSourceAsObject(JSONObject.class);
            decomposition.setUser(userJSON);

            /* Get problem JSON */
            for (String problemID : (ArrayList<String>) userJSON.get("problems")) {
                Get problemGet = new Get.Builder(index, problemID)
                        .type("problem")
                        .build();

                jestGetTask problemJgt = new jestGetTask();
                problemJgt.execute(problemGet);
                JestResult problemResult = problemJgt.get();

                JSONObject problemJSON = problemResult.getSourceAsObject(JSONObject.class);
                decomposition.getProblems().put(problemID, problemJSON);

                for (String recordID : (ArrayList<String>) problemJSON.get("records")){
                    Get recordGet = new Get.Builder(index, recordID)
                            .type("record")
                            .build();
                    jestGetTask recordJgt = new jestGetTask();
                    recordJgt.execute(recordGet);
                    JestResult recordResult = recordJgt.get();

                    JSONObject recordJSON = recordResult.getSourceAsObject(JSONObject.class);
                    decomposition.getRecords().put(recordID, recordJSON);

                    for(String photoID : (ArrayList<String>) recordJSON.get("photos")){
                        Get photoGet = new Get.Builder(index, photoID)
                                .type("photo")
                                .build();
                        jestGetTask photoJgt = new jestGetTask();
                        photoJgt.execute(photoGet);
                        JestResult photoResult = photoJgt.get();

                        Photo photo = photoResult.getSourceAsObject(Photo.class);
                        decomposition.getPhotos().put(photoID, photo);
                    }
                }
            }

            return decomposition;

        } catch (InterruptedException e) {
            throw new NetworkErrorException("Interrupted during user retrieval.", e);
        } catch (ExecutionException e) {
            throw new NetworkErrorException("Interrupted during jest task execution.", e);
        } catch (JSONException e) {
            throw new NetworkErrorException("Unable to parse JSON object", e);
        }

    }

    // TODO
    public UserDecomposer.Decomposition getCaregiverDecomposition(String userID) {

    }

    // TODO
    public boolean setUser(UserDecomposer.Decomposition userDecomp) {

    }

    private static class jestIndexTask extends AsyncTask<Index, Void, DocumentResult> {

        @Override
        protected DocumentResult doInBackground(Index... indices) {
            for (Index index : indices) {
                try {
                    return client.execute(index);
                } catch (IOException e) {
                    return null;
                }
            }
            return null;
        }
    }

    private static class jestDeleteTask extends AsyncTask<Delete, Void, DocumentResult> {

        @Override
        protected DocumentResult doInBackground(Delete... deletes) {
            for (Delete delete : deletes) {
                try {
                    return client.execute(delete);
                } catch (IOException e) {
                    return null;
                }
            }
            return null;
        }
    }

    private static class jestGetTask extends AsyncTask<Get, Void, DocumentResult> {

        @Override
        protected DocumentResult doInBackground(Get... gets) {
            for (Get get : gets) {
                try {
                    return client.execute(get);
                } catch (IOException e) {
                    return null;
                }
            }
            return null;
        }
    }

    // indicates if the esc has internet
    public boolean isConnected() {
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;
    }

}
