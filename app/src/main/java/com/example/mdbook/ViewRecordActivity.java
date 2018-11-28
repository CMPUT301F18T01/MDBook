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
import android.graphics.PathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;


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
    Button add;
//     Initialize dummy variables for debugging
    ListView mListView;
    Problem problem;
    Patient patient;
    int problemPos;
    Record record;
    String extra;

    ArrayList<Record> records = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<String> comments = new ArrayList<>();
    ArrayList<Date> date = new ArrayList<>();




    int[] Images = {R.drawable.donald_trump,
            R.drawable.ears,
            R.drawable.hand,
            R.drawable.nose,
            R.drawable.finger,
            R.drawable.eye,
           };
    

    public ViewRecordActivity() {
        date = new ArrayList<>();
    }


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
        add = findViewById(R.id.addButton);

        extra = "first";

        patient = (Patient)UserController.getController().getUser();
        problemPos = getIntent().getExtras().getInt("position");
        problem = patient.getProblems().get(problemPos);

        //To check if activity is returned form addRecord or initially run, if initial, do nothing
        if(getIntent().getStringExtra("return") == null)
        {
            Toast.makeText(ViewRecordActivity.this, "first", Toast.LENGTH_LONG).show();
        }
        //if returns from addRecord, proceed
        else{
            try{
                record = (Record) getIntent().getExtras().getSerializable("record");
                problem.addRecord(record);
                records.addAll(problem.getRecords());
            }
            catch (NullPointerException id)
            {
                records = new ArrayList<>();
            }
        }







        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddNewRecord();
            }
        });


        mListView = (ListView) findViewById(R.id.recordListView);
        for (int r = 0; r < records.size(); r++)
        {
            title.add(records.get(r).getTitle());
            images.add(Images[r]);
            comments.add(records.get(r).getComment());
            date.add(records.get(r).getDate());
        }
        CustomAdapter customAdapter = new CustomAdapter(ViewRecordActivity.this,
                title ,images, date, comments);
        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(getApplicationContext(), EditProblemDetailsActivity.class);
//                mIntent.putExtra("Titles", title[i]);
                mIntent.putExtra("Images", Images[i]);
                startActivity(mIntent);
            }
        });
    }

    /**
     * Switches the activity back to the List problem activity view
     */

    public void goAddNewRecord(){
        Intent addRecord = new Intent(this, AddRecordActivity.class);
        Problem problem = (Problem)getIntent().getExtras().getSerializable("problem");
        Toast.makeText(ViewRecordActivity.this, problem.getTitle(), Toast.LENGTH_SHORT).show();
        addRecord.putExtra("problem", problem);
        startActivity(addRecord);
        this.finish();
    }
    public void BackToAddProblem(){
        Intent mainPage = new Intent(this, ListProblemActivity.class);
        startActivity(mainPage);
        this.finish();
    }


}

