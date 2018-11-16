package com.example.mdbook;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

class LocalStorageController {

    private static ElasticsearchController elasticsearchController = null;
    private static LocalStorageController localStorageController = null;
    private DataManager dataManager = DataManager.getDataManager();
    private Gson patients = new Gson();
    private Gson caregivers = new Gson();
    private Gson problems = new Gson();
    private Gson records = new Gson();
    private Gson photos = new Gson();
    private Gson availableIDs = new Gson();
    private Context view;



    public static LocalStorageController getController() {
        if (localStorageController == null) {
            localStorageController = new LocalStorageController();
        }
        return localStorageController;


    }

    public void push() {
        SharedPreferences sharedPreferences = view.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String stringPatients = patients.toJson(dataManager.getPatients());
        String stringCaregivers = caregivers.toJson(dataManager.getCaregivers());
        String stringProblems = problems.toJson(dataManager.getProblems());
        String stringRecords = records.toJson(dataManager.getRecords());
        String stringPhotos = photos.toJson(dataManager.getPhotos());
        String stringAvailableIDs = availableIDs.toJson(dataManager.getAvailableID());
        editor.putString("patients", stringPatients);
        editor.putString("caregivers", stringCaregivers);
        editor.putString("problems", stringProblems);
        editor.putString("records", stringRecords);
        editor.putString("photos", stringPhotos);
        editor.putString("availableIDs", stringAvailableIDs);
        editor.apply();


    }

    public void loadData(){
        SharedPreferences sharedPreferences = view.getSharedPreferences("shared preferences",MODE_PRIVATE);
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



        HashMap<String, JSONObject> Patients = gson.fromJson(stringPatients,typeString);
        HashMap<String, JSONObject> Caregivers = gson.fromJson(stringCaregivers ,typeString);
        HashMap<Integer, JSONObject> Problems = gson.fromJson(stringProblems,typeInt);
        HashMap<Integer, JSONObject> Records = gson.fromJson(stringRecords,typeInt);
        HashMap<Integer, Photo> Photos = gson.fromJson(stringPhotos,typePhoto);
        //HashMap<String, JSONObject> AvailableIDs = gson.fromJson(stringAvailableIDs,type);


        dataManager.setPatients(Patients);
        dataManager.setCaregivers(Caregivers);
        dataManager.setProblems(Problems);
        dataManager.setRecords(Records);
        dataManager.setPhotos(Photos);
        //dataManager.setAvailableIDs(AvailableIDs);


    }


}
