package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewBodyLocationView extends AppCompatActivity {
    Button save;
    Button cancel;
    Button uploadFrontImage;
    Button uploadBackImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_body_location_view);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        uploadFrontImage = findViewById(R.id.uploadFrontButton);
        uploadBackImage = findViewById(R.id.uploadBackButton);
        //Intent intent = getIntent();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo: Save data into the user manager
                BackToAddRecord();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToAddRecord();
            }
        });

        uploadFrontImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFrontClicked();
            }
        });

        uploadBackImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadBackClicked();
            }
        });

    }

    public void uploadFrontClicked(){

        //code to add here

    }

    public void uploadBackClicked(){

        //code to add here

    }

    public void BackToAddRecord(){
        this.finish();
    }

}
