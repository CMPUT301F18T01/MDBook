package com.example.mdbook;


import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;


public class LoginActivityIntentTest {

    private Solo solo;


    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule= new ActivityTestRule(LoginActivity.class);



    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
    }


    @Test
    public void testStart() throws Exception {
        Activity activity = activityTestRule.getActivity();
    }


    @Test
    public void testPatientLogin(){
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.registerBtn));
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "rajkapadia12345");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "rajkapadia@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "0000000000");
        solo.clickOnView(solo.getView(R.id.registerButton));
        solo.goBackToActivity("LoginActivity");
        solo.enterText((EditText) solo.getView(R.id.etUserID), "rajkapadia12345");
        solo.clickOnView(solo.getView(R.id.loginButton));
        solo.assertCurrentActivity("Wrong Activity", ListProblemActivity.class);
        swipeToRight();
        solo.setNavigationDrawer(Solo.OPENED);
        solo.clickOnMenuItem("Log out");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }

    @Test
    public void careProviderLogin(){
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
        solo.goBackToActivity("LoginActivity");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.registerBtn));

        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "rajkapadiaCG1");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "rajkapadia@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "0000000000");
        CheckBox checkBox = (CheckBox) solo.getView(R.id.cgCheckBox);
        solo.clickOnView(checkBox);
        solo.clickOnView(solo.getView(R.id.registerButton));

        solo.goBackToActivity("LoginActivity");
        solo.enterText((EditText) solo.getView(R.id.etUserID), "rajkapadiaCG1");
        solo.clickOnView(solo.getView(R.id.loginButton));
        solo.assertCurrentActivity("Wrong Activity", ListPatientActivity.class);
        swipeToRight();
        solo.setNavigationDrawer(Solo.OPENED);
        solo.clickOnMenuItem("Log out");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);

    }

    private void swipeToRight() {
        Display display = solo.getCurrentActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        float xStart = 0 ;
        float xEnd = width / 2;
        solo.drag(xStart, xEnd, height / 2, height / 2, 1);
    }



}