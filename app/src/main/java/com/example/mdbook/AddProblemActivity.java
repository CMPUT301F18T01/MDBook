package com.example.mdbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

public class AddProblemActivity extends AppCompatActivity {

    ArrayList<String> problemArray = new ArrayList<String>();
    Button save;
    Button cancel;
    EditText title;
    EditText date;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        title = findViewById(R.id.addTitle);
        date = findViewById(R.id.addDate);
        description = findViewById(R.id.addDescription);

    }

    //TODO: Add newly entered problem to the array
}
