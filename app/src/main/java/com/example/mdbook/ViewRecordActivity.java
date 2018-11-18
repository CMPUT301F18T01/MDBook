/*
* ViewRecordActivity
*
* Version 0.0.1
*
* 2018-11-17
*
* Copyright (c) 2018. All rights reserved.
*/


package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * Creates a view for the records to users problems
 *
 * @see com.example.mdbook.Record
 * @see com.example.mdbook.CustomAdapter
 *
 * @author James Aina
 *
 * @version 0.0.1
 */

public class ViewRecordActivity extends AppCompatActivity {
    // Initialize dummy variables for debugging
    ListView mListView;
    String[] Title = {"Justin Trudeau", "Barrack Obama", "Kanye West", "Beyonce Knowles",
            "Aubrey Drake", "Donald Trump"};


    int[] Images = {R.drawable.justin_trudeau,
            R.drawable.obama,
            R.drawable.kanye_west,
            R.drawable.beyonce,
            R.drawable.drake,
            R.drawable.donald_trump,
           };

    String[] Comment = {"I have a headache", "My head hurts", "My Hand Hurts", "My nose hurts", "My finger hurts", "My eyes hurt"};
    String[] Date = {"08/09/18", "12/09/18", "16/09/18", "19/09/18", "21/09/18", "28/09/18"};


    /**
     * Creates a listview with an imageview
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        mListView = (ListView) findViewById(R.id.recordListView);
        CustomAdapter customAdapter = new CustomAdapter(ViewRecordActivity.this,
                Title ,Images, Date, Comment);
        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(getApplicationContext(), EditDetailsActivity.class);
                mIntent.putExtra("Titles", Title[i]);
                mIntent.putExtra("Images", Images[i]);
                startActivity(mIntent);
            }
        });
    }
}

