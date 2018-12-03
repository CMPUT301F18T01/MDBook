package com.example.mdbook;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class AddProblemActivityTest {

    @Rule
    public ActivityTestRule<AddProblemActivity> addProblemActivityActivityTestRule = new ActivityTestRule<>(AddProblemActivity.class);
    private AddProblemActivity AddProblemActivity = null;

    @Before
    public void setUp() throws Exception {
        AddProblemActivity = addProblemActivityActivityTestRule.getActivity();
    }

    @Test
    public void testAddRecord() throws Exception{
        String UserID;
        UserID = "Testpatient";
        Espresso.onView(withId(R.id.headline)).perform(typeText("Headline"));
        Espresso.onView(withId(R.id.saveButton)).perform(click());
        UserManager.initManager();
        UserManager um = UserManager.getManager();
        um.createPatient("Person", "Phone", "Email");
        Patient p = (Patient) um.fetchUser(UserID);
        assertEquals(p.getProblems().get(0).getRecords().get(0), "Headline");
    }

    @After
    public void tearDown() throws Exception {
        AddProblemActivity = null;
    }
}