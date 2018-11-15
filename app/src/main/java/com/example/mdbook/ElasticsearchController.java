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

/**
 * Keeps DataManager in sync with cloud storage.
 *
 * @author Noah Burghardt
 * @version 0.0.1
 **/
class ElasticsearchController {

    private static ElasticsearchController elasticsearchController = null;
    private DataManager dataManager = DataManager.getDataManager();

    /**
     * @return Singleton instance of ElasticSearchController
     */
    public static ElasticsearchController getController() {
        if (elasticsearchController == null){
            elasticsearchController = new ElasticsearchController();
        }
        return elasticsearchController;
    }


}

