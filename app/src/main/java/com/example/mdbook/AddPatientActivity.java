package com.example.mdbook;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPatientActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addPatientBtn;
    private Button cancelBtn;
    private EditText enterIDText;

    private String activity = "AddPatientActivity";
//
//    private DrawerLayout drawerLayout;
//    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        enterIDText = (EditText)findViewById(R.id.enterUserText);
        enterIDText.setOnClickListener(this);

        addPatientBtn = (Button)findViewById(R.id.addPatientBtn);
        addPatientBtn.setOnClickListener(this);

        cancelBtn = (Button)findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(this);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(actionBarDrawerToggle.onOptionsItemSelected(item))
//        {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.addPatientBtn:
                String userID = enterIDText.getText().toString();
                Toast.makeText(this, userID + ": added", Toast.LENGTH_SHORT);
                Intent addToListPatientIntent = new Intent(this, ListPatientActivity.class);
                addToListPatientIntent.putExtra("userID", userID);
                addToListPatientIntent.putExtra("activity", activity);
                startActivity(addToListPatientIntent);
                break;

            case R.id.cancelBtn:
                Intent patientListIntent = new Intent(AddPatientActivity.this, ListPatientActivity.class);
                startActivity(patientListIntent);
                break;

            case R.id.enterUserText:
                enterIDText.setText("");
                break;

        }

    }
}
