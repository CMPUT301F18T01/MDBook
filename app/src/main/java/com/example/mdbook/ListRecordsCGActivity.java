package com.example.mdbook;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Activity which shows the caregiver the number of records a patient has for a particular patient
 * uses RecordCGAdapter.
 *
 * @author Raj Kapadia
 * @author Vanessa Peng
 *
 * @see Record
 * @see RecordCGAdapter
 */
public class ListRecordsCGActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Record> recordList;
    private Integer patientPos;
    private Integer problemPos;
    private String patientID;
    private  Patient patient;
    private  Caregiver caregiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_records_cg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        recordList = new ArrayList<>();

        caregiver = (Caregiver) UserController.getController().getUser();

        problemPos = getIntent().getExtras().getInt("problemPos");
        patientPos = getIntent().getExtras().getInt("patientPos");
        patientID =  caregiver.getPatientList().get(patientPos);
        try {
            patient = (Patient) userManager.fetchUser(patientID);
            recordList = patient.getProblems().get(problemPos).getRecords();
            Collections.sort(recordList, new Comparator<Record>() {
                @Override
                public int compare(Record p, Record q) {
                    {
                        if (p.getDate().before(q.getDate())) {
                            return 11;
                        } else if (p.getDate().after(q.getDate())) {
                            return -1;
                        } else {
                            return 0;
                        }
                    }
                }
            });
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }

        mRecyclerView = findViewById(R.id.recordRecyclerViewCG);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecordCGAdapter(recordList,ListRecordsCGActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComment();
            }
        });
    }

    /**
     * when the activity resumes
     */
    @Override
    protected void onResume() {
      super.onResume();
        Caregiver caregiver = (Caregiver) UserController.getController().getUser();
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        problemPos = getIntent().getExtras().getInt("problemPos");
        patientPos = getIntent().getExtras().getInt("patientPos");
        patientID =  caregiver.getPatientList().get(patientPos);
        try {
            patient = (Patient) userManager.fetchUser(patientID);
            recordList = patient.getProblems().get(problemPos).getRecords();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * When the back button is pressed by the user, finish the current actvity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * When user clicks on Add button to add a comment, changes to AddCommentActivity, finished current Activity
     */
    public void AddComment() {
        Intent addRecord = new Intent(this, AddCommentActivity.class);
        addRecord.putExtra("problemPos", problemPos);
        addRecord.putExtra("patientID", patientID);
        addRecord.putExtra("patientPos", patientPos);
        startActivity(addRecord);
        this.finish();
    }

}
