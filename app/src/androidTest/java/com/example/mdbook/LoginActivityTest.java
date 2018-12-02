package com.example.mdbook;

import android.accounts.NetworkErrorException;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginActivityTest{

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private LoginActivity LoginActivity = null;

    @Before
    public void setUp() throws Exception {
        LoginActivity = loginActivityActivityTestRule.getActivity();
    }

    @Test
    public void login(){
        String etUserID;
        etUserID = "Shitake";
        UserManager userManager = UserManager.getManager();
        try {
            if (userManager.login(etUserID)){
                assertNotNull(etUserID);
            }
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() throws Exception {
        LoginActivity = null;
    }
}