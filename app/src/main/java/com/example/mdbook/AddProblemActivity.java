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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Creates an activity for the user to add a problem
 * Displays a list of all the problems already added
 *
 * @see com.example.mdbook.Problem
 *
 *
 * @author Vanessa Peng
 * @author Raj
 * @author James Aina
 *
 * @version 0.0.1
 */

public class AddProblemActivity extends AppCompatActivity {

    ArrayList<String> problemArray = new ArrayList<String>();
    Button save;
    Button cancel;
    private Button addDate;
    private Boolean datePicked;
    EditText title;
    EditText description;
    Problem problem;
    private Date date;
    private String day, month, year;
    private String dateString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        // set the view and butons appropriately by id's
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        title = findViewById(R.id.addTitle);
        description = findViewById(R.id.addDescription);
        addDate = findViewById(R.id.addDateBtn);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);

            }
        });

        // Switches to addProblemActivty upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Patient patient = (Patient) UserController.getController().getUser();

                //Patient patient = new Patient(user, null, null);

                    Problem problem = new Problem(title.getText().toString(), description.getText().toString());
                    patient.addProblem(problem);
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

    }



    public void BackToListProblem() {
        Intent mainPage = new Intent(AddProblemActivity.this, ListProblemActivity.class);
        startActivity(mainPage);
        this.finish();
    }


    public void showDatePicker(View v) {
        DialogFragment newFragment = new ProblemPickDateFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
//        day = getIntent().getExtras().getString("day");
//        month = getIntent().getExtras().getString("month");
//        year = getIntent().getExtras().getString("year");
        dateString = day.toString() + "/" + month.toString() + "/" + year.toString();
        Toast.makeText(AddProblemActivity.this, dateString, Toast.LENGTH_LONG).show();


    }



    public void goAddRecord(){
        Intent goAdd = new Intent(this, AddRecordActivity.class);
        startActivity(goAdd);
    }

}
