/*
 * PatientSlideActivity
 *
 * Version 0.0.1
 *
 * 2018-11-17
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Lets the user have a slideshow
 *
 * @see com.example.mdbook.Record
 * @see com.example.mdbook.ImageAdapter
 *
 * @author James Aina
 *
 * @version 0.0.1
 */
public class PatientSlideActivity extends AppCompatActivity {

    private ArrayList<Record> recordList;
    private Integer problemPos;

    /**
     * Creates a viewpager to allow slideshow
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_slide);

        UserManager.initManager();
        UserManager userManager = UserManager.getManager();

        Patient patient = (Patient) UserController.getController().getUser();
        if (problemPos == null) {
            problemPos = getIntent().getExtras().getInt("problemPos");
        }
        recordList = patient.getProblems().get(problemPos).getRecords();

        ViewPager viewPager = findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(this, recordList, problemPos);
        viewPager.setAdapter(adapter);
    }
}
