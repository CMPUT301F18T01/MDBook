/*
 * LoginActivity
 *
 * Version 0.0.1
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */


package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Switch careGiverSwitch;
    private Button logInBtn;
    private TextView registerText;
    private EditText etUserID;
    private String dummyCareGiver = "CG123", dummyPatient = "P123";
    private String activity = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        careGiverSwitch = findViewById(R.id.switchType);
        logInBtn = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.register_text);
        etUserID = findViewById(R.id.etUserID);

        careGiverSwitch.setOnCheckedChangeListener(this);
        logInBtn.setOnClickListener(this);
        registerText.setOnClickListener(this);
        etUserID.setOnClickListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {

    }

    @Override
    public void onClick(View v)
    {
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        switch (v.getId()){

            case R.id.loginButton:
                if(careGiverSwitch.isChecked())
                {
                    if(etUserID.getText().toString().equals(dummyCareGiver)|| userManager.login(etUserID.getText().toString())){
                        Toast.makeText(this, "Logging in " + dummyCareGiver, Toast.LENGTH_SHORT).show();
                        Intent careGiverIntent = new Intent(LoginActivity.this, ListPatientActivity.class);
                        careGiverIntent.putExtra("activity", activity);
                        startActivity(careGiverIntent);
                    }
                    else
                    {
                        Toast.makeText(this,  etUserID.getText() + ": invalid ID", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    if(etUserID.getText().toString().equals(dummyPatient) || userManager.login(etUserID.getText().toString())) {
                        Intent problemListActivityIntent = new Intent(this, ListProblemActivity.class);
                        problemListActivityIntent.putExtra("activity", activity);
                        Toast.makeText(this, "Logging in " + dummyPatient, Toast.LENGTH_SHORT).show();
                        startActivity(problemListActivityIntent);
                    }
                    else
                    {
                        Toast.makeText(this,  etUserID.getText() + ": invalid ID", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case R.id.register_text:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.etUserID:
                etUserID.setText("");
                break;

        }
    }
}
