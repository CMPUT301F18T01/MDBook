package com.example.mdbook;

class Caregiver extends User{
    // generate a new Caregiver object
    public Caregiver(String userID, String userPhone, String userEmail){
        super(userID, userPhone, userEmail);
    }

    // adds Patient to caregiver list
    // returns True on successful addition
    public boolean addPatient(Patient testPatient) {
        return(true);
    }
}
