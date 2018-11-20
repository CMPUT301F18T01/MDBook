package com.example.mdbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private EditText etUserID;
    private EditText etEmailAddress;
    private EditText etPhoneNumber;
    private Button registerButton;
    private CheckBox CGCheckbox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUserID = findViewById(R.id.etUserID);
        etUserID.setOnClickListener(this);
        etEmailAddress = findViewById(R.id.etEmail);
        etEmailAddress.setOnClickListener(this);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPhoneNumber.setOnClickListener(this);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);
        CGCheckbox = findViewById(R.id.cgCheckBox);
        CGCheckbox.setOnCheckedChangeListener(this);





    }

    @Override
    public void onClick(View v)
    {

        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        switch (v.getId())
        {
            case R.id.etEmail:
                etEmailAddress.setText("");
                break;
            case R.id.etPhoneNumber:
                etPhoneNumber.setText("");
                break;
            case R.id.etUserID:
                etUserID.setText("");
                break;
            case R.id.registerButton:
                if(CGCheckbox.isChecked())
                {
                    try {
                        if(etUserID.getText().toString().equals("")){

                            Toast.makeText(this, "Enter unique User ID", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            userManager.createCaregiver(etUserID.getText().toString(), etPhoneNumber.getText().toString(), etEmailAddress.getText().toString());
                            Toast.makeText(this, "UserID created: " + etUserID.getText(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (UserIDNotAvailableException e) {
                        Toast.makeText(this, "UserID is taken", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
                else
                    try {
                        if (etUserID.getText().toString().equals("")) {
                            Toast.makeText(this, "Enter unique User ID", Toast.LENGTH_SHORT).show();
                        } else {
                            userManager.createPatient(etUserID.getText().toString(), etPhoneNumber.getText().toString(), etEmailAddress.getText().toString());
                            Toast.makeText(this, "UserID created: " + etUserID.getText(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (UserIDNotAvailableException e) {
                        Toast.makeText(this, "UserID is taken", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}