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

    // test loading and saving user data via elastisearch
    // depends on elastisearch controller
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
        Patient patient = (Patient) um.fetchUser("patientid");
        assertEquals("userphone", patient.getPhoneNumber());

        // modify and save profile
        patient.setPhoneNumber("newphone");
        um.saveUser(patient);

        // check changes were loaded
        patient = (Patient) um.fetchUser("patientid");
        assertEquals("newphone", patient.getPhoneNumber());

        // delete profile
        um.deleteUser(patient);

        // check to make sure patient doesn't exist
        assertFalse(um.login("patientid"));

    }


    // test for expected output when logging in
    // test to make sure data isn't loaded into user object upon failed login
    public void testLoginFail(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        Patient p = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(p);
        // test that login fails
        assertFalse(um.login("patientid_"));
        // test that data is not loaded into usercontroller
        UserController userController = UserController.getController();
        User u = userController.getUser();
        assertNotSame("patientid", u.getUserID());
        assertNotSame("patientid_", u.getUserID());
    }

    // test to make sure user data is loaded into user object upon login
    // requires use of usercontroller singleton
    public void testUserLogin(){
        // add 2 users
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(patient);
        Patient patient1 = new Patient("patientid2", "userphone", "useremail2@test.com");
        um.addUser(patient1);

        UserController userController = UserController.getController();

        // test logging in to both accounts
        um.login("patientid");
        assertEquals(userController.getUser().getUserID(), "patientid");
        um.logout();
        um.login("patientid2");
        assertEquals(userController.getUser().getUserID(), "patientid2");
        um.logout();

    }

    // test to make sure user data is removed from user object upon logout
    // requires use of usercontroller singleton
    public void testUserLogout(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        UserController userController = UserController.getController();
        Patient patient = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(patient);
        Patient patient1 = (Patient)userController.getUser();
        um.login("patientid");

        // make sure login was successful
        assertEquals(patient1.getUserID(), "patientid");
        um.logout();
        // make sure usercontroller is wiped on logout
        assertNotSame("patientid", userController.getUser().getUserID());
    }

    // test to make sure login cannot be completed before logging out
    // requires use of usercontroller singleton
    //@Test(expected = IllegalStateException.class)
    public void testInvalidLogin(){
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        Patient u = new Patient("patientid", "userphone", "useremail@test.com");
        um.addUser(u);
        // verify successful login
        assertTrue(um.login("patientid"));
        // attempt logging in without logging out first
        um.login("patientid");
    }
}
