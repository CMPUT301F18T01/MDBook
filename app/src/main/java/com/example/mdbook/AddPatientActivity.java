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

import com.google.android.gms.vision.L;

import java.util.ArrayList;

/**
 * Creates an activity for the user to add patients
 * Displays a list of all the patients already added
 *
 * @see com.example.mdbook.Patient
 *
 *
 * @author Raj Kapadia
 *
 * @throws NoSuchUserException
 *
 *
 * @version 0.0.1
 */

public class AddPatientActivity extends AppCompatActivity  {

    private Button addPatientBtn;
    private Button cancelBtn;
    private Button readQRBtn;
    private EditText enterIDText;
    private User user;
    private ArrayList<String> patientList;
    private Caregiver caregiver;
    private String patientID;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();
        caregiver = (Caregiver) UserController.getController().getUser();
        patientList = caregiver.getPatientList();

        // set the view and butons appropriately by id's
        enterIDText = findViewById(R.id.enterUserText);
        addPatientBtn = findViewById(R.id.addPatientBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        readQRBtn = findViewById(R.id.readQR);

        TAG = getIntent().getExtras().getString("TAG");
        if(TAG.equals("getQR"))
        {
            patientID = getIntent().getExtras().getString("userID");
            enterIDText.setText(patientID);
        }
        else
        {
            patientID = "";
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(AddPatientActivity.this, ListPatientActivity.class);
                startActivity(back);
                AddPatientActivity.this.finish();
            }
        });


        addPatientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    patientID = enterIDText.getText().toString();
                    user =  userManager.fetchUser(patientID);
                    if(user.getClass() == Caregiver.class)
                    {
                        Toast.makeText(AddPatientActivity.this, "No patient exists with id: " + patientID, Toast.LENGTH_LONG).show();
                    }
                    else{

                        if(patientList.contains(patientID))
                        {
                            Toast.makeText(AddPatientActivity.this, "Patient already exists", Toast.LENGTH_LONG).show();
                        }
                        else{
                            caregiver.addPatient((Patient) user);
                            userManager.saveUser(caregiver);
                        }

                    }

                } catch (NoSuchUserException e) {
                    Toast.makeText(AddPatientActivity.this, "No user exists with id: " +enterIDText.getText().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (NetworkErrorException e) {
                    Toast.makeText(AddPatientActivity.this, "Internet connection is required to add patient", Toast.LENGTH_SHORT).show();
                }
                goBackToListPatient();
            }
        });

        readQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goReadQR();
            }
        });
    }


    /**
     * Takes user back to list of patients the caregiver can see. Passes neccessary Extras to
     * ListPatientActivity
     *
     * @see ListPatientActivity
     * @result The caregiver can see the added comment in the recyclerview.
     */
    public void goBackToListPatient()
    {
       Intent back = new Intent(AddPatientActivity.this, ListPatientActivity.class);
       back.putExtra("patientID", patientID);
       back.putExtra("TAG", 1);
       startActivity(back);
       this.finish();
    }

    public void goReadQR()
    {
        Intent scanQRIntent = new Intent(AddPatientActivity.this, ScanQRActivity.class);
        scanQRIntent.putExtra("TAG", 1);
        startActivity(scanQRIntent);
        Toast.makeText(AddPatientActivity.this, "Go scan QR code", Toast.LENGTH_LONG).show();
    }
}
