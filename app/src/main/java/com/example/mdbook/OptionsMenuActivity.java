package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionsMenuActivity extends AppCompatActivity {

    Button EditProblem;
    Button EditRecord;
    Button RecordSlide;
    Button GeoLocation;
    Button DeleteProblem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_menu);
        EditProblem = findViewById(R.id.EditProblem);
        EditRecord = findViewById(R.id.EditRecord);
        RecordSlide = findViewById(R.id.RecordSlide);
        GeoLocation = findViewById(R.id.GeoLocation);
        DeleteProblem = findViewById(R.id.DeleteProblem);

        EditProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoEditProblem();
            }
        });

        EditRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoEditRecord();
            }
        });
    }

    public void GoEditProblem(){
        Intent goEditProblem = new Intent(this, EditProblemDetailsActivity.class);
        startActivity(goEditProblem);
    }

    public void GoEditRecord(){
        Intent goEditProblem = new Intent(this, ViewRecordActivity.class);
        startActivity(goEditProblem);
    }


}
