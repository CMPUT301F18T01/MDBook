/*
 * LocalStorageController
 *
 * Version 1.0.0
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


/**
 * Provide a singleton interface for managing local data storage.
 *
 *
 *
 * @author Thomas Chan
 * @author Noah Burghardt
 * @see DataManager
 * @version 1.0.0
 */

class LocalStorageController {


    private static LocalStorageController localStorageController = null;
    private DataManager dataManager;
    private Gson patients = new Gson();
    private Gson caregivers = new Gson();
    private Gson problems = new Gson();
    private Gson records = new Gson();
    private Gson photos = new Gson();
    private Gson availableIDs = new Gson();
    private SharedPreferences sharedPreferences;


    /**
     * @param sharedPreferences Sets the context in the local storage controller
     */
    public static void init(SharedPreferences sharedPreferences){
        if (localStorageController == null) {
            localStorageController = new LocalStorageController();
        }
        localStorageController.sharedPreferences = sharedPreferences;
        localStorageController.dataManager = DataManager.getDataManager();
    }

    /**
     * Sets the localStorageController, If it is not found then it will create a new
     * localStorageController.
     * @return localStorageController singleton
     */

    public static LocalStorageController getController() {
        if (localStorageController == null) {
            throw new RuntimeException("LocalStorageController has not been initialized!");
        }
        return localStorageController;


    }


    /**
     * Saves all objects to Gson including:
     * Patients, Caregivers, Problems, Records ,Photos and Available
     */
    public void push() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String stringPatients = patients.toJson(dataManager.getPatients());
        String stringCaregivers = caregivers.toJson(dataManager.getCaregivers());
        String stringProblems = problems.toJson(dataManager.getProblems());
        String stringRecords = records.toJson(dataManager.getRecords());
        String stringPhotos = photos.toJson(dataManager.getPhotos());
        String stringAvailableIDs = availableIDs.toJson(dataManager.getAvailableIDs());
        editor.putString("patients", stringPatients);
        editor.putString("caregivers", stringCaregivers);
        editor.putString("problems", stringProblems);
        editor.putString("records", stringRecords);
        editor.putString("photos", stringPhotos);
        editor.putString("availableIDs", stringAvailableIDs);
        editor.apply();


    }

    /**
     *  Loads back data when the application starts, it does this by finding the String objects
     *  and converts the string objects back to its regular type and then it will load the data
     *  back to its containers.
     */
    public void loadData(){
        Gson gson = new Gson();
        String stringPatients = sharedPreferences.getString("patients", null);
        String stringCaregivers = sharedPreferences.getString("caregivers", null);
        String stringProblems = sharedPreferences.getString("problems", null);
        String stringRecords = sharedPreferences.getString("records", null);
        String stringPhotos = sharedPreferences.getString("photos", null);
        String stringAvailableIDs = sharedPreferences.getString("availableIDs", null);


        Type typeString = new TypeToken<HashMap<String, JSONObject>>(){}.getType();
        Type typeInt = new TypeToken<HashMap<Integer, JSONObject>>(){}.getType();
        Type typePhoto = new TypeToken<HashMap<Integer, Photo>>(){}.getType();
        Type typeArray = new TypeToken<ArrayList<Integer>>(){}.getType();



        HashMap<String, JSONObject> Patients = gson.fromJson(stringPatients,typeString);
        HashMap<String, JSONObject> Caregivers = gson.fromJson(stringCaregivers ,typeString);
        HashMap<Integer, JSONObject> Problems = gson.fromJson(stringProblems,typeInt);
        HashMap<Integer, JSONObject> Records = gson.fromJson(stringRecords,typeInt);
        HashMap<Integer, Photo> Photos = gson.fromJson(stringPhotos,typePhoto);
        ArrayList<Integer> AvailableIDs = gson.fromJson(stringAvailableIDs,typeArray);


        dataManager.setPatients(Patients);
        dataManager.setCaregivers(Caregivers);
        dataManager.setProblems(Problems);
        dataManager.setRecords(Records);
        dataManager.setPhotos(Photos);
        dataManager.setAvailableIDs(AvailableIDs);


    }


}
