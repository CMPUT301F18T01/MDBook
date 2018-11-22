package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;

public class AddProblemActivity extends AppCompatActivity {

    ArrayList<String> problemArray = new ArrayList<String>();
    Button save;
    Button cancel;
    Button addRecord;
    EditText title;
    EditText date;
    EditText description;
    private String activity = "notit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        title = findViewById(R.id.addTitle);
        date = findViewById(R.id.addDate);
        description = findViewById(R.id.addDescription);
        addRecord = findViewById(R.id.addRecord);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Save data into the user manager
                //BackToAddProblem();
                //Go back to patient main page

                BackToListProblem();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToListProblem();
            }
        });


        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddRecord();
            }
        });
    }


    public void BackToListProblem(){
        //Intent mainPage = new Intent(this, ListProblemActivity.class);
        //startActivity(mainPage);
        this.finish();
    }


    public void goAddRecord(){
        Intent addRecordPage = new Intent(this, AddRecordActivity.class);
        startActivity(addRecordPage);
    }

}
