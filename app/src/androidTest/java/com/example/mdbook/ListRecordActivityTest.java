package com.example.mdbook;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class ListRecordActivityTest {

    @Rule
    public ActivityTestRule<ListRecordActivity> listRecordActivityActivityTestRule = new ActivityTestRule<>(ListRecordActivity.class);
    private ListRecordActivity listRecordActivity= null;

    @Before
    public void setUp() throws Exception {
       listRecordActivity = listRecordActivityActivityTestRule.getActivity();
    }

    @Test
    public void testAddRecord() throws Exception {
        RecyclerView rv = listRecordActivity.findViewById(R.id.recordRecyclerView);
        assertNotNull(rv);
    }


    @After
    public void tearDown() throws Exception {
        listRecordActivity = null;
    }
}