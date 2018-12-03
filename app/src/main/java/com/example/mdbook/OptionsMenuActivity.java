/*
 * OptionsMenuActivity
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


import java.util.ArrayList;

/**
 * Creates activity that shows user things they can do when clicked on a particular proble,
 * @see Problem
 *
 * @author ...
 */

public class OptionsMenuActivity extends AppCompatActivity {

    private static final String TAG = "OptionsMenuActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private Button EditProblem;
    private Button EditRecord;
    private Button RecordSlide;
    private Button GeoLocation;
    private Button setReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);

        // set the view and butons appropriately by id's
        EditProblem = findViewById(R.id.EditProblem);
        EditRecord = findViewById(R.id.EditRecord);
        RecordSlide = findViewById(R.id.RecordSlide);
        GeoLocation = findViewById(R.id.GeoLocation);
        setReminder = findViewById(R.id.setRemindersBtn);

        // Switches to the edit problem activity upon the click of the edit problem button
        EditProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoEditProblem();
            }
        });
        // Switches to the edit record activity upon the click of the edit record button
        EditRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoEditRecord();
            }
        });
        // Switches to the record slide activity upon the click of the edit record slide button
        RecordSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordSlide();
            }
        });
        // Switches to the geolocation activity upon the click of the edit geolocation button
        GeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeoLocation();
            }
        });

        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setReminder();
            }
        });

    }
    /**
     * Creates a new intent for switching to the EditProblemDetailsActivity
     */
    public void GoEditProblem(){
        Intent goEditProblem = new Intent(this, EditProblemDetailsActivity.class);
        goEditProblem.putExtra("problemPos", getIntent().getExtras().getInt("problemPos"));
        startActivity(goEditProblem);
    }
    /**
     * Creates a new intent for switching to the ViewRecordActivity
     */
    public void GoEditRecord(){
        Intent goEditProblem = new Intent(this, ListRecordActivity.class);
        goEditProblem.putExtra("problemPos", getIntent().getExtras()
                .getInt("problemPos"));
        startActivity(goEditProblem);
    }
    /**
     * Creates a new intent for switching to the PatientSlideActivity
     */
    public void RecordSlide(){
        Intent ViewRecordSlide = new Intent(this, PatientSlideActivity.class);
        ViewRecordSlide.putExtra("problemPos", getIntent().getExtras()
                .getInt("problemPos"));
        startActivity(ViewRecordSlide);
    }

    /**
     * Creates a new intent for switching to the ViewLocationActivity
     */
    public void GeoLocation(){
        if (isServicesOK()) {
            Intent launchmap = new Intent(this, ViewAllMapActivity.class);
            boolean toggle = false;

            UserManager.initManager();
            UserManager userManager = UserManager.getManager();
            Patient patient = (Patient) UserController.getController().getUser();
            ArrayList<Record> allRecords = patient.getProblems().get( getIntent().getExtras()
                    .getInt("problemPos")).getRecords();
            if (allRecords.size() >0) {
                for (int i =0; i < allRecords.size();i++) {
                    if (allRecords.get(i).getLocation() != null) {
                        if (allRecords.get(i).getLocation().getLat() != null &&
                                allRecords.get(i).getLocation().getLong() != null &&
                                allRecords.get(i).getLocation().getTitle() != null) {
                            toggle = true;
                        }
                    }
                }
                if (toggle) {
                    launchmap.putExtra("problemPos", getIntent().getExtras()
                            .getInt("problemPos"));
                    startActivity(launchmap);
                }
                else{
                    Toast.makeText(this, "No locations for any record", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, "No records", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Changes to AddReminderActivity when set reminder buttons is pressed.
     */
    public  void setReminder()
    {
        Intent intent = new Intent(OptionsMenuActivity.this, AddReminderActivity.class);
        startActivity(intent);
        this.finish();
    }

    /**
     * Checks google services for proper map functionalities
     * @return
     */
    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(OptionsMenuActivity.this);
        if(available == ConnectionResult.SUCCESS){
            //Everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Error occured but is fixable
            Log.d(TAG,"isServicesOK: an error has occured but is fixable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(OptionsMenuActivity
                    .this,available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
