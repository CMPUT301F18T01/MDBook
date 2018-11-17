package com.example.mdbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

public class ViewRecordActivity extends AppCompatActivity {


    ListView mListView;
    String[] countryNames = {"Justin Trudeau", "Barrack Obama", "Kanye West", "Beyonce Knowles", "Aubrey Drake", "Donald Trump"};



    int[] countryFlags = {R.drawable.justin_trudeau,
            R.drawable.obama,
            R.drawable.kanye_west,
            R.drawable.beyonce,
            R.drawable.drake,
            R.drawable.donald_trump,
           };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);

        mListView = (ListView) findViewById(R.id.recordListView);

        CustomAdapter customAdapter = new CustomAdapter(ViewRecordActivity.this, countryNames, countryFlags);
        mListView.setAdapter(customAdapter);

        /**
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(MainActivity.this, DetailActivity.class);
                mIntent.putExtra("countryName", countryNames[i]);
                mIntent.putExtra("countryFlag", countryFlags[i]);
                startActivity(mIntent);
            }
        });*/
    }
}
/**
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
    }*/
//}
