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


    private static LocalStorageController localStorageController;
    private Gson patients = new Gson();
    private Gson caregivers = new Gson();
    private Gson problems = new Gson();
    private Gson records = new Gson();
    private Gson photos = new Gson();
    private Gson availableIDs = new Gson();
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

    public ArrayList<User> loadQueue() {
        String queueString = sharedPreferences.getString("pushQueue", null);
        Type queueType = new TypeToken<ArrayList<User>>(){}.getType();
        ArrayList<User> pushQueue = gson.fromJson(queueString, queueType);
        if (pushQueue == null){
            return new ArrayList<>();
        }
        return pushQueue;
    }

    public User loadMe(){
        String User = sharedPreferences.getString("user",null);
        Type userType = new TypeToken<User>(){}.getType();
        User returnUser = gson.fromJson(User,userType);
        return returnUser;
    }

    public void saveQueue(ArrayList<User> pushQueue) {
        String queueString = gson.toJson(pushQueue);
        editor.putString("pushQueue", queueString);
        editor.apply();
    }

    public void saveMe(User me) {
        String user = gson.toJson(me);
        editor.putString("user",user);
        editor.apply();
    }

    public void clearMe() {
        editor.remove("user").commit();
    }
}
