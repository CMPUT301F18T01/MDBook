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
        try {
            Patient patient = userManager.createPatient("patientID", "test-phone", "testemail");
        } catch (UserIDNotAvailableException e) {
            fail();
        }
        localStorageController.push();
        localStorageController.loadData();
    }

}
