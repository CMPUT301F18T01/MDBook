/*
 * Caregiver
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import java.util.ArrayList;

/**
 * Extends User into the caregiver class. Adds an arraylist of patients.
 * Provides methods for adding and removing patients.
 *
 * @author Noah Burghardt
 * @see Patient
 * @see User
 * @version 0.0.1
 **/
class Caregiver extends User{

    private ArrayList<String> patientList;

    /**
     * Creates new Caregiver in same way as superclass User.
     * Generates patientList as an empty ArrayList of strings (patient IDs)
     * @param userID the unique user ID, as a string. This class does not check for uniqueness.
     * @param userPhone the phone number, as a string
     * @param userEmail the email address, as a string
     */
    public Caregiver(String userID, String userPhone, String userEmail){
        super(userID, userPhone, userEmail);
        this.patientList = new ArrayList<>();
    }
    
    /**
     * Adds patient to caregivers patient list.
     * @param patient The patient to add.
     */
    public void addPatient(Patient patient) {
        this.patientList.add(patient.getUserID());
    }

    /**
     * Removes patient from caregivers patient list. Patient isn't deleted but will be dereferenced
     * if not saved to disk or cloud.
     * @param patient The patient to remove.
     * @return True on successful removal, false if patient couldn't be found.
     */
    public boolean removePatient(Patient patient) {
        if (this.patientList.contains(patient.getUserID())){
            this.patientList.remove(patient.getUserID());
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return ArrayList of patientIDs for this caregiver.
     */
    public ArrayList<String> getPatients(){
        return this.patientList;
    }
}
