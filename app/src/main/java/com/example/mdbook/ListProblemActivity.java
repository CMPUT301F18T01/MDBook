package com.example.mdbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for displaying the users problems when logging in. It displays problems,
 * has a side menu that allows the user to see their profile or sign out and it has a
 * add problem button. Relies on User manager to pass user objects which contain problem
 * objects.
 *
 * @author Thomas Chan
 * @author Vanessa Peng
 * @see Problem
 * @see ProblemAdapter
 * @version 2.0.0

 * **/

public class ListProblemActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ArrayList<Problem> problems;
    private RecyclerView recyclerView;
    private ProblemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;

    /**
     * Initializes the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problem);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        Patient patient = (Patient) UserController.getController().getUser();
        problems = patient.getProblems();

        /* Create recycler view */
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(this);
        mAdapter = new ProblemAdapter(problems);
        recyclerView.setLayoutManager(mLayoutmanager);
        recyclerView.setAdapter(mAdapter);

        /* Opens options menu when problem is clicked */
        mAdapter.setOnItemClickListener(new ProblemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int Position) {
                OptionMenu(Position);
            }
        });

        /* Delete problem when swipe right is activated */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView
                    .ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT){
                    showAlertDialog(viewHolder);
                }

            }
        }).attachToRecyclerView(recyclerView);

        /* Initializes the add problem activity */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProblem();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_problem);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showAlertDialog(final RecyclerView.ViewHolder position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("MDBook");
            alert.setMessage("Are you sure you want to delete this problem?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    problems.remove(position.getAdapterPosition());
                    UserManager.getManager().saveUser(UserController.getController().getUser());
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(ListProblemActivity.this, "Problem Removed", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAdapter.notifyDataSetChanged();
                }
            });
            alert.create().show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        Patient patient = (Patient) UserController.getController().getUser();
        problems = patient.getProblems();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Creates a Search bar menu icon
     * @param menu object that is of menu type
     * @return a boolean to see if the menu was created or not
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.recylerView);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Allows for pressing the back button in android which will go back in the activity
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**
     * @param item item in the menu object
     * @return a boolean to determine if an item has been selected or not
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            goViewProfile();
        } else if (id == R.id.signout) {
            Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show();
            signout();
        }
        else if(id == R.id.shareQR)
        {
            Toast.makeText(this, "Share QR-Code with Caregiver so they can add you", Toast.LENGTH_LONG).show();
            goGenerate();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Starts the add problem activity
     */
    public void addProblem(){
        Intent intent = new Intent(this, AddProblemActivity.class);
        startActivity(intent);
    }
    /**
     * Starts the option menu activity
     */
    public void OptionMenu(int position){
        Intent intent = new Intent(this, OptionsMenuActivity.class);
        intent.putExtra("problemPos",position);
        startActivity(intent);
    }
    /**
     * Starts the view profile activity
     */
    public void goViewProfile(){
        Intent viewProfile = new Intent(this, ViewAccountDetailActivity.class);
        startActivity(viewProfile);
    }
    /**
     * Starts the login activity
     */
    public void signout(){
        UserManager.getManager().logout();
        Intent viewSignout= new Intent(this, LoginActivity.class);
        startActivity(viewSignout);
        this.finish();
    }

    public void goGenerate()
    {
        Intent generateIntent = new Intent(this, GenerateQRActivity.class);
        startActivity(generateIntent);
    }

    /**
     * Gets the patients problems
     * @param user is a Patient object that contains patient problems.
     */
    public void setProblems(Patient user){
        problems = user.getProblems();
    }

}
