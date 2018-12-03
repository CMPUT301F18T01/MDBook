package com.example.mdbook;


import android.app.Activity;
import android.support.constraint.solver.widgets.ChainHead;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Text;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class LoginActivityTest {

    private Solo solo;
    private EditText editText;
    private Button loginButton;
    private TextView registerText;

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
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnButton("REGISTER HERE");

        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "rajkapadia");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "rajkapadia@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "0000000000");
        solo.clickOnButton("REGISTER");

        solo.goBackToActivity("LoginActivity");
        solo.enterText((EditText) solo.getView(R.id.etUserID), "rajkapadia");
        solo.clickOnButton("LOG IN");

        solo.assertCurrentActivity("Wrong Activity", ListProblemActivity.class);

        solo.goBackToActivity("LoginActivity");

        solo.clickOnButton("REGISTER HERE");

        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "rajkapadiaCG");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "rajkapadia@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "0000000000");
        CheckBox checkBox = (CheckBox) solo.getView(R.id.cgCheckBox);
        solo.clickOnView(checkBox);
        solo.clickOnButton("REGISTER");

        solo.goBackToActivity("LoginActivity");
        solo.enterText((EditText) solo.getView(R.id.etUserID), "rajkapadiaCG");
        solo.clickOnButton("LOG IN");

        solo.assertCurrentActivity("Wrong Activity", ListPatientActivity.class);
    }

    @Test
    public void testCaregiverLogin(){


    }

}