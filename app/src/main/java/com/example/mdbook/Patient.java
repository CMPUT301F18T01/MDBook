/*
 * Patient
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import java.util.ArrayList;

import io.searchbox.annotations.JestId;

/**
 * Extends User into the patient class. Adds a problem list.
 * Provides methods for adding and removing problems.
 * If a problem needs to be updated, the actual problem object should be updated, not replaced with
 * an updated copy. Hence no updateProblem() method.
 *
 * @author Noah Burghardt
 * @see Problem
 * @see User
 * @version 0.0.1
 **/
class Patient extends User {


    private ArrayList<Problem> problems;

    /**
     * Creates new Patient in same way as superclass User.
     * Generates problems as an empty list.
     * @param userID the unique user ID, as a string. This class does not check for uniqueness.
     * @param userPhone the phone number, as a string
     * @param userEmail the email address, as a string
     */
    public Patient(String userID, String userPhone, String userEmail){
        super(userID, userPhone, userEmail);
        this.problems = new ArrayList<Problem>();
    }

    /**
     * Convenience method to add problem to patient.
     * Can also be done via Patient.getProblems().addProblem(problem)
     * @param problem the problem to be added
     */
    public void addProblem(Problem problem) {
        this.problems.add(problem);
    }

    /**
     * Convenience method to remove problem from patient.
     * Can also be done via Patient.getProblems().removeProblem(problem)
     * @param problem the problem to remove
     */
    public void removeProblem(Problem problem) {
        this.problems.remove(problem);
    }

    /**
     * @return Problem list for this patient.
     */
    public ArrayList<Problem> getProblems() {
        return this.problems;
    }
}
