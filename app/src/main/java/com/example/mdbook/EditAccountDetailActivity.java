package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class EditAccountDetailActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    EditText editEmail;
    Button save;
    Button cancel;
//    String newName = editName.getText().toString();
//    String newEmail = editEmail.getText().toString();
//    String newPhone = editPhone.getText().toString();
//    private String activity = "EditAccountDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account_detail);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPhone = findViewById(R.id.editPhone);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                save();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToView();
            }
        });
    }

    public void save(){
        Intent save = new Intent(this, ViewAccountDetailActivity.class);
//        //String newName = editName.getText().toString();
//        save.putExtra("newName", newName);
//        save.putExtra("newEmail", newEmail);
//        save.putExtra("newPhone", newPhone);
//        save.putExtra("activity", activity);
        startActivity(save);
        backToView();
    }
    public void backToView(){

        this.finish();

    }
}
