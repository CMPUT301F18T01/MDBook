package com.example.mdbook;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

public class LocalStorageControllerTest extends TestCase {


    public void testLocalStorage() {
        Context context = InstrumentationRegistry.getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences",
                context.MODE_PRIVATE);
        LocalStorageController.init(sharedPreferences);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        LocalStorageController localStorageController = LocalStorageController.getController();

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
