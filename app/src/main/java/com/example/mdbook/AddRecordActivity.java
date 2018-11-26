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

import android.content.Intent;
//import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 *
 * @version 0.0.1
 */

public class AddRecordActivity extends AppCompatActivity {
    // Initialize all the required imageViews ans Buttons
    ImageView image;
    EditText headline;
    EditText date;
    EditText Description;
    Button geo;
    Button body;
    Button reminder;
    Button save;
    Button cancel;
    Record record;
    Date recordDate;

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
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        UserManager.initManager();
        final  UserManager userManager = UserManager.getManager();

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
                Problem problem = (Problem)getIntent().getExtras().getSerializable("problem");
                Patient patient = (Patient) UserController.getController().getUser();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    recordDate =  formatter.parse(date.getText().toString());
                    record = new Record(headline.getText().toString(), recordDate,Description.getText().toString());
                    problem.addRecord(record);
                } catch (ParseException e) {
                    Toast.makeText(AddRecordActivity.this, "WRONG DATE FORMAT", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                try {
                    userManager.saveUser(patient);
                    Toast.makeText(AddRecordActivity.this, "Save new record: " + record.getTitle(), Toast.LENGTH_SHORT).show();
                } catch (NoSuchUserException e) {
                    Toast.makeText(AddRecordActivity.this, "user doesn't exist", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
                BackToAddProblem();
                //Go back to patient main page
                //BackToAddProblem();
            }
        });

        // Switches to AddProblemsActivity upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToAddProblem();
            }
        });

        // Switches to AddProblemsActivity upon the click of the geolocstion button
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
        this.finish();
    }

    /**
     * Creates a new intent for switch to the ListProblemActivity
     */
    public void BackToAddProblem(){
        Intent mainPage = new Intent(this, ViewRecordActivity.class);
//        Record record = new Record(headline.getText().toString(), date.getText().toString(), Description.getText().toString());
        Problem problem = (Problem)getIntent().getExtras().getSerializable("problem");
        mainPage.putExtra("problem", problem);
        startActivity(mainPage);
        this.finish();
    }
    /**
     * Creates a new intent for switch to the ViewLocationActivity
     */
    public void openGeoLoc(){
        Intent geoLoc = new Intent(this, ViewLocationActivity.class);
        startActivity(geoLoc);
        this.finish();
    }


    public void goAddBodyLoc(View view) {
    }
}
