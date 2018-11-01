package com.example.mdbook;

class Patient extends User {

    // generate a new Patient object
    public Patient(String userID, String userPhone, String userEmail){
        super(userID, userPhone, userEmail);
    }

    // allow patients to add a problem to their problemList
    public void addProblem(Problem problem) {
    }

    // returns list of problems
    public ProblemList getProblems() {
        return(new ProblemList());
    }
}
