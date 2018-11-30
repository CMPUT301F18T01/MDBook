/*
 * AddProblemActivity
 *
 * Version 0.0.1
 *
 * 2018-11-18
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Creates an activity for the user to add a problem
 * Displays a list of all the problems already added
 *
 * @see com.example.mdbook.Problem
 *
 *
 * @author Vanessa Peng
 * @author Raj
 * @author James Aina
 *
 * @version 0.0.1
 */

public class AddProblemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    ArrayList<String> problemArray = new ArrayList<String>();
    Button save;
    Button cancel;
    private Button addDate;
    private Boolean datePicked;
    EditText title;
    EditText description;
    Problem problem;
    private Date date;
    private String day, month, year;
    private String dateString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_problem);
        // set the view and butons appropriately by id's
        save = findViewById(R.id.saveButton);
        cancel = findViewById(R.id.cancelButton);
        title = findViewById(R.id.addTitle);
        description = findViewById(R.id.addDescription);
        addDate = findViewById(R.id.addDateBtn);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);

            }
        });

        // Switches to addProblemActivty upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Patient patient = (Patient) UserController.getController().getUser();

                //Patient patient = new Patient(user, null, null);

                if(date != null)
                {
                    Problem problem = new Problem(title.getText().toString(), description.getText().toString());
                    problem.setDate(date);
                    patient.addProblem(problem);
                    BackToListProblem();
                }
                else{
                    Toast.makeText(AddProblemActivity.this, "Please pick date", Toast.LENGTH_LONG).show();
                    try{
                        userManager.saveUser(patient);
                        Toast.makeText(AddProblemActivity.this, "saved problem: " + title.getText().toString(), Toast.LENGTH_SHORT).show();
                    }catch ( NoSuchUserException id)
                    {
                        Toast.makeText(AddProblemActivity.this, "No user", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // Switches to addProblemActivty upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToListProblem();
            }
        });

    }



    public void BackToListProblem() {
        Intent mainPage = new Intent(AddProblemActivity.this, ListProblemActivity.class);
        startActivity(mainPage);
        this.finish();
    }


    public void showDatePicker(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");

    }

    private String setDate(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        return  dateFormat.format(date);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        view.setMaxDate(Calendar.getInstance().getTimeInMillis());
        date = new Date(view.getYear(), view.getMonth(), view.getDayOfMonth());
        String strDate = setDate(date);
        addDate.setText(strDate);
        Toast.makeText(AddProblemActivity.this, strDate, Toast.LENGTH_LONG).show();

    }

    public static class DatePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);
        }

    }
}
