package com.example.mdbook;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.TextView;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;



public class AddRecordActivityTest {

    @Rule
    public ActivityTestRule<ListProblemActivity> listProblemActivityTestRule = new ActivityTestRule<>(ListProblemActivity.class);
    private ListProblemActivity ListProblemActivity = null;
    @Rule
    public ActivityTestRule<AddProblemActivity> addProblemActivityActivityTestRule = new ActivityTestRule<>(AddProblemActivity.class);
    private AddProblemActivity AddProblemActivity = null;


    @Before
    public void setUp() throws Exception {
        ListProblemActivity = listProblemActivityTestRule.getActivity();
        AddProblemActivity = addProblemActivityActivityTestRule.getActivity();
    }

    /*
If an element in the activity is not null
that means the activity was launched successfully
*/
    @Test
    public void passObject(){
        RecyclerView view = ListProblemActivity.findViewById(R.id.recylerView);
        assertNotNull(view);
        EditText edit = AddProblemActivity.findViewById(R.id.addTitle);
        assertNotNull(edit);


    }

    @After
    public void tearDown() throws Exception {
        ListProblemActivity = null;
        AddProblemActivity = null;
    }
}