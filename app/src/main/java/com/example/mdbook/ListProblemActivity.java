package com.example.mdbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ListProblemActivity extends AppCompatActivity {

    ArrayList<String> problemArray = new ArrayList<String>();
    ListView problemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_problem);
        problemList = findViewById(R.id.problemList);

    }
}
