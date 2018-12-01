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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    TextView showTitle;
    TextView showDate;
    EditText editDescription;
    EditText editLocation;
    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem_details);
        // set the views and buttons appropriately by id's
        showTitle = findViewById(R.id.showTitle);
        showDate = findViewById(R.id.showDate);
        editDescription = findViewById(R.id.editDescription);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();


        // Switches to the main activity upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Save the data
                Patient patient = (Patient) UserController.getController().getUser();
                Problem problem = (Problem) getIntent().getExtras().getSerializable("problem");
                problem.setDescription(editDescription.getText().toString());
                 userManager.saveUser(patient);


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
        Intent intent = new Intent(EditProblemDetailsActivity.this, ListProblemActivity.class);
        startActivity(intent);
        this.finish();
    }

}

