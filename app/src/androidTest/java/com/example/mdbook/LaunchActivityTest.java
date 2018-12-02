package com.example.mdbook;
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



public class LaunchActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> loginActivityActivityTestRule = new ActivityTestRule<>(LoginActivity.class);
    private LoginActivity LoginActivity = null;
    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);
    private RegisterActivity RegisterActivity = null;
    @Rule
    public ActivityTestRule<ListProblemActivity> listProblemActivityTestRule = new ActivityTestRule<>(ListProblemActivity.class);
    private ListProblemActivity ListProblemActivity = null;
    @Rule
    public ActivityTestRule<AddProblemActivity> addProblemActivityActivityTestRule = new ActivityTestRule<>(AddProblemActivity.class);
    private AddProblemActivity AddProblemActivity = null;
    @Rule
    public ActivityTestRule<ViewAccountDetailActivity> viewAccountDetailActivityActivityTestRule = new ActivityTestRule<>(ViewAccountDetailActivity.class);
    private ViewAccountDetailActivity ViewAccountDetailActivity = null;
    @Rule
    public ActivityTestRule<EditAccountDetailActivity> editAccountDetailsActivityActivityTestRule = new ActivityTestRule<>(EditAccountDetailActivity.class);
    private EditAccountDetailActivity EditAccountDetailsActivity = null;




    @Before
    public void setUp() throws Exception {
        LoginActivity = loginActivityActivityTestRule.getActivity();
        RegisterActivity = registerActivityActivityTestRule.getActivity();
        ListProblemActivity = listProblemActivityTestRule.getActivity();
        AddProblemActivity = addProblemActivityActivityTestRule.getActivity();
        ViewAccountDetailActivity = viewAccountDetailActivityActivityTestRule.getActivity();
        EditAccountDetailsActivity = editAccountDetailsActivityActivityTestRule.getActivity();


    }

    /*
If an element in the activity is not null
that means the activity was launched successfully
*/
    @Test
    public void Launch(){
        EditText userID = LoginActivity.findViewById(R.id.etUserID);
        assertNotNull(userID);
        EditText etPhone = RegisterActivity.findViewById(R.id.etPhoneNumber);
        assertNotNull(etPhone);
        RecyclerView view = ListProblemActivity.findViewById(R.id.recylerView);
        assertNotNull(view);
        EditText edit = AddProblemActivity.findViewById(R.id.addTitle);
        assertNotNull(edit);
        TextView viewName = ViewAccountDetailActivity.findViewById(R.id.viewName);
        assertNotNull(viewName);
        EditText editName = EditAccountDetailsActivity.findViewById(R.id.editName);
        assertNotNull(editName);


    }

    @After
    public void tearDown() throws Exception {
        LoginActivity = null;
        RegisterActivity = null;
        ListProblemActivity = null;
        AddProblemActivity = null;
        ViewAccountDetailActivity = null;
        EditAccountDetailsActivity = null;


    }
}