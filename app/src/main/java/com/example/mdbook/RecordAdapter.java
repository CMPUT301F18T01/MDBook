package com.example.mdbook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
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
 * Record Adapter for viewing records in ListRecordActivity for the patient
 *
 * @author Thomas Chan
 *
 * @see ListRecordActivity
 * @see Record
 *
 *
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewholder> {


    private ArrayList<Record> mrecordList;
    private Record currentRecord;
    private Activity mActivity;
    private onItemClickListener mListener;

    public interface onItemClickListener {
        void onItemClick(int position);
        void viewmapClick(int postion);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    /**
     * A static class which is for a particular view, which contains the contents of record.
     */
    public static class RecordViewholder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTitle;
        public TextView mDate;
        public TextView mComment;
        public ImageButton mAddComment;
        public ImageButton mLocation;


        /**
         * creates a RecordViewHolder object
         * @param itemView
         * @param listener
         */
        public RecordViewholder(@NonNull View itemView, final onItemClickListener listener) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.recordImage);
            mTitle = itemView.findViewById(R.id.recordTitle);
            mDate = itemView.findViewById(R.id.recordDate);
            mComment = itemView.findViewById(R.id.recordComments);
            mAddComment = itemView.findViewById(R.id.recordAddComment);
            mLocation = itemView.findViewById(R.id.recordLocation);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.viewmapClick(position);
                        }
                    }
                }
            });

        }
    }

    /**
     * creates a RecordAdapter object
     * @param recordList: needs list of all records by the user currently signed
     * @param activity: needs the current activity to implement RecordAdapter
     */
    public RecordAdapter(ArrayList<Record> recordList, Activity activity){
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
    public RecordViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v  = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.record_item,viewGroup,false);
        RecordViewholder rvh = new RecordViewholder(v,mListener);
        return rvh;
    }

    /**
     * Sets the content of a record to objects on the RecordViewHolder
     * @param recordViewholder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecordViewholder recordViewholder, int position) {
        currentRecord = mrecordList.get(position);
        //recordViewholder.mImageView.setImageResource(currentRecord.getPhotos().get(0).getPhotoid());
        recordViewholder.mTitle.setText(currentRecord.getTitle());
        recordViewholder.mDate.setText(currentRecord.getDate().toString());
        recordViewholder.mComment.setText(currentRecord.getComment());
    }


    /**
     * @return size of list
     */
    @Override
    public int getItemCount() {
        return mrecordList.size();
    }

}
