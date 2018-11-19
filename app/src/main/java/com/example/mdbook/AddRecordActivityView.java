package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AddRecordActivityView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record_view);
    }

    public void viewBodyLoc(View view) {
        Intent i = new Intent(this, NewBodyLocationView.class);
        //i.putExtra("emotions", emotions);

        startActivity(i);

    }
}
