/*
 * UserDecomposer
 *
 * Version 1.0.0
 *
 * 2018-12-02
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import android.accounts.NetworkErrorException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Tools for converting user objects to and from JSONObjects.
 * Data Structure:
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
 *
 * @author Noah Burghardt
 * @see User
 * @version 1.0.0
 */
public class UserDecomposer {

    /**
     * Converts user object to decomposition. Relies on ElasticsearchController to provide IDs for
     * objects before converting them to JSON.
     * @see UserDecomposer.Decomposition
     * @see User
     * @param user The user (Patient or Caregiver) to be deconstructed.
     * @return The decomposition of the user.
     * @throws NetworkErrorException Thrown if no internet access is available.
     */
    public Decomposition decompose(User user) throws NetworkErrorException {
        Decomposition decomposition = new Decomposition(user.getUserID());
        ElasticsearchController elasticsearchController = ElasticsearchController.getController();

        /* Ensure internet connection is available before decomposing users, in order to generate
         * ID values.
         */
        if (!elasticsearchController.isConnected()){
            throw new NetworkErrorException();
        }

        try {
            /* Build user json */
            JSONObject userJSON = new JSONObject();
            userJSON.put("phone", user.getPhoneNumber());
            userJSON.put("email", user.getEmail());

            if (user.getClass() == Patient.class) {

                /* Create array for problem IDs */
                ArrayList<String> problemIDs = new ArrayList<>();

                /* Create hashmap stubs */
                HashMap<String, JSONObject> problems = new HashMap<>();
                HashMap<String, JSONObject> records = new HashMap<>();
                HashMap<String, Photo> photos = new HashMap<>();

                for (Problem problem : ((Patient) user).getProblems()) {

                    /* Assign an ID to new problems */
                    if (problem.getProblemID().equals("-1")) {
                        problem.setProblemID(elasticsearchController.generateID());
                    }

                    /* Add problem ID to userJSON */
                    problemIDs.add(problem.getProblemID());

                    /* Add problem data to problemJSON */
                    JSONObject problemJSON = new JSONObject();
                    problemJSON.put("title", problem.getTitle());
                    problemJSON.put("description", problem.getDescription());
                    problemJSON.put("comments", problem.getComments());

                    /* Set up list for recordIDS */
                    ArrayList<String> recordIDs = new ArrayList<>();

                    for (Record record : problem.getRecords()) {
                        /* Assign an ID to new records */
                        if (record.getRecordID().equals("-1")) {
                            record.setRecordID(elasticsearchController.generateID());
                        }

                        /* Add record ID to problemJSON */
                        recordIDs.add(record.getRecordID());

                        /* Add record data to recordJSON */
                        JSONObject recordJSON = new JSONObject();

                        recordJSON.put("date", record.getDate());
                        recordJSON.put("title", record.getTitle());
                        recordJSON.put("description", record.getDescription());
                        recordJSON.put("geoLocation", record.getLocation());
                        recordJSON.put("bodyLocation", record.getBodyLocation());
                        recordJSON.put("comment", record.getComment());

                        /* Set up list for photoIDs */
                        ArrayList<String> photoIDs = new ArrayList<>();

                        for (Photo photo : record.getPhotos()) {
                            /* Assign an ID to new photos */
                            if (photo.getPhotoid() == "-1") {
                                photo.setPhotoid(elasticsearchController.generateID());
                            }

                            /*Add photo ID to recordJSON */
                            photoIDs.add(photo.getPhotoid());

                            /* Add photo to hashmap */
                            photos.put(photo.getPhotoid(), photo);
                        }

                        /* Add photo ids to recordJSON */
                        recordJSON.put("photos", photoIDs);

                        /* Add recordJSON to hashmap */
                        records.put(record.getRecordID(), recordJSON);
                    }

                    /* Add record ids to problemJSON */
                    problemJSON.put("records", recordIDs);

                    /* Add problemJSON to hashmap */
                    problems.put(problem.getProblemID(), problemJSON);
                }

                /* save hashmaps */
                decomposition.setProblems(problems);
                decomposition.setRecords(records);
                decomposition.setPhotos(photos);

                /* set userJSON problemID list */
                userJSON.put("problems", problemIDs);

            } else {
                userJSON.put("patients", ((Caregiver) user).getPatientList());
                /* make other hashmaps null */
                decomposition.setProblems(null);
                decomposition.setPhotos(null);
                decomposition.setRecords(null);
            }

            /* Save userJSON */
            decomposition.setUser(userJSON);

            return decomposition;
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Builds user object from decomposition.
     * @see UserDecomposer.Decomposition
     * @see User
     * @param decomposition The decomposition to be turned into a user.
     * @return The user built from the decomposition.
     */
    public User compose(Decomposition decomposition){
        try {
            if (decomposition.getProblems() != null) {

                /* User is a patient */
                JSONObject patientJSON = decomposition.getUser();
                String phone = patientJSON.getString("phone");
                String email = patientJSON.getString("email");
                Patient patient = new Patient(decomposition.getUserid(), phone, email);

                /* Get problem method also loads in records, photos, etc */
                for (String problemID : decomposition.getProblems().keySet()) {
                    JSONObject problemJSON = decomposition.getProblems().get(problemID);

                    String title = problemJSON.getString("title");
                    String description = problemJSON.getString("description");

                    Problem problem = new Problem(title, description);
                    problem.setProblemID(problemID);

                    /* Add comments */
                    for (String comment : (ArrayList<String>) problemJSON.get("comments")) {
                        problem.addComment(comment);
                    }

                    /* Add records */
                    for (String recordID : (ArrayList<String>) problemJSON.get("records")) {

                        JSONObject recordJSON = decomposition.getRecords().get(recordID);

                        /* Fetch data */
                        String recordTitle = recordJSON.getString("title");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date recordDate = sdf.parse(recordJSON.getString("date"));
                        String recordDescription = recordJSON.getString("description");
                        String comment = recordJSON.getString("comment");

                        Record record = new Record(recordTitle, recordDate, recordDescription);

                        record.setComment(comment);
                        record.setRecordID(recordID);

                        // TODO
                        if (recordJSON.has("geoLocation")) {
                            //GeoLocation geoLocation = (GeoLocation) recordJSON.get("geoLocation");
                            GeoLocation geoLocation = new GeoLocation();
                            record.setGeoLocation(geoLocation);
                        }

                        // TODO
                        if (recordJSON.has("bodyLocation")) {
                            //BodyLocation bodyLocation = (BodyLocation) recordJSON.get("bodyLocation");
                            BodyLocation bodyLocation = new BodyLocation("");
                            record.setBodyLocation(bodyLocation);
                        }
                        /* Add photos */
                        for (String photoID : (ArrayList<String>) recordJSON.get("photos")) {
                            Photo photo = decomposition.getPhotos().get(photoID);
                            photo.setPhotoid(photoID);
                            record.addPhoto(photo);
                        }

                        problem.addRecord(record);
                    }

                    patient.addProblem(problem);
                }

                return patient;
            } else {
                /* User is a caregiver */
                JSONObject caregiverJSON = decomposition.getUser();

                String phone = caregiverJSON.getString("phone");
                String email = caregiverJSON.getString("email");
                ArrayList<String> patientIDs = (ArrayList<String>) caregiverJSON.get("patients");
                Caregiver caregiver = new Caregiver(decomposition.getUserid(), phone, email);
                caregiver.setPatientList(patientIDs);

                return caregiver;
            }
        } catch (JSONException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * Equivalent of a User object, but with its components converted to JSONObjects and grouped
     * into HashMaps based on their types. Makes it easier to store in Elasticsearch.
     * @see User
     */
    public static class Decomposition {

        private String userid;
        private JSONObject user;
        private HashMap<String, JSONObject> problems;
        private HashMap<String, JSONObject> records;
        private HashMap<String, Photo> photos;

        public Decomposition(String userid){
            this.userid = userid;
            user = new JSONObject();
            problems = new HashMap<>();
            records = new HashMap<>();
            photos = new HashMap<>();
        }

        public JSONObject getUser() {
            return user;
        }

        public void setUser(JSONObject user) {
            this.user = user;
        }

        public HashMap<String, JSONObject> getProblems() {
            return problems;
        }

        public void setProblems(HashMap<String, JSONObject> problems) {
            this.problems = problems;
        }

        public HashMap<String, JSONObject> getRecords() {
            return records;
        }

        public void setRecords(HashMap<String, JSONObject> records) {
            this.records = records;
        }

        public HashMap<String, Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(HashMap<String, Photo> photos) {
            this.photos = photos;
        }

        public String getUserid() {
            return userid;
        }
    }


}


