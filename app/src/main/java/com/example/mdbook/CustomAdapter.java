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


/**
 * Tests the problem objects
 *
 * @author James Aina
 *
 * @version 0.0.1
 **/
public class CustomAdapter extends ArrayAdapter {
    //
    String [] titles;
    String [] dates;
    String [] comments;
    int [] images;
    Context mContext;

    public CustomAdapter( Context context, String[] recordTitles, int[]recordImages, String[] recordDates, String [] recordComments) {
        super(context, R.layout.record_listview_item);
        this.titles = recordTitles;
        this.images = recordImages;
        this.dates = recordDates;
        this.comments = recordComments;
        this.mContext = context;
   }

   @Override
   public int getCount() {
       return titles.length;
   }

    /**
     * Inflates the layout and converts the view
     * Uses a view holder to avoid lagging while scrolling on listview
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.record_listview_item, parent, false);
            //Store the images and title in a view holder
            mViewHolder.mImages = (ImageView) convertView.findViewById((R.id.imageView));
            mViewHolder.mTitles = (TextView) convertView.findViewById(R.id.titleTextView);
            mViewHolder.mDates = (TextView) convertView.findViewById(R.id.dateTextView);
            mViewHolder.mComments = (TextView) convertView.findViewById(R.id.commentTextView);

            convertView.setTag(mViewHolder);

        }
        else {
            mViewHolder = (ViewHolder)convertView.getTag();
        }

            // Sett the viewholder
            mViewHolder.mImages.setImageResource(images[position]);
            mViewHolder.mTitles.setText(titles[position]);
            mViewHolder.mComments.setText(comments[position]);
            mViewHolder.mDates.setText(dates[position]);

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
