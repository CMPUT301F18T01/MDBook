package com.example.mdbook;

import android.app.Activity;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.test.rule.ActivityTestRule;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AddPatientIntentTest {

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
    public void testOpenAddPatientActivity()
    {
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.registerBtn));

        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "VanessaPeng");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "ryp4@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "604604604");
        solo.clickOnView(solo.getView(R.id.registerButton));
        solo.goBackToActivity("LoginActivity");
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.registerBtn));
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "rajkapadiaCG");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "rajkapadia@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "0000000000");
        CheckBox checkBox = (CheckBox) solo.getView(R.id.cgCheckBox);
        solo.clickOnView(checkBox);
        solo.clickOnView(solo.getView(R.id.registerButton));
        solo.goBackToActivity("LoginActivity");
        solo.enterText((EditText) solo.getView(R.id.etUserID), "rajkapadiaCG");
        solo.clickOnView(solo.getView(R.id.loginButton));
        solo.assertCurrentActivity("Wrong Activity", ListPatientActivity.class);
        solo.clickOnScreen(1300,2500);
        solo.assertCurrentActivity("Wrong Activity", AddPatientActivity.class);
        solo.enterText((EditText) solo.getView(R.id.enterUserText), "VanessaPeng");
        solo.clickOnView(solo.getView(R.id.addPatientBtn));
        solo.assertCurrentActivity("Wrong Activity", ListPatientActivity.class);
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        um.logout();
    }


}
