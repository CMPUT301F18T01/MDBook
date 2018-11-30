package com.example.mdbook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewholder> {

    private static final String TAG = "RecordAdapter";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ArrayList<Record> mrecordList;
    private Activity mActivity;


    public static class RecordViewholder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDate;
        public TextView mComment;
        public ImageButton mAddComment;
        public ImageButton mLocation;


        public RecordViewholder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.recordImage);
            mTitle = itemView.findViewById(R.id.recordTitle);
            mDate = itemView.findViewById(R.id.recordDate);
            mComment = itemView.findViewById(R.id.recordComments);
            mAddComment = itemView.findViewById(R.id.recordAddComment);
            mLocation = itemView.findViewById(R.id.recordLocation);

        }
    }

    public RecordAdapter(ArrayList<Record> recordList, Activity activity){
        mrecordList = recordList;
        mActivity = activity;
    }


    @NonNull
    @Override
    public RecordViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v  = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.record_item,viewGroup,false);
        RecordViewholder rvh = new RecordViewholder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewholder recordViewholder, int position) {
        Record currentRecord = mrecordList.get(position);

        //recordViewholder.mImageView.setImageResource(currentRecord.getPhotos().get(0).getPhotoid());
        recordViewholder.mTitle.setText(currentRecord.getTitle());
        recordViewholder.mDate.setText(currentRecord.getDate().toString());
        recordViewholder.mComment.setText(currentRecord.getComment());
        recordViewholder.mLocation.setOnClickListener(mOnLocationClickListener);


    }


    @Override
    public int getItemCount() {
        return mrecordList.size();
    }


    private View.OnClickListener mOnLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent launchmap = new Intent(mActivity, ViewMapActivity.class);
            mActivity.startActivity(launchmap);

        }
    };


    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mActivity);
        if(available == ConnectionResult.SUCCESS){
            //Everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Error occured but is fixable
            Log.d(TAG,"isServicesOK: an error has occured but is fixable");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(mActivity,available , ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(mActivity, "You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
