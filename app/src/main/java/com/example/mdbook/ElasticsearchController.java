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

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

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
    private static JestDroidClient client;

    /**
     * @return Singleton instance of ElasticSearchController
     */
    public static ElasticsearchController getController() {
        if (elasticsearchController == null) {
            elasticsearchController = new ElasticsearchController();
        }
        return elasticsearchController;
    }



    public static void verfiySettings(){
        if (client == null){
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/cmput301f18t01");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client  = (JestDroidClient) factory.getObject();
        }
    }







    public void push() {


    }


}

