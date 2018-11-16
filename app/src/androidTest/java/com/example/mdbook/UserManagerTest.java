package com.example.mdbook;
// test functionalities of usermanager and elastisearch controller


import junit.framework.TestCase;


public class UserManagerTest extends TestCase {

    // verify that UserManager only instantiates one instance of itself
    public void testSingleton(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        UserManager.initManager();
        UserManager um2 = UserManager.getManager();
        // check if the returned UserManager is the exact same object, by reference
        assert(um == um2);
    }

    /**
     * Test creating a patient
     */
    public void testLoadSaveDeleteUser(){

        UserManager.initManager();
        UserManager um = UserManager.getManager();
        // create new profile
        try {
            um.createPatient("patientid", "userphone", "useremail@test.com");
        } catch (UserIDNotAvailableException e){
            assert false;
        }

        // load profile
        try {
            Patient patient = (Patient) um.fetchUser("patientid");
            assertEquals("userphone", patient.getPhoneNumber());


            // modify and save profile
            patient.setPhoneNumber("newphone");
            um.saveUser(patient);

            // check changes were loaded
            patient = (Patient) um.fetchUser("patientid");
            assertEquals("newphone", patient.getPhoneNumber());

            // delete profile
            um.deleteUser(patient.getUserID());

            // check to make sure patient doesn't exist
            assertFalse(um.login("patientid"));
        } catch (NoSuchUserException e){
            assert false;
        }
    }


    /**
     * test for expected output when failing to login.
     * test to make sure data isn't loaded into usercontroller upon failed login
     */
    public void testLoginFail(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        try {
            um.createPatient("patientid", "userphone", "user@email.com");
            // test that login fails
            assertFalse(um.login("patientid_"));
            // test that data is not loaded into usercontroller
            UserController userController = UserController.getController();
            User u = userController.getUser();
            assertNotSame("patientid", u.getUserID());
            assertNotSame("patientid_", u.getUserID());
        } catch (UserIDNotAvailableException e){
            assert false;
        }
    }

    /**
     * test to make sure user data is loaded into user object upon login
     */
    public void testUserLogin(){
        // add 2 users
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        try {
            um.createPatient("patientid", "userphone", "useremail@test.com");
            um.createPatient("patientid2", "userphone", "useremail2@test.com");

            UserController userController = UserController.getController();

            // test logging in to both accounts
            um.login("patientid");
            assertEquals(userController.getUser().getUserID(), "patientid");
            um.logout();
            um.login("patientid2");
            assertEquals(userController.getUser().getUserID(), "patientid2");
            um.logout();
        } catch (UserIDNotAvailableException e){
            assert false;
        }

    }

    /**
     * test to make sure user data is removed from user object upon logout
     */
    public void testUserLogout(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        UserController userController = UserController.getController();
        try {
            um.createPatient("patientid", "userphone", "useremail@test.com");
            um.login("patientid");
            Patient patient1 = (Patient) userController.getUser();

            // make sure login was successful
            assertEquals(patient1.getUserID(), "patientid");
            um.logout();
            // make sure usercontroller is wiped on logout
            assertNull(userController.getUser());
        } catch (UserIDNotAvailableException e){
            assert false;
        }
    }

    /**
     * Test to make sure login cannot be completed without logging out first.
     */
    public void testInvalidLogin(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        try {
            um.createPatient("patientid", "userphone", "useremail@test.com");
            um.createPatient("patientid1", "userphone1", "useremail1@test.com");
            // verify successful login
            assertTrue(um.login("patientid"));
            // attempt logging in without logging out first
            assertFalse(um.login("patientid"));
            assertFalse(um.login("patientid1"));
            // ensure first user is still the one logged in
            assertEquals("patientid", UserController.getController().getUser().getUserID());
        } catch (UserIDNotAvailableException e) {
            assert false;
        }
    }

    /**
     * Test fetching of contact information for user.
     */
    public void testContactUser(){
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        try{
            String userID = "userid";
            String userPhone = "userPhone";
            String userEmail = "userEmail";
            userManager.createPatient(userID, userPhone, userEmail);


        } catch (UserIDNotAvailableException e) {
            assert false;
        }
    }

    /**
     * Test creating a user with a taken userID
     */
    public void testTakenUser(){
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
    }
}
