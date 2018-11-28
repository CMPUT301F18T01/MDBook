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

import java.util.ArrayList;

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

    private ArrayList<Problem> problems;
    private int problemPos;
    private Button save;
    private Button cancel;
    private Button addRecord;
    private EditText title;
    private EditText date;
    private EditText description;

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

        problems = new ArrayList<>();
        final Patient patient = (Patient) UserController.getController().getUser();
        problems = patient.getProblems();



        // Switches to addProblemActivty upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Problem problem = new Problem(title.getText().toString(), description.getText().toString());
                problems.add(0,problem);
                problemPos = problems.size()-1;
                try{
                    userManager.saveUser(patient);
                    Toast.makeText(AddProblemActivity.this
                            ,"Problem " + title.getText().toString() + " Added"
                            ,Toast.LENGTH_SHORT).show();

                } catch (NoSuchUserException e) {
                    Toast.makeText(AddProblemActivity.this
                            , "User does not exist"
                            , Toast.LENGTH_SHORT).show();
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
                try{
                    userManager.saveUser(patient);
                    Toast.makeText(AddProblemActivity.this
                            ,"Problem " + title.getText().toString() + " Added"
                            ,Toast.LENGTH_SHORT).show();

                } catch (NoSuchUserException e) {
                    Toast.makeText(AddProblemActivity.this
                            , "User does not exist"
                            , Toast.LENGTH_SHORT).show();
                }
                goAddRecord();
            }
        });
    }



    public void BackToListProblem() {
        //Intent mainPage = new Intent(this, ListProblemActivity.class);
        //startActivity(mainPage);
        this.finish();
    }

    public void goAddRecord(){
        Intent goAdd = new Intent(this, AddRecordActivity.class);
        goAdd.putExtra("problemPos",problemPos);
        startActivity(goAdd);
    }

}
