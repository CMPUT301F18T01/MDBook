package com.example.mdbook;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Deconstructs user for saving to elasticsearch.
 *
 */
public class UserDecomposer {
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


    public Decomposition decompose(User user){
        Decomposition decomposition = new Decomposition();
        ElasticsearchController elasticsearchController = ElasticsearchController.getController();
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
                    if (problem.getProblemID() == "-1") {
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
                        if (record.getRecordID() == "-1") {
                            record.setRecordID(elasticsearchController.generateID());
                        }

                        /* Add record ID to problemJSON */
                        problemIDs.add(problem.getProblemID());

                        /* Add record data to recordJSON */
                        JSONObject recordJSON = new JSONObject();

                        recordJSON.put("date", record.getDate());
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
                /* make otherhashmaps null */
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

    public class Decomposition {

        private JSONObject user;
        private HashMap<String, JSONObject> problems;
        private HashMap<String, JSONObject> records;
        private HashMap<String, Photo> photos;

        private Decomposition(){
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
    }


}


