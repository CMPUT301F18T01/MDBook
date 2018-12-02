/*
 * AddPatientActivity
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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Creates an activity for the user to add patients
 * Displays a list of all the patients already added
 *
 * @see com.example.mdbook.Patient
 * @see
 *
 * @author Raj Kapadia
 *
 *
 * @version 0.0.1
 */

public class AddPatientActivity extends AppCompatActivity  {

    private Button addPatientBtn;
    private Button cancelBtn;
    private EditText enterIDText;
    private String TAG = "AddPatientActivity";
    private Patient patient;
    private Caregiver caregiver;
    private String patientID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);



        // set the view and butons appropriately by id's
        enterIDText = (EditText)findViewById(R.id.enterUserText);
        addPatientBtn = (Button)findViewById(R.id.addPatientBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddPatientActivity.this, ListPatientActivity.class);
                startActivity(back);
                AddPatientActivity.this.finish();
            }
        });


        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();

        caregiver = (Caregiver) UserController.getController().getUser();



        addPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    patient = (Patient) userManager.fetchUser(enterIDText.getText().toString());
                    patientID = patient.getUserID();
                    caregiver.addPatient(patient);

                } catch (NoSuchUserException e) {
                    Toast.makeText(AddPatientActivity.this, "No user exists with id: " +enterIDText.getText().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (NetworkErrorException e) {
                    Toast.makeText(AddPatientActivity.this, "Internet connection is required to add patient", Toast.LENGTH_SHORT).show();
                }
                goBackToListPatient();

            }
        });
    }



    public void goBackToListPatient()
    {
       Intent back = new Intent(AddPatientActivity.this, ListPatientActivity.class);
       back.putExtra("patientID", patientID);
       back.putExtra("TAG", TAG);
       startActivity(back);
       this.finish();

    }
}
