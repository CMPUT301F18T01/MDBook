package com.example.mdbook;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Creates an activity for the user to add a reminder
 * Displays a timepicker
 *
 * @see com.example.mdbook.Record
 *
 *
 *
 * @author Raj Kapadia
 *
 *
 * @version 0.0.1
 */

public class AddReminderActivity extends AppCompatActivity {


    Button addReminder;
    Spinner chooseFreqPicker;
    TimePicker timePicker;

    ArrayAdapter<String> spinnerListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(this, AlarmService.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);

        addReminder = findViewById(R.id.AddButton);
        timePicker = findViewById(R.id.timePicker);
        chooseFreqPicker = findViewById(R.id.spinner);

        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("daily");
        spinnerList.add("weekly");
        spinnerList.add("monthly");

        spinnerListAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner, R.id.textForSpinner, spinnerList);
        chooseFreqPicker.setAdapter(spinnerListAdapter);

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddReminderActivity.this, timePicker.getHour() + " : " +timePicker.getMinute(), Toast.LENGTH_LONG).show();
            }
        });



    }

}
