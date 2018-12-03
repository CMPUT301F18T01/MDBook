

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

public class AddCommentActivityTest {
    private Solo solo;
    private EditText editText;
    private Button loginButton;
    private TextView registerText;

    @Rule
    public ActivityTestRule<AddCommentActivity> activityTestRule= new ActivityTestRule(AddCommentActivity.class);


    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
    }


    @Test
    public void testStart() throws Exception {
        Activity activity = activityTestRule.getActivity();
    }

    @Test public void testAddComment()
    {
        solo = new Solo(getInstrumentation(), activityTestRule.getActivity());
        solo.assertCurrentActivity("Wrong Activity", AddCommentActivity.class);
        solo.enterText((EditText) solo.getView(R.id.etAddComment), "Nightmare");
        solo.clickOnView(solo.getView(R.id.addCommentBtn));
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

}
