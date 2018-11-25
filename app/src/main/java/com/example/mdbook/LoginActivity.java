package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    private Button logInBtn;
    private TextView registerText;
    private EditText etUserID;
    private String dummyCareGiver = "CG123", dummyPatient = "P123";
    private String activity = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        logInBtn = findViewById(R.id.loginButton);
        registerText = findViewById(R.id.register_text);
        etUserID = findViewById(R.id.etUserID);

        logInBtn.setOnClickListener(this);
        registerText.setOnClickListener(this);
        etUserID.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){}

    @Override
    public void onClick(View v)
    {

        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

//        DataManager.getDataManager().pull();




        switch (v.getId()){

            case R.id.loginButton:
                    userManager.login(etUserID.getText().toString());
                    User user = new UserController().getController().getUser();
                    if (user != null)
                    //if(user instanceof Patient)
                    {
                        Toast.makeText(this, "Is a patient", Toast.LENGTH_SHORT).show();
                        Intent problemListActivityIntent = new Intent(this, ListProblemActivity.class);

                        problemListActivityIntent.putExtra("user ID", etUserID.getText().toString());


                        problemListActivityIntent.putExtra("activity", activity);
                        Toast.makeText(this, "Logging in " + dummyPatient, Toast.LENGTH_SHORT).show();
                        startActivity(problemListActivityIntent);
                        this.finish();

                    }
                    else if(user instanceof Caregiver)
                    {
                        Toast.makeText(this, "Is a caregiver", Toast.LENGTH_SHORT).show();
                        Toast.makeText(this, "Logging in " + dummyCareGiver, Toast.LENGTH_SHORT).show();
                        Intent careGiverIntent = new Intent(LoginActivity.this, ListPatientActivity.class);
                        careGiverIntent.putExtra("activity", activity);
                        startActivity(careGiverIntent);
                    }

                break;


            case R.id.register_text:
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                this.finish();
                break;

            case R.id.etUserID:
                etUserID.setText("");
                break;

        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Can not go back", Toast.LENGTH_SHORT).show();
    }
}
