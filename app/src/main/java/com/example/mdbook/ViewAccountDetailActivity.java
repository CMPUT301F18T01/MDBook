package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewAccountDetailActivity extends AppCompatActivity {

    TextView name;
    TextView phone;
    TextView email;
    Button editAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account_detail);
        name = findViewById(R.id.viewName);
        phone = findViewById(R.id.viewPhone);
        email = findViewById(R.id.viewEmail);
        editAccount = findViewById(R.id.EditAccount);
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goEditAccount();
            }
        });
    }

    public void goEditAccount(){
        Intent editAccount = new Intent(this, EditAccountDetailActivity.class);
        startActivity(editAccount);
    }

}
