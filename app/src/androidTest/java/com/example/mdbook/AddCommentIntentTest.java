package com.example.mdbook;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.test.rule.ActivityTestRule;
import android.view.Display;
import android.widget.EditText;
import com.robotium.solo.Solo;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class AddCommentIntentTest {
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
    public void testAddComment()
    {
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etUserID), "testcare2");
        solo.clickOnView(solo.getView(R.id.loginButton));
        solo.assertCurrentActivity("Wrong Activity", ListPatientActivity.class);
        solo.clickOnScreen(1300,2500);
        solo.assertCurrentActivity("Wrong Activity", AddPatientActivity.class);
        solo.enterText((EditText) solo.getView(R.id.enterUserText), "testpatient103");
        solo.clickOnView(solo.getView(R.id.addPatientBtn));
        solo.assertCurrentActivity("Wrong Activity", ListPatientActivity.class);
        solo.clickOnScreen(750,500);
        solo.assertCurrentActivity("Wrong Activity", ListPatientProblemActivity.class);
        solo.clickOnScreen(750,500);
        solo.assertCurrentActivity("Wrong Activity", ListRecordsCGActivity.class);
        FloatingActionButton addComment = (FloatingActionButton) solo.getView(R.id.fab);
        solo.clickOnView(addComment);
        solo.assertCurrentActivity("Wrong Activity", AddCommentActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etAddComment), "comment");
        solo.clickOnView(solo.getView(R.id.addCommentBtn));
        solo.assertCurrentActivity("Wrong Activity", ListRecordsCGActivity.class);
        solo.goBack();
        solo.goBack();
        solo.clickOnScreen(100,175);
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        um.logout();
//        solo.setNavigationDrawer(Solo.OPENED);
//        solo.clickOnScreen(300,1100);
//        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
    }

}
