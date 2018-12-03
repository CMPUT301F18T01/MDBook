package com.example.mdbook;


import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;




public class LoginActivityTest{

    @Rule
    public IntentsTestRule<LoginActivity> intentsTestRule = new IntentsTestRule<>(LoginActivity.class, false, false);


    @Before
    public void setUp() throws Exception {
        intentsTestRule.launchActivity(new Intent());
    }

    @Test
    public void testloggedIn() {
        Patient p = new Patient("Vanessa", "604", "yp4");
        UserController.getController().loadUser(p);
        onView(withId(R.id.etUserID)).perform(typeText("Vanessa"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etUserID)).perform(click());
        intended(hasComponent((Matcher<ComponentName>) UserController.getController().getUser()));
        UserController.getController().clearUser();
    }

//    @Test
//    public void CareProviderTest() {
//        Caregiver c = new Caregiver("Vanessa", "604", "yp4");
//        UserController.getController().loadUser(c);
//        onView(withId(R.id.etUserID)).perform(typeText("Vanessa"), ViewActions.closeSoftKeyboard());
//        onView(withId(R.id.etUserID)).perform(click());
//        intended(hasComponent(LoginActivity.class.getName()));
//        UserController.getController().clearUser();
//    }

    @After
    public void tearDown() throws Exception {

    }
}