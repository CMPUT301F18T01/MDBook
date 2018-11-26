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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Tests the problem objects
 *
 * @author James Aina
 *
 * @version 0.0.1
 **/
public class CustomAdapter extends ArrayAdapter {

    ArrayList<String> titles;
    ArrayList<String> dates;
    ArrayList<String> comments;
    ArrayList<Integer> photos;
    Context mContext;


    /**
     * Creates a custom adapter for takes in array containing record details
     *
     * @param context the context for the adapter
     * @param recordTitles the title of the record
     * @param recordImages the images to be attached to the record
     * @param recordDates the datestamp of the record
     * @param recordComments the comments attached to the record
     */
    public CustomAdapter(Context context, ArrayList<String> recordTitles, ArrayList<Integer>recordImages,ArrayList<String>recordDates, ArrayList<String> recordComments) {
        super(context, R.layout.record_listview_item);
        this.titles = recordTitles;
        this.photos = recordImages;
        this.dates = recordDates;
        this.comments = recordComments;
        this.mContext = context;
   }

    /**
     *
     * @return count
     */
   @Override
   public int getCount() {
       return titles.size();
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

            convertView.setTag(mViewHolder);

        }
        else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }

            // Set the viewholder to hold the information
            mViewHolder.mImages.setImageResource(photos.get(position));
            mViewHolder.mTitles.setText(titles.get(position));
            mViewHolder.mComments.setText(comments.get(position));
            mViewHolder.mDates.setText(dates.get(position));

        return convertView;
    }

    /**
     * Stores view holder variables
     */
    static  class ViewHolder{
        ImageView mImages;
        TextView mTitles;
        TextView mDates;
        TextView mComments;

    }
}
