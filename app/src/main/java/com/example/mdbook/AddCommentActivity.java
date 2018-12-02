package com.example.mdbook;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Creates an activity where the caregiver and patient are able to add comment record
 * to problems.
 *
 *
 * @see com.example.mdbook.Patient
 * @see com.example.mdbook.Caregiver
 * @see com.example.mdbook.Problem
 * @see com.example.mdbook.Record
 * @see UserManager
 *
 * @author Raj Kapadia
 *
 *
 * @version 0.0.1
 */

public class AddCommentActivity extends AppCompatActivity {

    private Button addComment;
    private EditText etComment;
    private String comment;
    private Integer problemPos;
    private String patientID;
    private Record commentRecord;
    private Problem problem;
    private Patient patient;
    Caregiver caregiver;

    /**
     * @throws NoSuchUserException
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();

        caregiver = (Caregiver)UserController.getController().getUser();

        commentRecord = new Record("Caregiver says: ");

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
                    commentRecord.setComment(comment);
                    problem.addRecord(commentRecord);

                    userManager.saveUser(patient);
                    userManager.saveUser(caregiver);
                    backToRecord();
                }
            });
        } catch (NoSuchUserException e) {
            Toast.makeText(AddCommentActivity.this, "No such user exists", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }

    }

    /**
     * Takes user back to list of records the caregiver can see. Passes neccessary Extras to
     * ListRecordsCGActivity
     *
     * @see ListRecordsCGActivity
     * @result The caregiver can see the added comment in the recyclerview.
     */
    public void backToRecord()
    {
        Intent backIntent = new Intent(AddCommentActivity.this, ListRecordsCGActivity.class);
        backIntent.putExtra("problemPos", problemPos);
        backIntent.putExtra("patientID", patientID);
        backIntent.putExtra("record", commentRecord);
        startActivity(backIntent);
        this.finish();
    }
}
