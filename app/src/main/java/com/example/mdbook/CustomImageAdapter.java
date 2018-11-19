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
 * @see com.example.mdbook.CustomImageAdapter
 *
 * @author James Aina
 *
 * @version 0.0.1
 */
public class CustomImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mSlideImages = new int[] {R.drawable.justin_trudeau,
            R.drawable.obama,
            R.drawable.kanye_west,
            R.drawable.beyonce,
            R.drawable.drake,
            R.drawable.donald_trump,
    };
    // Creates a context
    CustomImageAdapter(Context context) {
        mContext = context;

    }
    // returns the length of the array
    @Override
    public int getCount() {
        return mSlideImages.length;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mSlideImages[position]);
        container.addView(imageView, 0);
        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }


}
