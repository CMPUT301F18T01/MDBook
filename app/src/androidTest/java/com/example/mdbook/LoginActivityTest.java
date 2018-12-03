package com.example.mdbook;

import android.accounts.NetworkErrorException;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class LoginActivityTest{

    @Rule
    public IntentsTestRule<LoginActivity> loginActivityActivityTestRule = new IntentsTestRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        loginActivityActivityTestRule.getActivity();
        Patient p = new Patient("rajkapadia", "1234567890", "test@test.com");
        UserController.getController().loadUser(p);
        Caregiver c = new Caregiver("rajkapadiaCG", "1234567890", "test@test.com");
        UserController.getController().loadUser(c);

    }

    @Test
    public void login(){
        loginActivityActivityTestRule.getActivity();
        onView(withId(R.id.etUserID))
                .perform(typeText("rajkapadia"), closeSoftKeyboard());
        onView(withId(R.id.loginButton))
                .perform(click());
        intended(IntentMatchers.hasComponent(ListProblemActivity.class.getName()));

    }

}