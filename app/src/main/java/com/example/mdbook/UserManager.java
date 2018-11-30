/*
 * UserManager
 *
 * Version 2.0.0
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import android.accounts.NetworkErrorException;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Provides a singleton interface for managing users, user data and user login / logout.
 * Also provides interface for creating and deleting users. Activities should use userManager
 * instead of going directly through the database controller or the Patient / Caregiver classes.
 * On login, the logged in user object is to be interacted with through the UserController.
 * Relies on DataManager for data lookup and management.
 *
 * Based (partially) off the StudentListManager in the student-picker app by Abram Hindle
 * https://github.com/abramhindle/student-picker
 *
 * @author Noah Burghardt
 * @see User
 * @see UserController
 * @see ElasticsearchController
 * @version 2.0.0
 **/
public class UserManager {

    static private UserManager userManager = null;
    private DataManager dataManager;
    private ElasticsearchController elasticsearchController;
    private UserController userController;

    /**
     * Initialize singleton instance.
     */
    public static void initManager() {
        if (userManager == null){
            userManager = new UserManager();
            userManager.dataManager = DataManager.getDataManager();
            userManager.elasticsearchController = ElasticsearchController.getController();
            userManager.userController = UserController.getController();
        }
    }

    /**
     * Auto initilaizes if not already done
     * @return Singleton instance of self.
     */
    public static UserManager getManager() {
        if(userManager == null){
            // throw new IllegalStateException("UserManager has not been initialized!");
            initManager();
        }
        return userManager;
    }

    /**
     * Create new patient profile and save.
     * @param userID The unique ID of the new patient. Must be unique.
     * @param userPhone The phone number of the new patient.
     * @param userEmail The email of the new patient.
     * @throws UserIDNotAvailableException Thrown if the userID is not unique
     * @throws IllegalArgumentException Thrown if the userID is less than 8 characters
     * @throws NetworkErrorException Thrown if there were any network issues creating the user
     * (e.g no internet)
     */
    public void createPatient(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException, IllegalArgumentException, NetworkErrorException {

        /* Ensure userID is unique */
        if (elasticsearchController.existsUser(userID)) {
            throw new UserIDNotAvailableException();
        }

        /* Ensure userID is long enough */
        if (userID.length() < 8) {
            throw new IllegalArgumentException();
        } else {

            /* Create JSON representation */
            JSONObject data = new JSONObject();
            try {
                data.put("phone", userPhone);
                data.put("email", userEmail);
                data.put("problems", new ArrayList<Integer>());

                /* Push to elastic search */
                elasticsearchController.addPatient(userID, data);

            } catch (JSONException e) {
                throw new IllegalArgumentException("Unable to parse data into JSON object", e);
            }
        }
    }

    /**
     * Create new caregiver profile and save.
     * @param userID The unique ID of the new caregiver. Must be unique.
     * @param userPhone The phone number of the new caregiver.
     * @param userEmail The email of the new caregiver.
     * @throws UserIDNotAvailableException Thrown if the userID is not unique
     * @throws IllegalArgumentException Thrown if the userID is less than 8 characters
     * @throws NetworkErrorException Thrown if there were any network issues creating the user
     */
    public void createCaregiver(String userID, String userPhone, String userEmail)
            throws UserIDNotAvailableException, IllegalArgumentException, NetworkErrorException {

        /* Ensure userID is unique */
        if (elasticsearchController.existsUser(userID)){
            throw new UserIDNotAvailableException();
        }

        /* Ensure userID is long enough */
        if (userID.length() < 8){
            throw new IllegalArgumentException();
        }

        else {

            /* Create JSON representation */
            JSONObject data = new JSONObject();
            try {
                data.put("phone", userPhone);
                data.put("email", userEmail);
                data.put("problems", new ArrayList<Integer>());

                /* Push to elastic search */
                elasticsearchController.addCaregiver(userID, data);

            } catch (JSONException e) {
                throw new IllegalArgumentException("Unable to parse data into JSON object", e);
            }
        }
    }

    /**
     * Verify user exists and login credentials are valid.
     * Loads user into UserController on success.
     * @param userid The userID of the user trying to log in.
     * @return Returns true on successful login, false if credentials are invalid,
     * user doesn't exist or there is already someone logged in.
     */
    public boolean login(String userid) throws NetworkErrorException {

        UserController userController = UserController.getController();

        if (userController.getUser() != null){
            return false;
        }
        try {
            User user = this.fetchUser(userid);
            userController.loadUser(user);
            return true;
        } catch (NoSuchUserException e){
            return false;
        }
    }

    /**
     * Clears all data out of UserController.
     */
    public void logout() {
        UserController.getController().clearUser();
    }

    /**
     * Builds full user object for the given userID from database. Returns said user object.
     * Does not load into UserController
     * @param userID The userID of the user to load.
     * @return A patient or caregiver object built from the userID.
     * @throws NoSuchUserException Thrown if there is no user with the given userID in the database.
     */
    public User fetchUser (String userID) throws NoSuchUserException {
        //HashMap<String, JSONObject> patients = dataManager.getPatients();
        //HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();

        /* Check if userID corresponds with a patient */
        if (patients.containsKey(userID)){
            try {
                /* load basic data into patient */
                JSONObject patientJSON = patients.get(userID);
                String phone = patientJSON.getString("phone");
                String email = patientJSON.getString("email");
                ArrayList<Integer> problemIDs = (ArrayList<Integer>) patientJSON.get("problems");
                Patient patient = new Patient(userID, phone, email);

                /* Get problem method also loads in records, photos, etc */
                for (int problemID : problemIDs){
                    patient.addProblem(getProblem(problemID));
                }

                return patient;

            } catch (JSONException e){
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException("The user data is corrupt", e);
            }
        }

        /* Check if userID corresponds with a caregiver */
        else if (caregivers.containsKey(userID)){
            try {
                /* load basic data into caregiver */
                JSONObject caregiverJSON = caregivers.get(userID);
                String phone = caregiverJSON.getString("phone");
                String email = caregiverJSON.getString("email");
                ArrayList<String> patientIDs = (ArrayList<String>) caregiverJSON.get("patients");
                Caregiver caregiver = new Caregiver(userID, phone, email);
                caregiver.setPatientList(patientIDs);
                return caregiver;

            } catch (JSONException e){
                e.printStackTrace();
                throw new RuntimeException("User attributes are corrupt.", e);
            }
        }

        else {
            throw new NoSuchUserException();
        }
    }

    /**
     * Builds user object containing only userID, phone and email. Returns said user object.
     * Does not load into UserController. Good for showing an overview of a User.
     * @param userID The ID of the user to fetch.
     * @return A patient or caregiver object with only the contact information stored.
     * @throws NoSuchUserException Thrown if the userID couldn't be found.
     */
    public ContactUser fetchUserContact (String userID) throws NoSuchUserException {
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();
        JSONObject userJSON;

        if (caregivers.containsKey(userID)) {
            userJSON = caregivers.get(userID);
        } else if (patients.containsKey(userID)){
            userJSON = patients.get(userID);
        }
        else {
            throw new NoSuchUserException();
        }

        try {
            return new ContactUser(userID,
                    userJSON.getString("phone"),
                    userJSON.getString("email"));
        } catch (JSONException e) {
            throw new RuntimeException("User attributes are corrupt.", e);
        }
    }

    /**
     * Take the data in the given user object, convert its components into the corresponding
     * hashmaps and send them to the esc to be uploaded.
     *
     * @param user The user object to be synced into the database.
     * @throws NoSuchUserException Thrown if there is no user with a matching userID already in the
     * database. Shouldn't happen if users are created through the UserManager.
     * @throws IllegalArgumentException Thrown if the inputted user is not a (full) patient or
     * caregiver, e.g. a ContactUser.
     */
    public void saveUser(User user) throws NoSuchUserException, IllegalArgumentException {
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();

        /* Check to make sure user exists */
        if (!patients.containsKey(user.getUserID()) && !caregivers.containsKey(user.getUserID())){
            throw new NoSuchUserException();
        }

        if (user.getClass() == Patient.class) {
            /* Update contact info */
            JSONObject patientJSON = patients.get(user.getUserID());
            try {
                patientJSON.put("phone", user.getPhoneNumber());
                patientJSON.put("email", user.getEmail());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            /* Update problems, update/add/remove records transitively */
            /* Get pre-existing problemID list from user object */
            ArrayList<Integer> problemIDList = new ArrayList<>();
            for (Problem problem : ((Patient) user).getProblems()){
                if (problem.getProblemID() != -1) {
                    problemIDList.add(problem.getProblemID());
                }
            }

            /* Remove problems not present in user */
            try {
                for (int problemID : (ArrayList<Integer>) patientJSON.get("problems")){
                    if (!problemIDList.contains(problemID)){
                        deleteProblem(problemID);
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException("Patient problem data is corrupt", e);
            }

            /* Update new and already existing problems */
            for (Problem problem : ((Patient) user).getProblems()){
                int problemID = setProblem(problem);

                /* Add new problemIDs to problemID list */
                if (!problemIDList.contains(problemID)) {
                    problemIDList.add(problemID);
                }
            }

            /* Update stored problemID list */
            try {
                patientJSON.put("problems", problemIDList);
            } catch (JSONException e) {
                throw new RuntimeException("User problem ID list is corrupt", e);
            }

            patients.put(user.getUserID(), patientJSON);
        }

        else if (user.getClass() == Caregiver.class){
            /* Update contact info */
            JSONObject caregiverJSON = caregivers.get(user.getUserID());
            try {
                caregiverJSON.put("phone", user.getPhoneNumber());
                caregiverJSON.put("email", user.getEmail());

                /* Update patientID list */
                caregiverJSON.put("patients", ((Caregiver) user).getPatientList());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        else{
            throw new IllegalArgumentException("User is not a patient or a Caregiver!");
        }

    }

    /**
     * Delete all data belonging to the user associated with the given userID from the database.
     * Does not delete patients of caregiver when caregiver is deleted.
     * @param userID The userID of the user to be cleared out.
     */
    public void deleteUser(String userID) throws NoSuchUserException {
        HashMap<String, JSONObject> patients = dataManager.getPatients();
        HashMap<String, JSONObject> caregivers = dataManager.getCaregivers();

        /* Check to make sure user exists */
        if (patients.containsKey(userID)){
            /* grab problemIDs */
            ArrayList<Integer> problemIDs = null;
            try {
                problemIDs = (ArrayList<Integer>) patients.get(userID).get("problems");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            /* delete problems */
            for (int problemID : problemIDs){
                deleteProblem(problemID);
            }
            /* delete patient */
            patients.remove(userID);

        } else if (caregivers.containsKey(userID)){
            caregivers.remove(userID);
        } else {
            throw new NoSuchUserException();
        }
    }

    /**
     * Assistant method for retrieving full problem objects
     * @param problemID The problem ID number
     * @return A fully filled out problem object
     */
    private Problem getProblem(int problemID) throws InvalidKeyException {
        HashMap<Integer, JSONObject> problems = dataManager.getProblems();
        try {
            if (problems.containsKey(problemID)) {
                JSONObject problemJSON = problems.get(problemID);
                String title = problemJSON.getString("title");
                String description = problemJSON.getString("description");
                Problem problem = new Problem(title, description);
                problem.setProblemID(problemID);

                /* Add comments */
                for (String comment : (ArrayList<String>) problemJSON.get("comments")){
                    problem.addComment(comment);
                }

                /* Add records */
                for (int recordID : (ArrayList<Integer>) problemJSON.get("records")){
                    problem.addRecord(this.getRecord(recordID));
                }

                return problem;
            } else {
                throw new InvalidKeyException("Problem does not exist!");
            }
        } catch (JSONException e){
            throw new RuntimeException("User data is corrupt.", e);
        }

    }

    /**
     * Assistant method for retrieving full record objects
     * @param recordID The record ID number
     * @return A fully filled out problem object
     */
    private Record getRecord(int recordID) throws InvalidKeyException{
        HashMap<Integer, JSONObject> records = dataManager.getRecords();
        HashMap<Integer, Photo> photos = dataManager.getPhotos();
        try {
            if (records.containsKey(recordID)){
                JSONObject recordJSON = records.get(recordID);
                /* Fetch data */
                String title = recordJSON.getString("title");
                Date date = (Date) recordJSON.get("date");
                String description = recordJSON.getString("description");
                String comment = recordJSON.getString("comment");

                Record record = new Record(title, date, description);

                record.setComment(comment);
                record.setRecordID(recordID);
                if (recordJSON.has("geoLocation")){
                    GeoLocation geoLocation = (GeoLocation) recordJSON.get("geoLocation");
                    record.setGeoLocation(geoLocation);
                }
                if (recordJSON.has("bodyLocation")) {
                    BodyLocation bodyLocation = (BodyLocation) recordJSON.get("bodyLocation");
                    record.setBodyLocation(bodyLocation);
                }
                /* Add photos */
                for (int photoID : (ArrayList<Integer>) recordJSON.get("photos")){
                    Photo photo = photos.get(photoID);
                    photo.setPhotoid(photoID);
                    record.addPhoto(photo);
                }

                return record;
            }
            else{
                throw new InvalidKeyException("Record does not exist!");
            }
        } catch (JSONException e){
            throw new RuntimeException("User record data is corrupt.", e);
        }
    }

    /**
     * Assistant method to saveUser method. Saves Problem data, also calls method to save
     * corresponding record data.
     *
     * @param problem The problem object to be uploaded to the database.
     * @return Returns problemID already owned by problem or generated from the DataManager for new
     * problems.
     */
    private int setProblem(Problem problem){
        HashMap<Integer, JSONObject> problems = dataManager.getProblems();

        /* Build problem JSON */
        JSONObject problemJSON = new JSONObject();
        try {
            problemJSON.put("title", problem.getTitle());
            problemJSON.put("description", problem.getDescription());
            problemJSON.put("comments", problem.getComments());
            problemJSON.put("records", new ArrayList<Integer>());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        /* Generate problemID if needed */
        if (problem.getProblemID() == -1){
            problem.setProblemID(dataManager.generateID());
            /* Problem is new, add empty recordID list */
        }

        /* Update records */
        /* Get pre-existing recordID list from problem object */
        ArrayList<Integer> recordIDList = new ArrayList<>();
        for (Record record : problem.getRecords()){
            if (record.getRecordID() != -1) {
                recordIDList.add(record.getRecordID());
            }
        }

        /* Remove records not present in problem */
        try {
            for (int recordID : (ArrayList<Integer>) problemJSON.get("records")){
                if (!recordIDList.contains(recordID)){
                    deleteRecord(recordID);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException("Problem record data is corrupt", e);
        }

        /* Update new and already existing records */
        for (Record record : problem.getRecords()){
            int recordID = setRecord(record);

            /* Add new recordIDs to recordID list */
            if (!recordIDList.contains(recordID)){
                recordIDList.add(recordID);
            }
        }

        /* Update stored recordID list */
        try {
            problemJSON.put("records", recordIDList);
        } catch (JSONException e) {
            throw new RuntimeException("Problem recordID list is corrupt", e);
        }

        /* Save problem and return problemID */
        problems.put(problem.getProblemID(), problemJSON);
        return problem.getProblemID();
    }

    /**
     * Assistant method to setProblem and saveUser methods. Saves record data.
     * @param record The record object to be saved. If it doesn't have an ID already (new record),
     *               one will be generated.
     * @return The ID of the record, either the one it already has or a new one generated by the
     * DataManager.
     */
    private int setRecord(Record record){
        HashMap<Integer, JSONObject> records = dataManager.getRecords();
        HashMap<Integer, Photo> photos = dataManager.getPhotos();
        /* Build record JSON */
        JSONObject recordJSON = new JSONObject();
        try {
            recordJSON.put("title", record.getTitle());
            recordJSON.put("date", record.getDate());
            recordJSON.put("description", record.getDescription());
            recordJSON.put("geoLocation", record.getLocation());
            recordJSON.put("bodyLocation", record.getBodyLocation());
            recordJSON.put("comment", record.getComment());
            recordJSON.put("photos", new ArrayList<Integer>());
        } catch (JSONException e){
            throw new RuntimeException(e);
        }

        /* Generate recordID if needed */
        if (record.getRecordID() == -1){
            record.setRecordID(dataManager.generateID());
        }

        /* Update photos */
        /* Generate pre-existing photoID list from record object */
        ArrayList<Integer> photoIDs = new ArrayList<>();
        for (Photo photo : record.getPhotos()){
            if (photo.getPhotoid() != -1) {
                photoIDs.add(photo.getPhotoid());
            }
        }
        /* Remove photos not present in record object */
        try {
            for (int photoID : (ArrayList<Integer>) recordJSON.get("photos")) {
                if (!photoIDs.contains(photoID)) {
                    photos.remove(photoID);
                }
            }

            /* Update new and already existing photos */
            for (Photo photo : record.getPhotos()) {
                int photoID = photo.getPhotoid();
                if (photoID == -1) {
                    photoID = dataManager.generateID();
                    photo.setPhotoid(photoID);
                    photoIDs.add(photoID);
                }
                photos.put(photoID, photo);
            }

            /* Update photoID list in record object */
            recordJSON.put("photos", photoIDs);
        } catch (JSONException e) {
            throw new RuntimeException("Record photoID list is corrupt", e);
        }

        records.put(record.getRecordID(), recordJSON);
        return record.getRecordID();
    }

    /**
     * Deletes the problem data and associated records and photos from storage.
     * @param problemID The ID of the problem to delete.
     */
    private void deleteProblem(int problemID){
        HashMap<Integer, JSONObject>  problems = dataManager.getProblems();
        if (problems.containsKey(problemID)){
            try {
                /* remove records */
                for (Integer recordID : (ArrayList<Integer>) problems.get(problemID).get("records")) {
                    deleteRecord(recordID);
                }
                /* delete problem */
                problems.remove(problemID);
                /* make problemID available again */
                dataManager.addAvailableID(problemID);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Deletes the record data and associated photos from storage.
     * @param recordID The ID of the record to delete.
     */
    private void deleteRecord(int recordID){
        HashMap<Integer, JSONObject> records = dataManager.getRecords();
        HashMap<Integer, Photo> photos = dataManager.getPhotos();
        if (records.containsKey(recordID)){
            try {
                ArrayList<Integer> photoIDs = (ArrayList<Integer>) records.get(recordID).get("photos");
                /* Remove photos */
                for (int photoID : photoIDs){
                    photos.remove(photoID);
                    /* make photoID available again */
                    dataManager.addAvailableID(photoID);
                }

                /* delete record object */
                records.remove(recordID);
                /* make recordID available again */
                dataManager.addAvailableID(recordID);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
