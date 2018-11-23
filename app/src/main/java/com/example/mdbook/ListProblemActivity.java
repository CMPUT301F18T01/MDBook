/*
 * ListProblemActivity
 *
 * Version 0.0.1
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */


package com.example.mdbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;



public class ListProblemActivity extends AppCompatActivity {

    ArrayList<String> problems =  new ArrayList<String>(Arrays.asList("Problem A", "Problem B", "Problem C", "Problem D"));
    EditText searchBar;
    Button addProblemButton;
    Button search;
    Button viewProfile;
    ListView problemListView;
    ArrayAdapter<String> problemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problem);
        addProblemButton = findViewById(R.id.problemButton);
        // When a user click on s
        addProblemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the save button is clicked, direct the user to the add problem page
                addProblem();
            }
        });

        problemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, problems);
        problemListView = findViewById(R.id.problemList);
        problemListView.setAdapter(problemAdapter);
        problemAdapter.notifyDataSetChanged();

        problemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //spot is the item clicked
                Intent showOption = new Intent(ListProblemActivity.this, OptionsMenuActivity.class);
                startActivity(showOption);
            }
        });


        problemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showAlertDialog(view, position);
                problemAdapter.notifyDataSetChanged();
                return true;
            }
        });


        // When a user clicked search button
        search = findViewById(R.id.go);
        searchBar = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchBar.getText().toString();
                // If the search bar is empty, tell the user to enter a keyword
                if (keyword.equalsIgnoreCase("")){
                    Toast.makeText(getApplicationContext(), "Please enter a search keyword.", Toast.LENGTH_SHORT).show();
                }
                // If the search bar is not empty, search
                // TODO: Retrieve Search Result
            }
        });

        viewProfile = findViewById(R.id.viewProfile);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goViewProfile();
            }
        });



    }

    public void showAlertDialog(View v, final int position){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("MDBook");
            alert.setMessage("Are you sure you want to delete this problem?");
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    problems.remove(position);
                    problemAdapter.notifyDataSetChanged();
                    Toast.makeText(ListProblemActivity.this, "Problem is deleted", Toast.LENGTH_SHORT).show();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Don't do anything

                }
            });
            alert.create().show();

    }


    public void addProblem(){
        Intent intent = new Intent(this, AddProblemActivity.class);
        startActivity(intent);
    }


    /**
     * Creates a new intent for switching to the ViewAccountDetailActivity
     */
    public void goViewProfile(){
        Intent viewProfile = new Intent(this, ViewAccountDetailActivity.class);
        startActivity(viewProfile);
    }
}
