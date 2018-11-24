/*
 * EditProblemDetailActivity
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
        // set the views and buttons appropriately by id's
        editTitle = findViewById(R.id.editTitle);
        editDate = findViewById(R.id.editDate);
        editLocation = findViewById(R.id.editLocation);
        editComment = findViewById(R.id.editComment);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        // Switches to the main activity upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Save the data
                backToMainPage();
            }
        });
        // Switches to the main activity upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainPage();
            }
        });
    }


    /**
     * Creates a new intent for switching to the ListProblemActivity
     */
    public void backToMainPage(){
        this.finish();
    }

}

