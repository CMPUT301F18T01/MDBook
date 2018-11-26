/*
 * CaregiverViewRecordActivity
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
import android.widget.ListView;
/**
 * Creates a view of the users problem records
 *
 * @see com.example.mdbook.Record
 * @see com.example.mdbook.CustomAdapter
 *
 * @author James Aina
 *
 * @version 0.0.1
 */
public class CaretakerViewRecordsActivity extends AppCompatActivity {
    // Initialize dummy variables for debugging
    Button ReturnButton;
    ListView CaregiverRecordListView;
//    String[] Title1 = {"Head Issue", "Ear Issue", "Hand Issue",
//            "Nose Issue", "Finger Issue", "Eye Issue"};


//    int[] Images1 = {R.drawable.donald_trump,
//            R.drawable.ears,
//            R.drawable.hand,
//            R.drawable.nose,
//            R.drawable.finger,
//            R.drawable.eye,
//    };

//    String[] Comment1 = {"I have a headache", "My ear hurts", "My Hand Hurts", "My nose hurts", "My finger hurts", "My eye hurts"};
//    String[] Date1 = {"08/09/18", "12/09/18", "16/09/18", "19/09/18", "21/09/18", "28/09/18"};

    /**
     * Creates a listview with an imageview
     * @param savedInstanceState the saved for onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_view_records);
        ReturnButton = findViewById(R.id.ReturnButton);

        // Switches to problem list view screen upon click of the save button
        ReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Save data into the user manager

                BackToAddProblem();
            }
        });

        CaregiverRecordListView = (ListView) findViewById(R.id.recordListView);
        CustomAdapter customAdapter = new CustomAdapter(CaretakerViewRecordsActivity.this,
                null ,null, null, null);
        CaregiverRecordListView.setAdapter(customAdapter);
    }

    public void BackToAddProblem(){

        this.finish();
    }
}
