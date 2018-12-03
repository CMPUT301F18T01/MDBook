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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.ExtractedTextRequest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

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
    Button Front;
    Button Back;
    Button done;
    Button cancel;
    TextView bodypart;
    TextView label1;
    String location;
    Bitmap myBitmap;
    private int xF, yF, xB, yB;
    private ArrayList<String> bodypartlist;
    private ArrayList<String> labellist;

    private BodyLocation bodylocation;
    private UserManager userManager;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_body_location_view);


        bodylocation = new BodyLocation();

        // set the views and buttons appropriately by id's
        done = findViewById(R.id.done);
        cancel = findViewById(R.id.cancel);
        label1 = findViewById(R.id.label);
        bodypart = findViewById(R.id.bodypart);
        //Front = findViewById(R.id.uploadFront);
        //Back = findViewById(R.id.uploadBack);
        bodyFront = findViewById(R.id.body_front);
        bodyBack = findViewById(R.id.body_back);


        bodyFront.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                xF = (int) motionEvent.getX();
                yF = (int) motionEvent.getY();
                bodylocation.setFrontLoc(xF, yF);
                ChooseUploadMethod();
                return false;

            }
        });

        bodyBack.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                xB = (int) motionEvent.getX();
                yB = (int) motionEvent.getY();
                bodylocation.setBackLoc(xB, yB);
                ChooseUploadMethod();
                return false;

            }
        });


        //Intent intent = getIntent();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Save data into the user manager

                //Toast toast = Toast.makeText(getApplicationContext(), "Body Location successfully added!", Toast.LENGTH_SHORT);
                //toast.show();
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

    public void BackToAddProblem() {
        this.finish();
    }

    public void ChooseUploadMethod() {
        Intent chooseUpload = new Intent(this, ChooseUploadActivity.class);
        startActivityForResult(chooseUpload, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String uri = data.getStringExtra("uri");
                //Photo photo = (Photo) getIntent().getExtras().getSerializable("uri");

                //String uri = photo.getFilepath();

                //myBitmap = BitmapFactory.decodeFile(uri);

                Photo photo = new Photo(uri);
                bodylocation.setPhoto(photo);


                    //Toast toast = Toast.makeText(getApplicationContext(), bodylocation.getPhoto().getFilepath() + " " + "added", Toast.LENGTH_LONG);
                    //toast.show();
                Intent intent = new Intent();
                intent.putExtra("bodylocation", bodylocation);
                setResult(NewBodyLocationView.RESULT_OK,intent);
                finish();


                }


                if (resultCode == RESULT_CANCELED) {
                    Toast toast = Toast.makeText(getApplicationContext(), "No image URI found", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        }

    }



