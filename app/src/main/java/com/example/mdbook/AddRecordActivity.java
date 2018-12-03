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
import android.location.Address;
import android.support.annotation.Nullable;
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
    private static final Integer MAP_ACTIVITY_REQUEST_CODE = 0;
    private static final Integer BODYLOC_ACTIVITY_REQUEST_CODE = 1;

    // Initialize all the required imageViews ans Buttons

    private Intent launchmap;
    private Record record;
    private Address address;
    private Integer problemPos;
    private DateFormat format;
    private Date recordDate;
    private ImageView image;
    private EditText headline;
    private EditText date;
    private EditText Description;
    private Button geo;
    private Button body;
    private Button reminder;
    private Button save;
    private Button cancel;
    private ArrayList<BodyLocation> bodyLocationList;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        // set the view and butons appropriately by id's
        headline = findViewById(R.id.headline);
        date = findViewById(R.id.date);
        Description = findViewById(R.id.description);
        geo = findViewById(R.id.geo);
        body = findViewById(R.id.body);
        reminder = findViewById(R.id.reminder);
        save = findViewById(R.id.done);
        cancel = findViewById(R.id.cancel);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();
        problemPos = getIntent().getExtras().getInt("problemPos");
        final Patient patient = (Patient) UserController.getController().getUser();

        format = new SimpleDateFormat("dd/MM/yy");

        // Switches to addBodyLocationActivity upon the click of the body button
        if(bodyLocationList == null){
            bodyLocationList = new ArrayList<BodyLocation>();
            Toast toast = Toast.makeText(getApplicationContext(), "something", Toast.LENGTH_SHORT);
            toast.show();
        }

        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddBodyLoc();
                //AddRecordActivity.this.finish();
            }
        });


        // Switches to addBodyLocationActivity upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //UserManager.initManager();
                    //UserManager userManager = UserManager.getManager();
                    //Patient patient = (Patient) UserController.getController().getUser();
                    recordDate = format.parse(date.getText().toString());
                    if (record == null) {

                        record = new Record(headline.getText().toString(), recordDate, Description.getText().toString());
                    }
                    if (address != null){
                        record.getLocation().addAddress(address);

                    }

                    patient.getProblems().get(problemPos).addRecord(record);
                    userManager.saveUser(patient);
                    Toast.makeText(AddRecordActivity.this
                            , "Record " + headline.getText().toString() + " Added"
                            , Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();


                } catch (ParseException e) {
                    Toast.makeText(AddRecordActivity.this,"WRONG DATE FORMAT", Toast.LENGTH_SHORT).show();
                }

            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder();
            }
        });
        // Switches to AddProblemsActivity upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG);
                toast.show();
               finish();
            }
        });

        // Switches to AddProblemsActivity upon the click of the geolocation button
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                if (record == null) {
                    try {
                        recordDate = format.parse(date.getText().toString());
                        record = new Record(headline.getText().toString(), recordDate, Description.getText().toString());
                        //patient.getProblems().get(problemPos).addRecord(record);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }*/
                openGeoLoc();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                address = (Address) data.getParcelableExtra("address");
                address.getAddressLine(0);
            }
        }
        else if (requestCode == BODYLOC_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (getIntent().getExtras().getSerializable("bodylocation") != null) {
                    BodyLocation bodylocation = (BodyLocation) getIntent().getExtras().getSerializable("bodylocation");
                    path = bodylocation.getPhoto().getFilepath();
                    bodyLocationList.add(bodylocation);


                    }


                }
            }
        }

//    if(bodyLocationList.size()>0){
//        for(int i = 0; i < bodyLocationList.size(); i++){
//            Photo photo = bodyLocationList.get(i).getPhoto();
//            String path = photo.getFilepath();
//            Toast toast = Toast.makeText(getApplicationContext(), path, Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }

    /**
     * Creates a new intent for switch to the AddBodyLocationActivity
     */
    public void goAddBodyLoc(){
        Intent addRecordPage = new Intent(this, NewBodyLocationView.class);

        startActivityForResult(addRecordPage, BODYLOC_ACTIVITY_REQUEST_CODE);
    }


    /**
     * Creates a new intent for switch to the ViewLocationActivity
     */
    public void openGeoLoc(){
        if (isServicesOK()) {
            launchmap = new Intent(this, MapActivity.class);
            startActivityForResult(launchmap, MAP_ACTIVITY_REQUEST_CODE);
        }
    }
    public void addReminder(){
        Intent reminder= new Intent(this, AddReminderActivity.class);
        startActivity(reminder);
    }

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
