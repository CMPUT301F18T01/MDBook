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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    Button uploadFront;
    Button uploadBack;
    Button save;
    Button cancel;
    int counter;

    private static final String DEBUGTAG = "JAYC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_body_location_view);

        // set the views and buttons appropriately by id's
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        uploadFront = findViewById(R.id.uploadFront);
        uploadBack = findViewById(R.id.uploadBack);
        bodyFront = findViewById(R.id.body_front);
        bodyBack = findViewById(R.id.body_back);

        uploadFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload activity handled here
                counter = 1;
                ChooseUploadMethod();

            }
        });

        uploadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload activity handled here
                counter = 2;
                ChooseUploadMethod();

            }
        });

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
        this.finish();
    }

    public void ChooseUploadMethod(){
        Intent chooseUpload = new Intent(this, ChooseUploadActivity.class);
        startActivityForResult(chooseUpload,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String uri = data.getStringExtra("uri");

                Bitmap myBitmap = BitmapFactory.decodeFile(uri);

                if (counter == 1) {
                    bodyFront.setImageBitmap(myBitmap);
                }

                if (counter == 2) {
                    bodyBack.setImageBitmap(myBitmap);
                }
            }

            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
