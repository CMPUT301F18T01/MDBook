package com.example.mdbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class ListRecordActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Record> recordList;
    private Integer problemPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        recordList = new ArrayList<>();

        Patient patient = (Patient) UserController.getController().getUser();
        if (problemPos == null) {
            problemPos = getIntent().getExtras().getInt("problemPos");
        }
        recordList = patient.getProblems().get(problemPos).getRecords();


        Record testrecord = new Record("test");
        recordList.add(testrecord);



        mRecyclerView = findViewById(R.id.recordRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecordAdapter(recordList,ListRecordActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRecord();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();
        Patient patient = (Patient) UserController.getController().getUser();
        recordList = patient.getProblems().get(problemPos).getRecords();
        mAdapter.notifyDataSetChanged();
    }

    public void AddRecord(){
        Intent addRecord = new Intent(this, AddRecordActivity.class);
        addRecord.putExtra("problemPos", problemPos);
        startActivity(addRecord);

        //this.finish();
    }
}
