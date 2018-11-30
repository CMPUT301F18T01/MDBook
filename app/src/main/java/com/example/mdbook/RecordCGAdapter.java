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

public class RecordCGAdapter extends RecyclerView.Adapter<RecordCGAdapter.RecordCGViewholder> {


    private static final String TAG = "RecordCGAdapter";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ArrayList<Record> mrecordList;
    private Activity mActivity;
    public Record currentRecord;


    public static class RecordCGViewholder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDate;
        public TextView mComment;
        public ImageButton mAddComment;




        public RecordCGViewholder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.recordImageCG);
            mTitle = itemView.findViewById(R.id.recordTitleCG);
            mDate = itemView.findViewById(R.id.recordDateCG);
            mComment = itemView.findViewById(R.id.recordCommentsCG);
            mAddComment = itemView.findViewById(R.id.recordAddCommentCG);


        }
    }

    public RecordCGAdapter(ArrayList<Record> recordList, Activity activity){
        mrecordList = recordList;
        mActivity = activity;
    }




    @NonNull
    @Override
    public RecordCGViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v  = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.record_cg_items,viewGroup,false);
        RecordCGViewholder rvh = new RecordCGViewholder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecordCGViewholder recordCGViewholder, int position) {
        currentRecord = mrecordList.get(position);

        //recordViewholder.mImageView.setImageResource(currentRecord.getPhotos().get(0).getPhotoid());
        recordCGViewholder.mTitle.setText(currentRecord.getTitle());
        recordCGViewholder.mDate.setText(currentRecord.getDate().toString());
        recordCGViewholder.mComment.setText(currentRecord.getComment());
    }

//    private View.OnClickListener addComment = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent commentIntent = new Intent(mActivity, AddCommentActivity.class);
//            commentIntent.putExtra("record", currentRecord);
//            mActivity.startActivity(commentIntent);
//        }
//    };

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


    @Override
    public int getItemCount() {
        return mrecordList.size();
    }


}
