package com.example.mdbook;

import android.content.Intent;
//import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddRecordActivity extends AppCompatActivity {

    EditText headline;
    EditText date;
    EditText Description;
    Button geo;
    Button body;
    Button reminder;
    Button save;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
        headline = findViewById(R.id.headline);
        date = findViewById(R.id.date);
        Description = findViewById(R.id.description);
        geo = findViewById(R.id.geo);
        body = findViewById(R.id.body);
        reminder = findViewById(R.id.reminder);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAddBodyLoc();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Save data into the user manager
                BackToAddProblem();
                //Go back to patient main page
                //BackToAddProblem();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToAddProblem();
            }
        });

        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGeoLoc();
            }
        });


    }

    public void goAddBodyLoc(){
        Intent addRecordPage = new Intent(this, NewBodyLocationView.class);
        startActivity(addRecordPage);
    }

    public void BackToAddProblem(){
        //Intent mainPage = new Intent(this, ListProblemActivity.class);
        //startActivity(mainPage);
        this.finish();
    }

    public void openGeoLoc(){
        Intent geoLoc = new Intent(this, ViewLocationActivity.class);
        startActivity(geoLoc);
    }

}
