package com.example.mdbook;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;

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
        String stringAvailableIDS = availableIDs.toJson(dataManager.getAvailableID());
        editor.putString("patients", stringPatients);
        editor.putString("caregivers", stringCaregivers);
        editor.putString("problems", stringProblems);
        editor.putString("records", stringRecords);
        editor.putString("photos", stringPhotos);
        editor.putString("availableIDS", stringAvailableIDS);
        editor.apply();


    }


}
