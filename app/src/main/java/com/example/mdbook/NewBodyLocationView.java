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
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;

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
    String location;
    Bitmap myBitmap;
    String uri;

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
                location = "Front";
                ChooseUploadMethod();

            }
        });

        uploadBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //upload activity handled here
                location = "Back";
                ChooseUploadMethod();

            }
        });

        //Intent intent = getIntent();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Save data into the user manager
                Toast toast = Toast.makeText(getApplicationContext(), "Body Location successfully added!", Toast.LENGTH_SHORT);
                toast.show();
                BackToAddProblem();

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

    /*takes the string path and imageView
    then set's the imageView image to the image referenced by the FilePath
     */
    public void loadImage(String path, ImageView img){

        myBitmap = BitmapFactory.decodeFile(path);
        img.setImageBitmap(myBitmap);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1 & data != null) {
            if (resultCode == RESULT_OK) {

                //receives photo object from ChooseUploadActivity
                Photo photo = (Photo) getIntent().getSerializableExtra("serialized_data");

                //assigns string uri to the file path assigned to the photo object
                uri = photo.getFilepath();

                if (location == "Front") {

                    loadImage(uri, bodyFront);

                }

                if (location == "Back") {

                    loadImage(uri, bodyBack);

                }
            }


            if (resultCode == RESULT_CANCELED || data == null) {
                Toast toast = Toast.makeText(getApplicationContext(), "No image URI found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    }



