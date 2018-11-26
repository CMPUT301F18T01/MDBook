/*
 * AddProblemActivity
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
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Creates an activity for the user to add a problem
 * Displays a list of all the problems already added
 *
 * @see com.example.mdbook.Problem
 *
 *
 * @author .....
 * @author James Aina
 *
 * @version 0.0.1
 */

public class AddProblemActivity extends AppCompatActivity {

    ArrayList<String> problemArray = new ArrayList<String>();
    Button save;
    Button cancel;
    Button addRecord;
    EditText title;
    EditText date;
    EditText description;
    Problem problem;
    Date problemDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        // set the view and butons appropriately by id's
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        title = findViewById(R.id.addTitle);
        date = findViewById(R.id.addDate);
        description = findViewById(R.id.addDescription);
        addRecord = findViewById(R.id.addRecord);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();

        // Switches to addProblemActivty upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Patient patient = (Patient) UserController.getController().getUser();

                //Patient patient = new Patient(user, null, null);
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    problemDate =  formatter.parse(date.getText().toString());
                    Problem problem = new Problem(title.getText().toString(), description.getText().toString(), problemDate);
                    patient.addProblem(problem);
                } catch (ParseException e) {
                    Toast.makeText(AddProblemActivity.this, "WRONG DATE FORMAT", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                try{
                    userManager.saveUser(patient);
                    Toast.makeText(AddProblemActivity.this, "saved problem: " + title.getText().toString(), Toast.LENGTH_SHORT).show();
                }catch ( NoSuchUserException id)
                {
                    Toast.makeText(AddProblemActivity.this, "No user", Toast.LENGTH_SHORT).show();
                }

                BackToListProblem();


            }
        });

        // Switches to addProblemActivty upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToListProblem();
            }
        });

        // Switches to addRecordActivity upon the click of the save button
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddRecord();
            }
        });
    }



    public void BackToListProblem() {
        Intent mainPage = new Intent(AddProblemActivity.this, ListProblemActivity.class);
        startActivity(mainPage);
        this.finish();
    }


    public void goAddRecord(){
        Intent goAdd = new Intent(this, AddRecordActivity.class);
        startActivity(goAdd);
    }

}
