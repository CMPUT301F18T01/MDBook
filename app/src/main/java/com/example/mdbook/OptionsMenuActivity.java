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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.Serializable;

public class OptionsMenuActivity extends AppCompatActivity {

    Button EditProblem;
    Button EditRecord;
    Button RecordSlide;
    Button GeoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);

        // set the view and butons appropriately by id's
        EditProblem = findViewById(R.id.EditProblem);
        EditRecord = findViewById(R.id.EditRecord);
        RecordSlide = findViewById(R.id.RecordSlide);
        GeoLocation = findViewById(R.id.GeoLocation);

//        Intent intent = new Intent();
//        Problem problem = (Problem) getIntent().getExtras().getSerializable("problem");
//        Toast.makeText(OptionsMenuActivity.this, problem.getTitle(), Toast.LENGTH_SHORT).show();

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

    }
    /**
     * Creates a new intent for switching to the EditProblemDetailsActivity
     */
    public void GoEditProblem(){
        Intent goEditProblem = new Intent(this, EditProblemDetailsActivity.class);
       // Intent intent = new Intent();
        Problem problem = (Problem) getIntent().getExtras().getSerializable("problem");
//        Toast.makeText(OptionsMenuActivity.this, problem.getTitle(), Toast.LENGTH_SHORT).show();
        goEditProblem.putExtra("problem", problem);
        startActivity(goEditProblem);
    }
    /**
     * Creates a new intent for switching to the ViewRecordActivity
     */
    public void GoEditRecord(){
        Intent goEditRecord = new Intent(this, ViewRecordActivity.class);
        Problem problem = (Problem) getIntent().getExtras().getSerializable("problem");
        int position = getIntent().getExtras().getInt("position");
        goEditRecord.putExtra("problem", problem);
        goEditRecord.putExtra("position", position);
        startActivity(goEditRecord);
    }
    /**
     * Creates a new intent for switching to the PatientSlideActivity
     */
    public void RecordSlide(){
        Intent ViewRecordSlide = new Intent(this, PatientSlideActivity.class);
        startActivity(ViewRecordSlide);
    }

    /**
     * Creates a new intent for switching to the ViewLocationActivity
     */
    public void GeoLocation(){
        Intent ViewLocationActivity = new Intent(this, ViewLocationActivity.class);
        startActivity(ViewLocationActivity);
    }
}
