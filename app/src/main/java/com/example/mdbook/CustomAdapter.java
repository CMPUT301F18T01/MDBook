/*
 * CustomAdapter
 *
 * Version 0.0.1
 *
 * 2018-11-17
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


/**
 * Tests the problem objects
 *
 * @author James Aina
 * @author ThomasChan
 *
 * @version 2.0.0
 **/
public class CustomAdapter extends ArrayAdapter {
    private static final String TAG = "CustomAdapter";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private String [] titles;
    private String [] dates;
    private String [] comments;
    private int [] images;
    private Context mContext;
    private Activity mActivity;

    /**
     * Creates a custom adapter for takes in array containing record details
     *
     * @param context the context for the adapter
     * @param recordTitles the title of the record
     * @param recordImages the images to be attached to the record
     * @param recordDates the datestamp of the record
     * @param recordComments the comments attached to the record
     */
    public CustomAdapter( Context context, String[] recordTitles, int[]recordImages, String[] recordDates, String [] recordComments,Activity activity) {
        super(context, R.layout.record_listview_item);
        this.titles = recordTitles;
        this.images = recordImages;
        this.dates = recordDates;
        this.comments = recordComments;
        this.mContext = context;
        this.mActivity = activity;
   }

    /**
     *
     * @return count
     */
   @Override
   public int getCount() {
       return titles.length;
   }

    /**
     * Inflates the layout and converts the view
     * Uses a view holder to avoid lagging while scrolling on listview
     * @param position the position
     * @param convertView the converted view
     * @param parent the parent
     * @return it returns a converted view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.record_listview_item, parent, false);

            //Store the images and title in a view holder
            //set the view and butons appropriately by id's
            mViewHolder.mImages = (ImageView) convertView.findViewById((R.id.imageView));
            mViewHolder.mTitles = (TextView) convertView.findViewById(R.id.titleTextView);
            mViewHolder.mDates = (TextView) convertView.findViewById(R.id.dateTextView);
            mViewHolder.mComments = (TextView) convertView.findViewById(R.id.commentTextView);
            mViewHolder.mImageButton = (ImageButton) convertView.findViewById(R.id.imageButton);

            convertView.setTag(mViewHolder);

        }
        else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }

            // Set the viewholder to hold the information
            mViewHolder.mImages.setImageResource(images[position]);
            mViewHolder.mTitles.setText(titles[position]);
            mViewHolder.mComments.setText(comments[position]);
            mViewHolder.mDates.setText(dates[position]);
            mViewHolder.mImageButton.setOnClickListener(mOnLocationClickListener);

        return convertView;
    }


    private View.OnClickListener mOnLocationClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isServicesOK()) {
                Intent launchmap = new Intent(mContext, ViewMapActivity.class);
                mContext.startActivity(launchmap);
            }
        }
    };

    /**
     * Stores view holder variables
     */
    static  class ViewHolder{
        ImageView mImages;
        TextView mTitles;
        TextView mDates;
        TextView mComments;
        ImageButton mImageButton;

    }


    public boolean isServicesOK(){
        Log.d(TAG,"isServicesOK: checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext);
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
            Toast.makeText(mContext, "You cant make map request", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
