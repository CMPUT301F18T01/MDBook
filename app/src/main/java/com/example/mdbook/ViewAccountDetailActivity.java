/*
 * ViewAccountDetailActivity
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
import android.widget.TextView;


/**
 * Creates an activity for the user to view their Account Details
 *
 *
 * @see com.example.mdbook.LoginActivity
 *
 *
 * @author Raj Kapadia
 * @author Vanessa Peng
 * @author James Aina
 *
 * @version 0.0.1
 */
public class ViewAccountDetailActivity extends AppCompatActivity {

    TextView name;
    TextView phone;
    TextView email;
    Button editAccount;
    Button logOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_detail);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        User user = UserController.getController().getUser();

        // set the view and buttons appropriately by id's
        name = findViewById(R.id.viewName);
        phone = findViewById(R.id.viewPhone);
        email = findViewById(R.id.viewEmail);
        editAccount = findViewById(R.id.EditAccount);
        logOutButton = findViewById(R.id.LogOutBtn);
        name.setText(user.getUserID());
        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());


        // Switches to the edit account details upon the click of the editbutton
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEditAccount();
            }
        });
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
    }

    /**
     * Creates a new intent for switching to the EditAccountDetailActivity
     */
    public void goEditAccount(){
        Intent editAccount = new Intent(this, EditAccountDetailActivity.class);
        startActivity(editAccount);
        this.finish();
    }

    /**
     * Logout functionality
     */
    public void goToLogin()
    {
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        Intent logOutIntent = new Intent(this, LoginActivity.class);
        startActivity(logOutIntent);
        userManager.logout();
        this.finish();
    }



}
