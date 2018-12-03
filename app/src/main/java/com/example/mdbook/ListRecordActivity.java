package com.example.mdbook;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

/**
 * Activity which lists the records for a certain problem, viewed by the Patient
 *
 * @see Patient
 *
 * @author Thomas Chan
 * 
 */
public class ListRecordActivity extends AppCompatActivity {

    private static final String TAG = "ListRecordActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private RecyclerView mRecyclerView;
    private RecordAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Record> recordList;
    private Integer problemPos;
    private static final Integer ADD_RECORD_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        Patient patient = (Patient) UserController.getController().getUser();
        if (problemPos == null) {
            problemPos = getIntent().getExtras().getInt("problemPos");
        }
        recordList = patient.getProblems().get(problemPos).getRecords();



        mRecyclerView = findViewById(R.id.recordRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecordAdapter(recordList,ListRecordActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new RecordAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                recordList.get(position);
                //Toast.makeText(ListRecordActivity.this, "works",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void viewmapClick(int postion) {
                if (isServicesOK()) {
                    Intent launchmap = new Intent(ListRecordActivity.this, ViewMapActivity.class);
                    if (recordList.get(postion).getLocation().getLat() != null){
                        launchmap.putExtra("recieveLat",recordList.get(postion).getLocation().getLat());
                        launchmap.putExtra("recieveLong",recordList.get(postion).getLocation().getLong());
                        launchmap.putExtra("recieveTitle",recordList.get(postion).getLocation().getTitle());
                        startActivity(launchmap);
                    }else {
                        Toast.makeText(ListRecordActivity.this, "No location for record",Toast.LENGTH_SHORT).show();
                    }

                }

            }


        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRecord();
            }
        });
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RECORD_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                UserManager.initManager();
                UserManager userManager = UserManager.getManager();
                Patient patient = (Patient) UserController.getController().getUser();
                recordList = patient.getProblems().get(problemPos).getRecords();
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * Changes to AddRecordActivity, when user clicks add
     */
    public void AddRecord(){
        Intent addRecord = new Intent(this, AddRecordActivity.class);
        addRecord.putExtra("problemPos", problemPos);
        startActivityForResult(addRecord, ADD_RECORD_REQUEST_CODE);
        //this.finish();
    }

    /**
     * Checks the google services for proper map functionality
     * @return bool
     */
    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(available == ConnectionResult.SUCCESS){
            //Everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Error occured but is fixable
            Log.d(TAG,"isServicesOK: an error has occured but is fixable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
