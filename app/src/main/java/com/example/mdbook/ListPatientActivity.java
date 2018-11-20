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

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 * @author James Aina
 *
 * @version 0.0.1
 */

public class ListPatientActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private FloatingActionButton fabAddPatient;
    private FloatingActionButton fabSearch;
    private Button viewProfile;

    private ListView patientListContainer;
    private ArrayAdapter<String> patientListAdapter;

    private String nameOfPatient;

    private String previousIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient);
        
        viewProfile = findViewById(R.id.viewProfileButton);
        viewProfile.setOnClickListener(this);

        fabAddPatient = (FloatingActionButton) findViewById(R.id.fabAddPatient);
        fabAddPatient.setOnClickListener(this);

        fabSearch = (FloatingActionButton) findViewById(R.id.fabSearchPatient);
        fabSearch.setOnClickListener(this);

        patientListContainer = (ListView) findViewById(R.id.listPatientContainer);
        patientListContainer.setOnItemClickListener(this);
        patientListContainer.setOnItemLongClickListener(this);

        String[] patients = new String[]{"John Doe", "Jane Doe", "Some Random Guy", "Some Random Girl",
                "Some Random Baby"};
        Intent getPreviousIntent = getIntent();
        previousIntent = getPreviousIntent.getExtras().getString("activity");

        if (previousIntent.equals("AddPatientActivity")) {
            Intent getUserFromAdd = getIntent();
            previousIntent = getUserFromAdd.getExtras().getString("userID");
            patients[patients.length - 1] = previousIntent;
        }

        List<String> patientList = new ArrayList<>();
        patientList.addAll(Arrays.asList(patients));


        patientListAdapter = new ArrayAdapter<>(this, R.layout.simple_list, patientList);
        patientListAdapter.notifyDataSetChanged();

        patientListContainer.setAdapter(patientListAdapter);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddPatient:
                Intent addPatientActivity = new Intent(ListPatientActivity.this, AddPatientActivity.class);
                startActivity(addPatientActivity);
                break;
            case R.id.fabSearchPatient:
                Intent searchPatientActivity = new Intent(ListPatientActivity.this, PatientSearchActivity.class);
                startActivity(searchPatientActivity);
                break;
            case R.id.viewProfileButton:
                Toast.makeText(this, "View profile Activity", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent viewPatientProblemsIntent = new Intent(this, ListPatientProblemActivity.class);
        nameOfPatient = (String) parent.getItemAtPosition(position);
        viewPatientProblemsIntent.putExtra("nameOfPatient", nameOfPatient);
        startActivity(viewPatientProblemsIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }


}
