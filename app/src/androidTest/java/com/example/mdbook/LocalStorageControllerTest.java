package com.example.mdbook;


import android.content.Context;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

public class LocalStorageControllerTest extends TestCase {


    public void testLocalStorage() {
        Context context = InstrumentationRegistry.getContext();
        LocalStorageController localStorageController = new LocalStorageController();
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        localStorageController.setContext(context);
        Patient patient = null;
        try {
            patient = userManager.createPatient("patientID", "test-phone", "testemail");
            userManager.saveUser(patient);
        } catch (UserIDNotAvailableException e) {
            fail();
        } catch (NoSuchUserException e) {
            fail();
        }
        localStorageController.push();
        try {
            userManager.deleteUser("patientID");
        } catch (NoSuchUserException e) {
            fail();
        }
        localStorageController.loadData();
        try {
            assert (userManager.fetchUser("patientID") == patient);
        } catch (NoSuchUserException e) {
            fail();
        }
    }

}
