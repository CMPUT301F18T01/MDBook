/*
 * LocalStorageController
 *
 * Version 2.0.0
 *
 * 2018-12-02
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Provide a singleton interface for managing local data storage.
 *
 *
 *
 * @author Thomas Chan
 * @author Noah Burghardt
 * @see DataManager
 * @version 2.0.0
 */

class LocalStorageController {


    private static LocalStorageController localStorageController;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private SharedPreferences.Editor editor;


    /**
     * @param sharedPreferences Sets the context in the local storage controller
     */
    public static void init(SharedPreferences sharedPreferences){
        if (localStorageController == null) {
            localStorageController = new LocalStorageController();
        }
        localStorageController.sharedPreferences = sharedPreferences;
        localStorageController.gson = new Gson();
        localStorageController.editor = sharedPreferences.edit();
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
     * Loads the local data for the logged in user.
     * @return User object of logged in user.
     */
    public User loadMe(){
        String userTypeString = sharedPreferences.getString("usertype", null);
        Type userTypeStringType = new TypeToken<String>(){}.getType();
        String userTypeStringString = gson.fromJson(userTypeString, userTypeStringType);

        if (userTypeStringString == null){
            return null;
        }

        switch (userTypeStringString) {
            case "patient": {
                String User = sharedPreferences.getString("user", null);
                Type userType = new TypeToken<Patient>() {
                }.getType();
                return gson.fromJson(User, userType);
            }
            case "caregiver": {
                String User = sharedPreferences.getString("user", null);
                Type userType = new TypeToken<Caregiver>() {
                }.getType();
                return gson.fromJson(User, userType);
            }
            default:
                return null;
        }
    }

    /**
     * Saves cached data of currently logged in user to storage.
     * @param me The User data of currently logged in user.
     */
    public void saveMe(User me) {
        String user = gson.toJson(me);
        editor.putString("user",user);
        if (me.getClass() == Patient.class){
            editor.putString("usertype", "patient");
        } else {
            editor.putString("usertype", "caregiver");
        }
        editor.apply();
    }

    /**
     * Saves queue of offline changes to localstorage, for uploading when possible.
     * @param pushQueue Queue of user objects to update.
     */
    public void saveQueue(ArrayList<User> pushQueue) {

        ArrayList<Patient> patientQueue = new ArrayList<>();
        ArrayList<Caregiver> caregiverQueue = new ArrayList<>();
        for (User user : pushQueue){
            if (user.getClass() == Patient.class){
                patientQueue.add((Patient) user);
            }
            else {
                caregiverQueue.add((Caregiver) user);
            }
        }
        String patientQueueString = gson.toJson(patientQueue);
        String caregiverQueueString = gson.toJson(caregiverQueue);
        editor.putString("patientPushQueue", patientQueueString);
        editor.putString("caregiverPushQueue", caregiverQueueString);

    }


    /**
     * Loads queue of offline changes that need to be uploaded when possible.
     * @return Queue of user objects to update.
     */
    public ArrayList<User> loadQueue() {

        String patientQueueString = sharedPreferences.getString("patientPushQueue", null);
        String caregiverQueueString = sharedPreferences.getString("caregiverPushQueue", null);
        Type patientQueueType = new TypeToken<ArrayList<Patient>>(){}.getType();
        Type caregiverQueueType = new TypeToken<ArrayList<Caregiver>>(){}.getType();
        ArrayList<Patient> patientPushQueue = gson.fromJson(patientQueueString, patientQueueType);
        ArrayList<Caregiver> caregiverPushQueue = gson.fromJson(caregiverQueueString, caregiverQueueType);
        ArrayList<User> pushQueue = new ArrayList<>();
        if (patientPushQueue != null){
            pushQueue.addAll(patientPushQueue);
        }
        if (caregiverPushQueue != null) {
            pushQueue.addAll(caregiverPushQueue);
        }
        return pushQueue;
    }

    /**
     * Clears logged in user data out from saved storage. Should be called on logout.
     */
    public void clearMe() {
        editor.remove("user").commit();
        editor.remove("usertype").commit();
    }
}
