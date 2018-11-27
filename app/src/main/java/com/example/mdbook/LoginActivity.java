package com.example.mdbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Logs in user, if user exists.
 *
 *
 * @see com.example.mdbook.LoginActivity
 *
 * @author Noah Burghardt
 * @author Raj Kapadia
 * @author James Aina
 *
 * @version 0.0.1
 */

public class LoginActivity extends AppCompatActivity {

    private EditText etUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Initialize controllers */
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("shared preferences",
                getApplicationContext().MODE_PRIVATE);
        LocalStorageController.init(sharedPreferences);
        UserManager.initManager();

        setContentView(R.layout.activity_login);

        etUserID = findViewById(R.id.etUserID);
    }

    public void onLoginClick(View v) {
        UserManager userManager = UserManager.getManager();
        if (userManager.login(etUserID.getText().toString())) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            User user = UserController.getController().getUser();
            String activity = "LoginActivity";
            if (user.getClass() == Patient.class) {
                Intent patientIntent = new Intent(this, ListProblemActivity.class);
                patientIntent.putExtra("activity", activity);
                startActivity(patientIntent);
            } else if (user.getClass() == Caregiver.class) {
                Intent caregiverIntent = new Intent(this, ListPatientActivity.class);
                caregiverIntent.putExtra("activity", activity);
                startActivity(caregiverIntent);
            }
        }
    }

    public void onRegisterClick(View v) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }

    public void onResume(){
        super.onResume();
        UserManager.getManager().logout();
    }
}
