/*
 * NewBodyLocationActivity
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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Creates an activity that add/view a body location
 *
 *
 * @see com.example.mdbook.Patient
 * @see com.example.mdbook.Problem
 *
 * @author Jayanta Chatterjee
 * @author James Aina
 *
 * @version 0.0.1
 */


public class NewBodyLocationView extends AppCompatActivity {

    ImageView bodyFront;
    ImageView bodyBack;
    Button save;
    Button cancel;
    private static final String DEBUGTAG = "JAYC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_body_location_view);

        // set the views and buttons appropriately by id's
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        //Intent intent = getIntent();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Save data into the user manager
                BackToAddProblem();
                //Go back to patient main page
                //BackToAddProblem();
            }
        });
        // Switches to the add problem upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToAddProblem();
            }
        });

    }

    /**
     * Creates a new intent for switching to the ListProblemActivity
     */
    
    public void BackToAddProblem(){
        Intent mainPage = new Intent(this, ListProblemActivity.class);
        startActivity(mainPage);
    }

}
