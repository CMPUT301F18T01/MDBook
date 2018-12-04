package com.example.mdbook;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.test.rule.ActivityTestRule;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AddProblemTest
{
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
    public void testAddProblem()
    {
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.registerBtn));

        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "rajkapadia1234");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "rajkapadia@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "0000000000");
        solo.clickOnView(solo.getView(R.id.registerButton));

        solo.goBackToActivity("LoginActivity");
        solo.enterText((EditText) solo.getView(R.id.etUserID), "rajkapadia1234");
        solo.clickOnView(solo.getView(R.id.loginButton));


        solo.assertCurrentActivity("Wrong Activity", ListProblemActivity.class);
        FloatingActionButton floatingActionButton = (FloatingActionButton) solo.getView(R.id.fabAddProblem);
        solo.clickOnView(floatingActionButton);

        solo.assertCurrentActivity("Wrong Activity", AddProblemActivity.class);
        solo.enterText((EditText) solo.getView(R.id.addTitle), "Problem 1");
        solo.enterText((EditText) solo.getView(R.id.addDescription), "Problem 1 description");
        solo.clickOnView(solo.getView(R.id.addDateBtn));
        solo.setDatePicker(0, 2018, 12, 3);
        solo.clickOnText("OK");
        solo.clickOnView(solo.getView(R.id.saveButton));

        solo.assertCurrentActivity("Wrong Activity", ListProblemActivity.class);
    }

    @Test
    public void testEnd() throws Exception{

    }


}
