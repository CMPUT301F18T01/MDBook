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

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creates an activity that lists the Patients Problems for the care giver.
 *
 *
 * @see com.example.mdbook.Patient
 * @see com.example.mdbook.Problem
 *
 * @author Raj Kapadia
 * @author James Aina
 *
 * @version 0.0.1
 */
public class ListPatientProblemActivity extends AppCompatActivity
{

    private TextView headerPatientProblem;
    private ListView patientProblemsContainer;

    private ArrayAdapter<String> problemListAdapter;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_patient_problem);


        headerPatientProblem = (TextView)findViewById(R.id.headerPatientProblems);

        patientProblemsContainer = (ListView)findViewById(R.id.listPatientProblems);

        String [] problems = new String[]{"Problem 1", "Problem 2", "Problem 3", "Problem 4"};
        ArrayList<String> problemList = new ArrayList<String>();
        problemList.addAll(Arrays.asList(problems));


        problemListAdapter = new ArrayAdapter<String>(this, R.layout.simple_list, problemList);
        patientProblemsContainer.setAdapter(problemListAdapter);

        patientProblemsContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //spot is the item clicked
                Intent showRecords = new Intent(ListPatientProblemActivity.this, CaretakerViewRecordsActivity.class);
                startActivity(showRecords);
            }
        });

        Intent intent = getIntent();
        String nameOfPatient = intent.getExtras().getString("nameOfPatient");
        headerPatientProblem.setText("List of problems for: " + nameOfPatient);



    }

}
