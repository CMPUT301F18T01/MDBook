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
/**
 * Record Adapter for viewing records in ListRecordCGActivity for the caregiver
 *
 * @author Raj Kapadia
 * @author Thomas Chan
 *
 * @see ListRecordsCGActivity
 * @see Record
 *
 *
 */
public class RecordCGAdapter extends RecyclerView.Adapter<RecordCGAdapter.RecordCGViewholder> {

    private static final String TAG = "RecordCGAdapter";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ArrayList<Record> mrecordList;
    private Activity mActivity;
    public Record currentRecord;

    /**
     * A static class which is for a particular view, which contains the contents of record.
     */
    public static class RecordCGViewholder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDate;
        public TextView mComment;
        public ImageButton mAddComment;



        /**
         * creates a RecordViewHolder object
         * @param itemView
         */
        public RecordCGViewholder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.recordImageCG);
            mTitle = itemView.findViewById(R.id.recordTitleCG);
            mDate = itemView.findViewById(R.id.recordDateCG);
            mComment = itemView.findViewById(R.id.recordCommentsCG);
            mAddComment = itemView.findViewById(R.id.recordAddCommentCG);

        }
    }


    /**
     * creates a RecordAdapter object
     * @param recordList: needs list of all records by the user currently signed
     * @param activity: needs the current activity to implement RecordAdapter
     */
    public RecordCGAdapter(ArrayList<Record> recordList, Activity activity){
        mrecordList = recordList;
        mActivity = activity;
    }

    /**
     * when a RecordViewHolder is created, it adds to a layout
     * @param viewGroup
     * @param i
     * @return RecordViewHolder
     */
    @NonNull
    @Override
    public RecordCGViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v  = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.record_cg_items,viewGroup,false);
        RecordCGViewholder rvh = new RecordCGViewholder(v);
        return rvh;
    }

    /**
     * Sets the content of a record to objects on the RecordViewHolder
     * @param recordCGViewholder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecordCGViewholder recordCGViewholder, int position) {
        currentRecord = mrecordList.get(position);

        //recordViewholder.mImageView.setImageResource(currentRecord.getPhotos().get(0).getPhotoid());
        recordCGViewholder.mTitle.setText(currentRecord.getTitle());
        recordCGViewholder.mDate.setText(currentRecord.getDate().toString());
        recordCGViewholder.mComment.setText(currentRecord.getComment());
    }


    /**
     * @return size of list
     */
    @Override
    public int getItemCount() {
        return mrecordList.size();
    }


}
