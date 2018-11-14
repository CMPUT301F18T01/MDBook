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

    private ArrayList<Patient> patients;

    /**
     * Creates new Caregiver in same way as superclass User.
     * Generates patients as an empty ArrayList.
     * @param userID the unique user ID, as a string. This class does not check for uniqueness.
     * @param userPhone the phone number, as a string
     * @param userEmail the email address, as a string
     */
    public Caregiver(String userID, String userPhone, String userEmail){
        super(userID, userPhone, userEmail);
        this.patients = new ArrayList<>();
    }

    // adds Patient to caregiver list
    // returns True on successful addition

    /**
     * Adds patient to caregivers patient list.
     * @param patient The patient to add.
     */
    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    /**
     * Removes patient from caregivers patient list. Patient isn't deleted but will be dereferenced
     * if not saved to disk or cloud.
     * @param patient The patient to remove.
     * @return True on successful removal, false if patient couldn't be found.
     */
    public boolean removePatient(Patient patient) {
        if (this.patients.contains(patient)){
            this.patients.remove(patient);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return ArrayList of patients for this caregiver.
     */
    public ArrayList<Patient> getPatients(){
        return this.patients;
    }
}
