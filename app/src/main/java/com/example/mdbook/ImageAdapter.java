/*
 * CustomImageAdapter
 *
 * Version 0.0.1
 *
 * 2018-11-17
 *
 * Copyright (c) 2018. All rights reserved.
 */


package com.example.mdbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Lets the user have a slideshow
 *
 * @see com.example.mdbook.PatientSlideActivity
 * @see com.example.mdbook.ImageAdapter
 *
 * @author James Aina
 *
 * @version 0.0.1
 */
public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mSlideImages = new int[] {R.drawable.donald_trump,
            R.drawable.ears,
            R.drawable.hand,
            R.drawable.nose,
            R.drawable.finger,
            R.drawable.eye,
    };

    // Set the context for the custom image adapter
    ImageAdapter(Context context) {
        mContext = context;

    }
    /**
     *
     * @return count
     */

    @Override
    public int getCount() {
        return mSlideImages.length;
    }

    /**
     *
     * @param view the view
     * @param object the object
     * @return boolean
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     *
     * @param container the View Group container
     * @param position The position for the item to be instantiated
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // set the view and butons appropriately by id's
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mSlideImages[position]);
        container.addView(imageView, 0);
        return imageView;

    }

    /**
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }


}