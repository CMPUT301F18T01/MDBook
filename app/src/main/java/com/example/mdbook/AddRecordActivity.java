package com.example.mdbook;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddRecordActivity extends AppCompatActivity {

    ImageView image;
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

    }

}
