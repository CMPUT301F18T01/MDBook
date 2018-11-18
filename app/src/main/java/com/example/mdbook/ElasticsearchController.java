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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

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

    }
    /*

    public static class AddPatientTask extends AsyncTask<Patient, Void, Void> {

        @Override
        protected Void doInBackground(Patient... patients) {
            verifySettings();

            for (Patient patient : patients) {
                Index index = new Index.Builder(patient).index("testing").type("patient").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        patient.userID = result.getId();
                    }
                    else {
                        Log.i("Error", "Elasticsearch was not able to add the patient");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the patients");
                }

            }
            return null;
        }
    }
*/
    /*
    commented out for the time being for testing purposes


    // TODO we need a function which gets tweets from elastic search
    public static class GetTask extends AsyncTask<String, Void, ArrayList<NormalTweet>> {
        @Override
        protected ArrayList<NormalTweet> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<NormalTweet> tweets = new ArrayList<NormalTweet>();

            // TODO Build the query

            //String query = "{ \"size\": 3, \"query\" : { \"term\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{ \"size\": 3, \n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"message\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}" ;

            Search search = new Search.Builder(query)
                    .addIndex("testing")
                    .addType("tweet")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()){
                    List<NormalTweet> foundTweets = result.getSourceAsObjectList(NormalTweet.class);
                    tweets.addAll(foundTweets);
                }
                else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tweets;
        }
    }
    */

/*

    public static void verifySettings(){
        if (client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/cmput301f18t01");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client  = (JestDroidClient) factory.getObject();
        }
    }

*/





    public void push() {

        this.pushPatients();
    }


}
