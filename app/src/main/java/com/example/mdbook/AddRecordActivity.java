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
                //Todo: Save data into the user manager
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
    }

    /**
     * Creates a new intent for switch to the ListProblemActivity
     */
    public void BackToAddProblem(){
//        Intent mainPage = new Intent(this, ListProblemActivity.class);
//        startActivity(mainPage);
        this.finish();
    }
    /**
     * Creates a new intent for switch to the ViewLocationActivity
     */
    public void openGeoLoc(){
        Intent geoLoc = new Intent(this, ViewLocationActivity.class);
        startActivity(geoLoc);
    }

}
