/*
* ViewRecordActivity
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


/**
 * Creates a view for the records to users problems
 *
 * @see com.example.mdbook.Record
 * @see com.example.mdbook.CustomAdapter
 *
 * @author James Aina
 *
 * @version 0.0.1
 */

public class ViewRecordActivity extends AppCompatActivity {
    Button save;
    Button cancel;
    // Initialize dummy variables for debugging
    ListView mListView;
    String[] Title = {"Head Issue", "Ear Issue", "Hand Issue",
            "Nose Issue", "Finger Issue", "Eye Issue"};


    int[] Images = {R.drawable.donald_trump,
            R.drawable.ears,
            R.drawable.hand,
            R.drawable.nose,
            R.drawable.finger,
            R.drawable.eye,
           };

    String[] Comment = {"I have a headache", "My ear hurts", "My Hand Hurts", "My nose hurts", "My finger hurts", "My eye hurts"};
    String[] Date = {"08/09/18", "12/09/18", "16/09/18", "19/09/18", "21/09/18", "28/09/18"};


    /**
     * Creates a listview with an imageview
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        save = findViewById(R.id.SaveRecordButton);
        cancel = findViewById(R.id.EditRecordButton);

        // Switches to problem list view screen upon click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveBtnClicked();
                //Go back to patient main page
            }
        });

        // Switches to problem list view screen upon click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelBtnClicked();
            }
        });



        mListView = (ListView) findViewById(R.id.recordListView);
        CustomAdapter customAdapter = new CustomAdapter(ViewRecordActivity.this,
                Title ,Images, Date, Comment);
        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(getApplicationContext(), EditProblemDetailsActivity.class);
                mIntent.putExtra("Titles", Title[i]);
                mIntent.putExtra("Images", Images[i]);
                startActivity(mIntent);
            }
        });
    }

    /**
     * Switches the activity back to the List problem activity view
     */

    public void SaveBtnClicked(){

        //Todo: Save data into the user manager
        this.finish();
    }

    public void CancelBtnClicked(){

        this.finish();
    }

}

