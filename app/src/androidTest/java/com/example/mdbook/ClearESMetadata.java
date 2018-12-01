package com.example.mdbook;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.google.gson.internal.LinkedTreeMap;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.searchbox.client.JestClient;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

public class ClearESMetadata extends TestCase {
    public void testResetStringIDs() {

        HashMap<String, ArrayList<String>> idlists = new HashMap<>();
        String availableID = "0";
        idlists.put("patientIDs", new ArrayList<String>());
        idlists.put("caregiverIDs", new ArrayList<String>());
        idlists.put("problemIDs", new ArrayList<String>());
        idlists.put("recordIDs", new ArrayList<String>());
        idlists.put("photoIDs", new ArrayList<String>());
        idlists.put("availableIDs", new ArrayList<String>());



        JSONObject IDJSON = new JSONObject(idlists);
        try {
            IDJSON.put("availableID", availableID);
        } catch (JSONException e) {
            fail();
        }
        Index JestID = new Index.Builder(IDJSON).index("cmput301f18t01test").type("metadata")
                .id("idlists")
                .build();
        new jestIndexTask().execute(JestID);
    }

    public void testResetIntIDs(){


        HashMap<String, Object> idlists = new HashMap<>();
        int availableID = 0;
        idlists.put("patientIDs", new ArrayList<String>());
        idlists.put("caregiverIDs", new ArrayList<String>());
        idlists.put("problemIDs", new ArrayList<Integer>());
        idlists.put("recordIDs", new ArrayList<Integer>());
        idlists.put("photoIDs", new ArrayList<Integer>());
        idlists.put("availableIDs", new ArrayList<Integer>());



        JSONObject IDJSON = new JSONObject(idlists);
        try {
            IDJSON.put("availableID", availableID);
        } catch (JSONException e) {
            fail();
        }
        Index JestID = new Index.Builder(IDJSON).index("cmput301f18t01test").type("metadata")
                .id("idlists")
                .build();
        new jestIndexTask().execute(JestID);
    }

    private static class jestIndexTask extends AsyncTask<Index, Void, DocumentResult> {

        @Override
        protected DocumentResult doInBackground(Index... indices) {
            for (Index index : indices) {
                try {
                    JestClientFactory factory = new JestClientFactory();
                    factory.setDroidClientConfig(new DroidClientConfig
                            .Builder("http://cmput301.softwareprocess.es:8080")
                            .multiThreaded(true)
                            .defaultMaxTotalConnectionPerRoute(2)
                            .maxTotalConnection(20)
                            .build());
                    JestClient client = factory.getObject();
                    return client.execute(index);
                } catch (IOException e) {
                    return null;
                }
            }
            return null;
        }
    }
}
