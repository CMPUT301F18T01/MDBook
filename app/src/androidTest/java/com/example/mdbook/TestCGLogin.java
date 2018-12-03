package com.example.mdbook;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class TestCGLogin {

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
    public void testLogin()
    {
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
        UserController.getController().clearUser();

    }

    @Test
    public void testEnd() throws Exception{
        UserController.getController().clearUser();
    }

}
