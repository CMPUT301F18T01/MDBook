package com.example.mdbook;
/*
 * ElasticSearchController
 *
 * Version 2.0.0
 *
 * 2018-12-02
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
 *  * http://cmput301.softwareprocess.es:8080/cmput301f18t01test
 *  * http://cmput301.softwareprocess.es:8080/cmput301f18t01
 *  * http://es2.softwareprocess.ca:8080/cmput301f18t01test
 *  * http://es2.softwareprocess.ca:8080/cmput301f18t01
 */


/**
 * Provides a suite of tools for interfacing with Elasticsearch.
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
 *      "date": DateString
 *      "description": String
 *      "comments": ArrayList of comments (strings)
 *      "records": ArrayList of recordIDs (numeric string)
 * RecordID:
 *      "title": String
 *      "date": DateString
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
 * @version 2.0.0
 **/
class ElasticsearchController {

    private static ElasticsearchController elasticsearchController;
    private ConnectivityManager connectivityManager;
    private DataManager dataManager;
    private static JestClient client;
    private static String index = "cmput301f18t01test";
    private HashMap<String, ArrayList<String>> idlists;
    private String availableID;


    /**
     * Initializes the controller. Fills all id lists with empty array lists and starts availableID
     * at "0".
     * @param connectivityManager Connectivity manager generated from application context to check
     *                            internet connection.
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


    /**
     * @return Singleton instance of ElasticSearchController
     */
    public static ElasticsearchController getController() {
        if (elasticsearchController == null) {
            throw new RuntimeException("ElasticsearchController has not been initialized!");
        }
        return elasticsearchController;
    }

    /**
     * Checks if the given userID is available.
     * @param userID The userID to check.
     * @return True or False depending on if the userID is free.
     * @throws NetworkErrorException Thrown if it is unable to reach the elasticsearch server.
     */
    public boolean existsUser(String userID) throws NetworkErrorException {
        return (this.existsPatient(userID) || this.existsCaregiver(userID));
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
     *
     * Pushes cached copy of idlists to server.
     */
    private void pushIDLists() {
        JSONObject IDJSON = new JSONObject(this.idlists);
        try {
            IDJSON.put("availableID", availableID);
        } catch (JSONException e) {
            throw new RuntimeException();
        }
        Index JestID = new Index.Builder(IDJSON).index(index).type("metadata")
                .id("idlists")
                .build();
        new jestIndexTask().execute(JestID);
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
     * Indicates if given userID belongs to a patient.
     * @param userID The userID to check.
     * @return True or False depending on if the userID belongs to a patient.
     * @throws NetworkErrorException Thrown if it is unable to reach the Elasticsearch server.
     */
    public boolean existsPatient(String userID) throws NetworkErrorException {
        this.pullIDLists();
        return this.idlists.get("patientIDs").contains(userID);
    }

    /**
     * Indicates if given userID belongs to a caregiver.
     * @param userID The userID to check.
     * @return True or False depending on if the userID belongs to a caregiver.
     * @throws NetworkErrorException Thrown if it is unable to reach the Elasticsearch server.
     */
    public boolean existsCaregiver(String userID) throws NetworkErrorException {
        this.pullIDLists();
        return this.idlists.get("caregiverIDs").contains(userID);
    }


    /**
     * Removes the userobject and its id from the id list. Called by UserManager.deleteUser, assumes
     * that if the user is a patient, they have no problems/records/photos.
     * @see UserManager
     * @param userID The userID of the user to delete.
     * @throws NetworkErrorException Thrown if the app is unable to reach Elasticsearch.
     * @throws NoSuchUserException Thrown if the userID is not connected to a user.
     */
    public void deleteUser(String userID) throws NetworkErrorException, NoSuchUserException {
        this.pullIDLists();
        if (existsPatient(userID)){
            /* remove from idlist */
            this.idlists.get("patientIDs").remove(userID);
            /* delete patient */
            Delete delete = new Delete.Builder(userID)
                    .index(index)
                    .type("patient")
                    .build();
            new jestDeleteTask().execute(delete);

        } else if (existsCaregiver(userID)){
            /* remove from idlist */
            this.idlists.get("caregiverIDs").remove(userID);
            /* delete caregiver */
            Delete delete = new Delete.Builder(userID)
                    .index(index)
                    .type("caregiver")
                    .build();
            new jestDeleteTask().execute(delete);
        } else {
            throw new NoSuchUserException();
        }
        this.pushIDLists();
    }

    /**
     * If there are unused IDs preceding the currently available one, return and remove one of
     * those. Otherwise return fresh ID and increment counter.
     * @return An ID number that is not as of yet connected to any objects.
     */
    public String generateID() throws NetworkErrorException {
        this.pullIDLists();

        if (idlists.get("availableIDs").size() == 0){
            String oldid = availableID;
            String availableIDList[] = availableID.split("\\.");
            //
            availableID = Integer.toString(Integer.parseInt(availableIDList[0]) + 1);

            this.pushIDLists();
            return oldid;
        }
        else {
            String id = idlists.get("availableIDs").get(0);
            idlists.get("availableIDs").remove(0);

            this.pushIDLists();
            return id;
        }

    }

    /**
     * Returns a decomposition of the given patientID, taken from the server.
     * @see UserDecomposer
     * @param userID The userID of the patient to fetch.
     * @return A decomposition of the fetched patient.
     * @throws NetworkErrorException Thrown if the app is unable to reach the Elasticsearch server.
     */
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

                /* Get record JSON */
                for (String recordID : (ArrayList<String>) problemJSON.get("records")){
                    Get recordGet = new Get.Builder(index, recordID)
                            .type("record")
                            .build();
                    jestGetTask recordJgt = new jestGetTask();
                    recordJgt.execute(recordGet);
                    JestResult recordResult = recordJgt.get();

                    JSONObject recordJSON = recordResult.getSourceAsObject(JSONObject.class);
                    decomposition.getRecords().put(recordID, recordJSON);

                    /* Get photos */
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

    /**
     * Returns a decomposition of the given caregiverID, taken from the server.
     * @see UserDecomposer
     * @param userID The userID of the caregiver to fetch.
     * @return A decomposition of the fetched caregiver.
     * @throws NetworkErrorException Thrown if the app is unable to reach the Elasticsearch server.
     */
    public UserDecomposer.Decomposition getCaregiverDecomposition(String userID) throws NetworkErrorException {
        try {

            UserDecomposer.Decomposition decomposition = new UserDecomposer.Decomposition(userID);
            this.pullIDLists();

            /* Get user JSON */
            Get get = new Get.Builder(index, userID)
                    .type("caregiver")
                    .build();

            jestGetTask jgt = new jestGetTask();
            jgt.execute(get);
            JestResult result = jgt.get();
            JSONObject userJSON = result.getSourceAsObject(JSONObject.class);
            decomposition.setUser(userJSON);
            decomposition.setPhotos(null);
            decomposition.setRecords(null);
            decomposition.setProblems(null);

            return decomposition;

        } catch (InterruptedException e) {
            throw new NetworkErrorException("Interrupted during user retrieval.", e);
        } catch (ExecutionException e) {
            throw new NetworkErrorException("Interrupted during jest task execution.", e);
        }
    }


    /**
     * Attempts to push the deconstructed patient to Elasticsearch.
     * @see UserDecomposer
     * @param userDecomp The decomposition of the user to push.
     * @return False if for any reason the user couldn't be pushed, including a missing internet
     * connection or an incorrectly deconstructed user.
     */
    public boolean pushPatient(UserDecomposer.Decomposition userDecomp) {
        try {
            /* Get fresh id lists */
            this.pullIDLists();
            /* Get cloud version of user */
            UserDecomposer.Decomposition cloudPatient = getPatientDecomposition(userDecomp.getUserid());

            /* Update user JSON */
            if (!this.idlists.get("patientIDs").contains(userDecomp.getUserid())){
                this.idlists.get("patientIDs").add(userDecomp.getUserid());
            }
            Index jestIndex = new Index.Builder(userDecomp.getUser()).index(index)
                    .type("patient")
                    .id(userDecomp.getUserid())
                    .build();
            new jestIndexTask().execute(jestIndex);

            /* Add/update new/existing problems */
            for (String problemID : userDecomp.getProblems().keySet()){
                JSONObject problemJSON = userDecomp.getProblems().get(problemID);
                if (!this.idlists.get("problemIDs").contains(problemID)){
                    this.idlists.get("problemIDs").add(problemID);
                }
                Index problemIndex = new Index.Builder(problemJSON).index(index)
                        .type("problem")
                        .id(problemID)
                        .build();
                new jestIndexTask().execute(problemIndex);
            }

            /* Remove deleted problems */
            for (String problemID : cloudPatient.getProblems().keySet()) {
                if (!userDecomp.getProblems().containsKey(problemID)) {
                    this.idlists.get("problemIDs").remove(problemID);
                    this.idlists.get("availableIDs").add(problemID);
                    Delete delete = new Delete.Builder(problemID)
                            .index(index)
                            .type("problem")
                            .build();
                    new jestDeleteTask().execute(delete);
                }
            }

            /* Add new records */
            for (String recordID : userDecomp.getRecords().keySet()) {
                JSONObject recordJSON = userDecomp.getRecords().get(recordID);
                if (!this.idlists.get("recordIDs").contains(recordID)) {
                    this.idlists.get("recordIDs").add(recordID);
                }


                Index recordIndex = new Index.Builder(recordJSON).index(index)
                        .type("record")
                        .id(recordID)
                        .build();
                new jestIndexTask().execute(recordIndex);
            }


            /* Delete removed records */
            for (String recordID : cloudPatient.getRecords().keySet()) {
                if (!userDecomp.getRecords().containsKey(recordID)) {
                    this.idlists.get("recordIDs").remove(recordID);
                    this.idlists.get("availableIDs").add(recordID);
                    Delete delete = new Delete.Builder(recordID)
                            .index(index)
                            .type("record")
                            .build();
                    new jestDeleteTask().execute(delete);
                }
            }

            /* Add/Update new/existing photos */
            for (String photoID : userDecomp.getPhotos().keySet()){
                Photo photo = userDecomp.getPhotos().get(photoID);
                if (!this.idlists.get("photoIDs").contains(photoID)){
                    this.idlists.get("photoIDs").add(photoID);
                }
                Index photoIndex = new Index.Builder(photo).index(index)
                        .type("photo")
                        .id(photoID)
                        .build();
                new jestIndexTask().execute(photoIndex);
            }

            /* Delete removed photos */
            for (String photoID : cloudPatient.getPhotos().keySet()) {
                if (!userDecomp.getPhotos().containsKey(photoID)) {
                    this.idlists.get("photoIDs").remove(photoID);
                    this.idlists.get("availableIDs").add(photoID);
                    Delete delete = new Delete.Builder(photoID.toString())
                            .index(index)
                            .type("photo")
                            .build();
                    new jestDeleteTask().execute(delete);
                }
            }

            /* Update ID lists */
            this.pushIDLists();

            return true;

        } catch (NetworkErrorException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Attempts to push the deconstructed caregiver to Elasticsearch.
     * @see UserDecomposer
     * @param userDecomp The decomposition of the user to push.
     * @return False if for any reason the user couldn't be pushed, including a missing internet
     * connection or an incorrectly deconstructed user.
     */
    public boolean pushCaregiver(UserDecomposer.Decomposition userDecomp) {
        try {
            this.pullIDLists();

            /* Patient JSON */
            if (!this.idlists.get("caregiverIDs").contains(userDecomp.getUserid())){
                this.idlists.get("caregiverIDs").add(userDecomp.getUserid());
            }
            Index jestIndex = new Index.Builder(userDecomp.getUser()).index(index)
                    .type("caregiver")
                    .id(userDecomp.getUserid())
                    .build();
            new jestIndexTask().execute(jestIndex);

            /* Update ID lists */
            this.pushIDLists();

            return true;

        } catch (NetworkErrorException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Performs a Jest Indexing task in a seperate thread. Returns the result as a DocumentResult.
     */
    private static class jestIndexTask extends AsyncTask<Index, Void, DocumentResult> {

        @Override
        protected DocumentResult doInBackground(Index... indices) {
            for (Index index : indices) {
                try {
                    DocumentResult documentResult = client.execute(index);
                    return documentResult;
                } catch (IOException e) {
                    return null;
                }
            }
            return null;
        }
    }

    /**
     * Performs a Jest Deleting task in a seperate thread. Returns the result as a DocumentResult.
     */
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

    /**
     * Performs a Jest Getting task in a seperate thread. Returns the result as a DocumentResult.
     */
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

    /**
     * @return True or False depending on whether the phone has an internet connection.
     */
    public boolean isConnected() {
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        } else
            return false;
    }

    /**
     * Adds a new Patient (no problems/records/photos) to the server.
     * @see UserDecomposer
     * @param decomposition A decomposition of the new user.
     * @throws NetworkErrorException Thrown if the app is unable to reach the Elasticsearch server.
     */
    public void addPatient(UserDecomposer.Decomposition decomposition) throws NetworkErrorException {
        this.idlists.get("patientIDs").add(decomposition.getUserid());
        Index jestIndex = new Index.Builder(decomposition.getUser()).index(index)
                .type("patient")
                .id(decomposition.getUserid())
                .build();
        new jestIndexTask().execute(jestIndex);
        this.pushIDLists();
    }

    /**
     * Adds a new Caregiver (empty patient list) to the server.
     * @see UserDecomposer
     * @param decomposition A decomposition of the new user.
     * @throws NetworkErrorException Thrown if the app is unable to reach the Elasticsearch server.
     */
    public void addCaregiver(UserDecomposer.Decomposition decomposition) throws NetworkErrorException {
        this.idlists.get("caregiverIDs").add(decomposition.getUserid());
        Index jestIndex = new Index.Builder(decomposition.getUser()).index(index)
                .type("caregiver")
                .id(decomposition.getUserid())
                .build();
        new jestIndexTask().execute(jestIndex);
        this.pushIDLists();
    }

}
