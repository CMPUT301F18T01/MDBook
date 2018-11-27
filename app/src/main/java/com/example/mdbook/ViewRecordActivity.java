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

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


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
    private static final String TAG = "ViewRecordActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private Button save;
    private Button cancel;
    // Initialize dummy variables for debugging
    private ListView mListView;
    private String[] Title = {"Head Issue", "Ear Issue", "Hand Issue",
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
                //Todo: Save data into the user manager
                BackToAddProblem();
                //Go back to patient main page
                BackToAddProblem();
            }
        });

        // Switches to problem list view screen upon click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToAddProblem();
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

    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ViewRecordActivity.this);
        if(available == ConnectionResult.SUCCESS){
            //Everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Error occured but is fixable
            Log.d(TAG,"isServicesOK: an error has occured but is fixable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ViewRecordActivity
                    .this,available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }





    /**
     * Switches the activity back to the List problem activity view
     */

    public void BackToAddProblem(){
        Intent mainPage = new Intent(this, ListProblemActivity.class);
        startActivity(mainPage);
    }

    public void launchmap(){
        Intent launchmap= new Intent(this, MapActivity.class);
        startActivity(launchmap);
    }


}

