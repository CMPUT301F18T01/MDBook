package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ListProblemActivity extends AppCompatActivity {

    ArrayList<String> problemArray = new ArrayList<String>();
    ListView problemList;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problem);
        problemList = findViewById(R.id.problemList);
        save = findViewById(R.id.problemButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the save button is clicked, direct the user to the add problem page
                addProblem();
            }
        });
    }

    public void addProblem(){
        Intent intent = new Intent(this, AddProblemActivity.class);
        startActivity(intent);
    }
}
