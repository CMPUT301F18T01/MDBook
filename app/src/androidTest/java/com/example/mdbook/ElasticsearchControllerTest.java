/*
 * ElasticsearchControllerTest
 *
 * Version 1.0.0
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;


import com.google.gson.internal.LinkedTreeMap;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/**
 * ElasticSearchControllerTest
 *
 * Tests ElasticSearchController functions by manually setting and checking data with
 * the Jest Client.
 * tests are a bit wordy, most of this stuff will be wrapped up cleanly in usermanager
 * In order for tests to run, Elasticsearch index must be at cmput301f18t01test
 * @author Noah Burghardt
 * @author Thomas Chan
 * @see ElasticsearchController
 * @see UserManager
 *
 * @version 1.0.0
 **/
public class ElasticsearchControllerTest extends  TestCase{



}
