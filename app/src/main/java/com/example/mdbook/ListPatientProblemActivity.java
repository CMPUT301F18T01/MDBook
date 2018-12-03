/*
 * ListPatientProblemActivity
 *
 * Version 0.0.1
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */


package com.example.mdbook;
import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Creates an activity that lists the Patients Problems for the care giver.
 *
 *
 * @see com.example.mdbook.Patient
 * @see com.example.mdbook.Problem
 *
 * @author Raj Kapadia
 *
 *
 * @version 0.0.1
 */
public class ListPatientProblemActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private ArrayList<Problem> patientProblems;
    private RecyclerView recyclerView;
    private ProblemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;
    private int patientPos;
    private String patientID;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient_problem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        Caregiver caregiver = (Caregiver) UserController.getController().getUser();
        patientPos = getIntent().getExtras().getInt("patientPos");
        patientID = caregiver.getPatientList().get(patientPos);


        try {
            patient = (Patient) userManager.fetchUser(patientID);
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }
        patientProblems = patient.getProblems();

        /* Create recycler view */
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(this);
        mAdapter = new ProblemAdapter(patientProblems);
        recyclerView.setLayoutManager(mLayoutmanager);
        recyclerView.setAdapter(mAdapter);

        /* Opens options menu when problem is clicked */
        mAdapter.setOnItemClickListener(new ProblemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int Position) {
                OptionMenu(Position);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        Intent viewSignout= new Intent(this, LoginActivity.class);
        startActivity(viewSignout);
        this.finish();
    }


    /**
     * Changes to ListRecordsCGActivity, passes the necessary Extras
     * @param position:position of problem in list
     */
    public void OptionMenu(int position){
        Intent intent = new Intent(this, ListRecordsCGActivity.class);
        intent.putExtra("problemPos", position);
        intent.putExtra("patientPos",patientPos);
        startActivity(intent);
    }

}
