package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class ListProblemActivity extends AppCompatActivity {

    String problems[] = {"Problem A", "Problemn B", "Problem C", "Problem D"};
    EditText searchBar;
    Button addProblemButton;
    Button search;
    //ArrayAdapter problemAdapter = new ArrayAdapter();
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

        ArrayAdapter problemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, problems);
        ListView problemListView = (ListView)findViewById(R.id.problemList);
        problemListView.setAdapter(problemAdapter);

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

    }

    public void addProblem(){
        Intent intent = new Intent(this, AddProblemActivity.class);
        startActivity(intent);
    }
}
