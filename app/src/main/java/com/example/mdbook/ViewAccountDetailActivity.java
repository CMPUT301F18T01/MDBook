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
 * @author .....
 * @author James Aina
 *
 * @version 0.0.1
 */
public class ViewAccountDetailActivity extends AppCompatActivity {

    TextView name;
    TextView phone;
    TextView email;
    Button editAccount;
//    private String previousIntent;
//    private String newName ;
//    private String newEmail ;
//    private String newPhone ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_detail);

        // set the view and buttons appropriately by id's
        name = findViewById(R.id.viewName);
        phone = findViewById(R.id.viewPhone);
        email = findViewById(R.id.viewEmail);
        editAccount = findViewById(R.id.EditAccount);
//        Intent getPreviousIntent = getIntent();
//        previousIntent = getPreviousIntent.getExtras().getString("activity");
//
//        if (previousIntent.equals("EditAccountDetailActivity")) {
//            Intent getUserFromAdd = getIntent();
//            newName = getUserFromAdd.getExtras().getString("newName");
//            //name.setText(newName);
//            newEmail = getUserFromAdd.getExtras().getString("newEmail");
//            //email.setText(newEmail);
//            newPhone = getUserFromAdd.getExtras().getString("newPhone");
//            //phone.setText(newPhone);
//        }

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEditAccount();
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


}
