/*
 * AddRecordActivity
 *
 * Version 0.0.1
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */


package com.example.mdbook;

import android.app.Dialog;
import android.content.Intent;
//import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Creates an activity for the user to add a record
 * Displays a list of all the problems already added
 *
 * @see com.example.mdbook.Record
 *
 *
 * @author Jayanta Chatterjee
 * @author James Aina
 * @author Raj Kapadia
 *
 * @version 0.0.1
 */

public class AddRecordActivity extends AppCompatActivity {
    private static final String TAG = "AddRecordActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    // Initialize all the required imageViews ans Buttons

    private ArrayList<Record> recordList;
    private Integer problemPos;
    private Date recordDate;
    private ImageView image;
    private EditText headline;
    private EditText date;
    private EditText Description;
    private Button geo;
    private Button body;
    private Button save;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        // set the view and butons appropriately by id's
        headline = findViewById(R.id.headline);

        Description = findViewById(R.id.description);
        geo = findViewById(R.id.geo);
        body = findViewById(R.id.body);
        image = findViewById(R.id.addImage);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();
        recordList = new ArrayList<>();
        final Patient patient = (Patient) UserController.getController().getUser();
        problemPos = getIntent().getExtras().getInt("problemPos");


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddRecordActivity.this, "Add photo", Toast.LENGTH_LONG).show();
            }
        });
        // Switches to addBodyLocationActivity upon the click of the body button
        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddBodyLoc();
            }
        });

        // Switches to addBodyLocationActivity upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDate = new Date();
                Record record = new Record(headline.getText().toString(),recordDate,Description.getText().toString());
                patient.getProblems().get(problemPos).addRecord(record);
                userManager.saveUser(patient);
                Toast.makeText(AddRecordActivity.this
                        ,"Record " + headline.getText().toString() + " Added"
                        ,Toast.LENGTH_SHORT).show();
                endActivity();

            }
        });

        // Switches to AddProblemsActivity upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               endActivity();
            }
        });

        // Switches to AddProblemsActivity upon the click of the geolocation button
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGeoLoc();
            }
        });


    }

    /**
     * Creates a new intent for switch to the AddBodyLocationActivity
     */
    public void goAddBodyLoc(){
        Intent addRecordPage = new Intent(this, NewBodyLocationView.class);
        startActivity(addRecordPage);
    }

    /**
     * Creates a new intent for switch to the ListProblemActivity
     */
    public void endActivity(){
        this.finish();
    }
    /**
     * Creates a new intent for switch to the ViewLocationActivity
     */
    public void openGeoLoc(){
        Intent launchmap= new Intent(this, MapActivity.class);
        startActivity(launchmap);
    }


    /**
     * Checks to see if Google Services are working fine for proper map functionality
     * @return boolean value
     */
    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AddRecordActivity.this);
        if(available == ConnectionResult.SUCCESS){
            //Everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Error occured but is fixable
            Log.d(TAG,"isServicesOK: an error has occured but is fixable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(AddRecordActivity
                    .this,available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}
