package com.example.mdbook;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;

import android.widget.EditText;
import android.widget.TextView;
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

        etUserID.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN ||
                        event.getAction() == KeyEvent.KEYCODE_ENTER){
                    hideSoftKeyboard();
                    onLoginClick(v);

                }
                return false;
            }
        });

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
                this.finish();
            } else if (user.getClass() == Caregiver.class) {
                Intent caregiverIntent = new Intent(this, ListPatientActivity.class);
                caregiverIntent.putExtra("activity", activity);
                startActivity(caregiverIntent);
                this.finish();
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

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
