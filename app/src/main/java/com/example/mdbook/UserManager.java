package com.example.mdbook;


public class UserManager {

    static private UserManager userManager = null;
    private ElasticsearchController esc;


    // create proper singleton of self
    public static void initManager() {
        if (userManager == null){
            userManager = new UserManager();
        }
    }

    // return singleton of self
    public static UserManager getManager() {
        if(userManager == null){
            throw new IllegalStateException("UserManager has not been initialized!");
        }
        return userManager;
    }

    // creates usermanager object
    private UserManager(){
        this.esc = ElasticsearchController.getController();
    }

    // create new patient
    public void createPatient(String userID, String userPhone, String userEmail){

    }
    // create new Caregiver
    public void createCaregiver(String userID, String userPhone, String userEmail){

    }


    // deconstruct user and add through elasticsearchcontroller
    public void addUser(Patient u) {
    }

    // verify user exists through elasticsearchcontroller
    // attempt login
    // load user into usercontroller on success
    // return true on success
    public boolean login(String userid) {
        return(true);
    }

    // clear out data in usercontroller
    public void logout() {

    }

    // load data of user into new User object, return User object
    public User fetchUser (String Userid) {
        return(new Patient("userID","userPhone", "userEmail"));
    }

    // deconstruct user and update through elasticsearchcontroller
    public void saveUser(User user) {

    }

    // deconstruct user and remove all associated data through elasticsearchcontroller
    public void deleteUser(Patient u) {
    }
}
