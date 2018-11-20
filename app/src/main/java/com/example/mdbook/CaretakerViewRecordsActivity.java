/*
 * CaregiverViewRecordActivity
 *
 * Version 0.0.1
 *
 * 2018-11-17
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
/**
 * Creates a view of the users problem records
 *
 * @see com.example.mdbook.Record
 * @see com.example.mdbook.CustomAdapter
 *
 * @author James Aina
 *
 * @version 0.0.1
 */
public class CaretakerViewRecordsActivity extends AppCompatActivity {
    // Initialize dummy variables for debugging
    ListView CaregiverRecordListView;
    String[] Title1 = {"Justin Trudeau", "Barrack Obama", "Kanye West", "Beyonce Knowles",
            "Aubrey Drake", "Donald Trump"};


    int[] Images1 = {R.drawable.justin_trudeau,
            R.drawable.obama,
            R.drawable.kanye_west,
            R.drawable.beyonce,
            R.drawable.drake,
            R.drawable.donald_trump,
    };

    String[] Comment1 = {"I have a headache", "My head hurts", "My Hand Hurts", "My nose hurts", "My finger hurts", "My eyes hurt"};
    String[] Date1 = {"08/09/18", "12/09/18", "16/09/18", "19/09/18", "21/09/18", "28/09/18"};

    /**
     * Creates a listview with an imageview
     * @param savedInstanceState the saved for onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker_view_records);

        CaregiverRecordListView = (ListView) findViewById(R.id.recordListView);
        CustomAdapter customAdapter = new CustomAdapter(CaretakerViewRecordsActivity.this,
                Title1 ,Images1, Date1, Comment1);
        CaregiverRecordListView.setAdapter(customAdapter);
    }
}
