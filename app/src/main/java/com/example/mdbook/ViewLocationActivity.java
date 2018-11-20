package com.example.mdbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        ImageView mLocationImage =  findViewById((R.id.image));
        int imageResource = R.drawable.map_image;
        mLocationImage.setImageResource(imageResource);
    }
}
