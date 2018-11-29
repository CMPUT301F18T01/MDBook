package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCommentActivity extends AppCompatActivity {

    private Button addComment;
    private EditText etComment;
    private String comment;
    private Integer problemPos;
    private String patientID;
    private Record commentRecord;
    private Problem problem;
    private Patient patient;
    private String patientPos;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        UserManager.initManager();
        UserManager userManager = UserManager.getManager();


        addComment = findViewById(R.id.addCommentBtn);
        etComment = findViewById(R.id.etAddComment);

        problemPos = getIntent().getExtras().getInt("problemPos");
        patientID = getIntent().getExtras().getString("patientID");



        try {
            patient = (Patient) userManager.fetchUser(patientID);
            problem = patient.getProblems().get(problemPos);
            addComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comment = (etComment.getText().toString());
                    commentRecord = new Record("Caregiver says: ");
                    commentRecord.setComment(comment);
                    problem.addRecord(commentRecord);
                    backToRecord();
                }
            });
        } catch (NoSuchUserException e) {
            Toast.makeText(AddCommentActivity.this, "No such user exists", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }




    }

    public void backToRecord()
    {
        TAG = "coming back";
        patientPos = getIntent().getExtras().getString("patientPos");
        Intent backIntent = new Intent(AddCommentActivity.this, ListRecordsCGActivity.class);
        backIntent.putExtra("problemPos", problemPos);
        backIntent.putExtra("patientPos", patientPos);
        backIntent.putExtra("patientID", patientID);
        backIntent.putExtra("record", commentRecord);
        backIntent.putExtra("TAG", TAG);
        startActivity(backIntent);
        this.finish();
    }
}
