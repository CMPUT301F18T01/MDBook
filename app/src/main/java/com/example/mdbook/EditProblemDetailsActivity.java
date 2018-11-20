/*
 * CustomAdapter
 *
 * Version 0.0.1
 *
 * 2018-11-17
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

/**
 * Creates a view for editing the problems details
 *
 * @see com.example.mdbook.Record
 *
 * @author Noah Burghardt
 * @author James Aina
 *
 * @version 0.0.1
 **/
public class EditProblemDetailsActivity extends AppCompatActivity {

    EditText editTitle;
    EditText editDate;
    EditText editComment;
    EditText editLocation;
    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem_details);

        editTitle = findViewById(R.id.editTitle);
        editDate = findViewById(R.id.editDate);
        editLocation = findViewById(R.id.editLocation);
        editComment = findViewById(R.id.editComment);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Save the data
                backToMainPage();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainPage();
            }
        });
    }

    public void backToMainPage(){
        Intent goEditProblem = new Intent(this, ListProblemActivity.class);
        startActivity(goEditProblem);
    }

}

