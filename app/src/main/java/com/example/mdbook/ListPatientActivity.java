/*
 * ListPatientActivity
 *
 * Version 0.0.1
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Creates an activity that list all the patients for the Caregiver
 *
 *
 * @see com.example.mdbook.Patient
 * @see com.example.mdbook.Caregiver
 *
 *
 * @author Raj Kapadia
 * @author Thomas Chan
 *
 * @version 0.0.1
 */

public class ListPatientActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> patientIDs;
    private RecyclerView recyclerView;
    private PatientAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutmanager;
    private Caregiver caregiver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        caregiver = (Caregiver) UserController.getController().getUser();
        patientIDs = caregiver.getPatientList();



        /* Create recycler view */
        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        mLayoutmanager = new LinearLayoutManager(this);
        mAdapter = new PatientAdapter(patientIDs);
        recyclerView.setLayoutManager(mLayoutmanager);
        recyclerView.setAdapter(mAdapter);



        /* Opens options menu when problem is clicked */
        mAdapter.setOnItemClickListener(new PatientAdapter.OnItemClickListener() {
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
                addPatient();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string
                .navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_patient);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /**
     *
     * @param position:position of item in recyclerview
     */
    public void showAlertDialog(final RecyclerView.ViewHolder position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("MDBook");
        alert.setMessage("Are you sure you want to delete this patient?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                patientIDs.remove(position.getAdapterPosition());
                mAdapter.notifyDataSetChanged();
                Toast.makeText(ListPatientActivity.this, "Patient Removed", Toast.LENGTH_SHORT).show();
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
     * Lets the user change to AddPatientActivity
     */
    public void addPatient(){
        Intent intent = new Intent(this, AddPatientActivity.class);
        intent.putExtra("TAG", "first");
        startActivity(intent);
    }


    /**
     * Lets the user change to ListPatientProblemActivity.class, passes necessary Extras
     * @param position
     */
    public void OptionMenu(int position){
        Intent intent = new Intent(this, ListPatientProblemActivity.class);
        intent.putExtra("patientPos",position);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        SyncController syncController = new SyncController();
        registerReceiver(syncController,filter);
    }
}
