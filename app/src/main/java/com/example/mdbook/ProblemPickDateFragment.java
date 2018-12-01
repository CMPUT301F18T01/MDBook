package com.example.mdbook;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Fragment to pick the date for the patient
 * when they add a new problem
 *
 *
 * @see ListProblemActivity
 *
 *
 * @author Raj Kapadia
 *
 * @version 0.0.1
 */



public class ProblemPickDateFragment extends DialogFragment {

    private int day, month, year;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
         year = c.get(Calendar.YEAR);
         month = c.get(Calendar.MONTH);
         day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    public int getDay()
    {
        return day;
    }

    public int getMonth()
    {
        return month;
    }

    public int getYear()
    {
        return year;
    }
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int month, int day) {

                    Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                            " / " + (view.getMonth()+1) +
                            " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), AddProblemActivity.class);
                    intent.putExtra("day", view.getDayOfMonth());
                    intent.putExtra("month", view.getMonth());
                    intent.putExtra("year", view.getYear());
                }
            };


}
