package com.example.mdbook;

import android.content.Intent;
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

public class ListRecordsCGActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Record> recordList;
    private Integer patientPos;
    private Integer problemPos;
    private String patientID;
    private  Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_records_cg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        recordList = new ArrayList<>();

        Caregiver caregiver = (Caregiver) UserController.getController().getUser();

        if (problemPos == null || patientPos == null) {
            problemPos = getIntent().getExtras().getInt("problemPos");
            patientPos = getIntent().getExtras().getInt("patientPos");
        }

        patientID =  caregiver.getPatientList().get(patientPos);
        try {
            patient = (Patient)userManager.fetchUser(patientID);
            recordList = patient.getProblems().get(problemPos).getRecords();

        } catch (NoSuchUserException e) {
            Toast.makeText(ListRecordsCGActivity.this, "No user found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
//        recordList = patient.getProblems().get(problemPos).getRecords();




        mRecyclerView = findViewById(R.id.recordRecyclerViewCG);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecordCGAdapter(recordList,ListRecordsCGActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComment();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        recordList = patient.getProblems().get(problemPos).getRecords();
        mAdapter.notifyDataSetChanged();
    }

    public void AddComment(){
        Intent addRecord = new Intent(this, AddRecordActivity.class);
        addRecord.putExtra("problemPos", problemPos);
        startActivity(addRecord);

        //this.finish();
    }

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        return false;
//    }
}
