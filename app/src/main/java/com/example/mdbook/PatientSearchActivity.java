/*
 * PatientSearchActivity
 *
 * Version 0.0.1
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */


package com.example.mdbook;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * Creates an activity for the user to view to search their problems
 *
 *
 * @see com.example.mdbook.SearchController
 *
 *
 * @author .....
 * @author James Aina
 *
 * @version 0.0.1
 */


public class PatientSearchActivity extends AppCompatActivity implements View.OnClickListener {

//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FloatingActionButton fabSearchProblemBtn, fabSearchMapBtn, fabSearchUserBtn;
    private EditText etSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search);

//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // set the view and butons appropriately by id's
        fabSearchProblemBtn = (FloatingActionButton)findViewById(R.id.fabSearchProblem);
        fabSearchProblemBtn.setOnClickListener(this);
        fabSearchMapBtn = (FloatingActionButton)findViewById(R.id.fabSearchMap);
        fabSearchMapBtn.setOnClickListener(this);
        fabSearchUserBtn = (FloatingActionButton)findViewById(R.id.fabSearchUser);
        fabSearchUserBtn.setOnClickListener(this);

        etSearchBar = (EditText)findViewById(R.id.etSearchPatient);
        etSearchBar.setOnClickListener(this);

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(actionBarDrawerToggle.onOptionsItemSelected(item))
//        {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    /**
     * Returns a toast message
     * @param v view
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.fabSearchMap:
                Toast.makeText(this, "Searching map for " + etSearchBar.getText(), Toast.LENGTH_LONG).show();
                break;
            case R.id.fabSearchUser:
                Toast.makeText(this, "Search patient " + etSearchBar.getText(), Toast.LENGTH_LONG).show();
                break;
            case R.id.fabSearchProblem:
                Toast.makeText(this, "Searching problem " + etSearchBar.getText(), Toast.LENGTH_LONG).show();
                break;
            case R.id.etSearchPatient:
                etSearchBar.setText("");
        }

    }
}
