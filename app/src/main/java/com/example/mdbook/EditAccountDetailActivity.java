/*
 * EditAccountDetailActivity
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Creates an activity for the user to edit their Account Details
 *
 *
 * @see com.example.mdbook.LoginActivity
 *
 *
 * @author .....
 * @author James Aina
 *
 * @version 0.0.1
 */
public class EditAccountDetailActivity extends AppCompatActivity {

    private TextView editName;
    private EditText editPhone;
    private EditText editEmail;
    private Button save;
    private Button cancel;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_detail);
        // set the view and butons appropriately by id's
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();
        user = UserController.getController().getUser();

       editName.setText(user.getUserID());
       editPhone.setText(user.getPhoneNumber());
       editEmail.setText(user.getEmail());

        // Switches to the main activity upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setPhoneNumber(editPhone.getText().toString());
                user.setEmail(editEmail.getText().toString());
                userManager.saveUser(user);
                backToMain();
            }
        });

        // Switches to the main activity upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
    }


    /**
     * Creates a new intent for switching to the ListProblemActivity
     */
    public void backToMain(){

        Intent backToMain = new Intent(this, ListProblemActivity.class);
        startActivity(backToMain);

    }
}
