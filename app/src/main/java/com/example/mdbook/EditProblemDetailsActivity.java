/*
 * EditProblemDetailActivity
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Creates a view for editing the problems details
 *
 * @see com.example.mdbook.Record
 *
 * @author Noah Burghardt
 * @author Raj Kapadia
 * @author Vanessa Peng
 * @author James Aina
 *
 * @version 0.0.1
 **/
public class EditProblemDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText editTitle;
    private Button editDate;
    private EditText editDescription;
    private Button save;
    private Button cancel;
    private Problem problem;
    private int problemPos;
    private Patient patient;
    private Date date;
    private String strDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_problem_details);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

        // set the views and buttons appropriately by id's
        editTitle = findViewById(R.id.showTitle);
        editDate = findViewById(R.id.datePickerEdit);
        editDescription = findViewById(R.id.editDescription);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);
        UserManager.initManager();
        final UserManager userManager = UserManager.getManager();
        patient = (Patient) UserController.getController().getUser();
        problemPos = getIntent().getExtras().getInt("problemPos");
        problem = patient.getProblems().get(problemPos);
        editTitle.setText(problem.getTitle());
        editDescription.setText(problem.getDescription());
        date = problem.getDate();
        strDate = dateFormat.format(date);
        editDate.setText(strDate);


        // Switches to the main activity upon the click of the save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                problem.setTitle(editTitle.getText().toString());
                problem.setDescription(editDescription.getText().toString());
                userManager.saveUser(patient);
                backToMainPage();
            }
        });
        // Switches to the main activity upon the click of the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainPage();
            }
        });

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);


            }
        });
    }

    /**
     * Creates a DatePicker instance and lets the user select the date from
     * calender that pops. This happens when
     * @param v
     */
    public void showDatePicker(View v) {
        DialogFragment newFragment = new AddProblemActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
    }


    /**
     * Creates a new intent for switching to the ListProblemActivity
     */
    public void backToMainPage(){
        Intent intent = new Intent(EditProblemDetailsActivity.this, ListProblemActivity.class);
        startActivity(intent);
        this.finish();
    }

    private String setDate(Date date) {
        String strDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        strDate = dateFormat.format(date);
        return strDate;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        view.setMaxDate(Calendar.getInstance().getTimeInMillis());
        date = new Date(view.getYear(), view.getMonth(), view.getDayOfMonth());
        String strDate = setDate(date);
        editDate.setText(strDate);
        problem.setDate(date);
        Toast.makeText(EditProblemDetailsActivity.this, strDate, Toast.LENGTH_LONG).show();
    }

    /**
     * A static fragment class required for proper DatePicker implementation
     */
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

