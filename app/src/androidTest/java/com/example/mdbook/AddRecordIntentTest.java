package com.example.mdbook;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;
import com.robotium.solo.Solo;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AddRecordIntentTest
{
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
    public void testAddRecord() {
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.clickOnView(solo.getView(R.id.registerBtn));
        solo.assertCurrentActivity("Wrong Activity", RegisterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserIDR), "rajkapadia101");
        solo.enterText((EditText) solo.getView(R.id.etEmail), "rajkapadia@test.com");
        solo.enterText((EditText) solo.getView(R.id.etPhoneNumber), "0000000000");
        solo.clickOnView(solo.getView(R.id.registerButton));
        solo.goBackToActivity("LoginActivity");
        solo.enterText((EditText) solo.getView(R.id.etUserID), "rajkapadia101");
        solo.clickOnView(solo.getView(R.id.loginButton));
        solo.assertCurrentActivity("Wrong Activity", ListProblemActivity.class);

        solo.clickOnScreen(750,500);
        solo.assertCurrentActivity("Wrong Activity", OptionsMenuActivity.class);
        solo.clickOnScreen(750, 950);

        solo.clickOnScreen(1300,2500);
        solo.assertCurrentActivity("Wrong Activity", AddRecordActivity.class);

        solo.enterText((EditText) solo.getView(R.id.headline), "headline 1");
        solo.enterText((EditText) solo.getView(R.id.description), "Problem 1 description");
        solo.clickOnView(solo.getView(R.id.done));
        solo.assertCurrentActivity("Wrong Activity", ListRecordActivity.class);
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        um.logout();
    }

}
