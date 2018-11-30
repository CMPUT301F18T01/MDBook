package com.example.mdbook;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Holds data for currently logged in user
 *
 * @author Noah Burghardt
 * @version 0.0.1
 **/
public class DataManager {
    /**
     * PatientID:
     *      "phone": String
     *      "email": String
     *      "problems": ArrayList of problemIDs (ints)
     * CaregiverID:
     *      "phone": String
     *      "email": String
     *      "patients": ArrayList of patient userIDs (strings)
     * ProblemID:
     *      "title": String
     *      "description": String
     *      "comments": ArrayList of comments (strings)
     *      "records": ArrayList of recordIDs (ints)
     * RecordID:
     *      "title": String
     *      "date": Date
     *      "description": String
     *      "geoLocation": GeoLocation
     *      "bodyLocation": BodyLocation
     *      "photos": ArrayList of photoIDs (ints)
     *      "comment": String
     * PhotoID: Photo
     *
     *
     */
    private User me;
    private ArrayList<User> pushQueue;


    private static DataManager dataManager = null;
    private LocalStorageController localStorageController;

    /**
     * @return Singleton instance of DataManager
     */
    public static DataManager getDataManager() {
        if (dataManager == null){
            dataManager = new DataManager();
            dataManager.localStorageController = LocalStorageController.getController();
            dataManager.setPushQueue(LocalStorageController.getController().loadQueue());
            //dataManager.setMe(LocalStorageController.loadMe());
        }
        return dataManager;
    }

    // Queues user up for upload
    public void addToQueue(User user){
        pushQueue.add(user);
        localStorageController.saveQueue(pushQueue);
    }

    public void setPushQueue(ArrayList<User> pushQueue) {
        this.pushQueue = pushQueue;
    }

    public void removeFromQueue(User user){
        pushQueue.remove(user);
        localStorageController.saveQueue(pushQueue);
    }

    public void saveMe(User user){
        me = user;
        localStorageController.saveMe(me);
    }

    public void removeMe(){
        me = null;
        localStorageController.clearMe();
    }
}
