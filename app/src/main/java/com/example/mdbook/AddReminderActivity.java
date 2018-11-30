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

public class AddReminderActivity extends AppCompatActivity implements View.OnClickListener {


    private Button addReminder;
    //    Spinner chooseFreqPicker;
    private TimePicker timePicker;
    private int minute;
    private int hour;
    private int notificationID = 1;
    private AlarmManager alarm;

    ArrayAdapter<String> spinnerListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

//        Intent notificationIntent = new Intent(this, AlarmService.class);
//        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.SECOND, 5);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
//
//        addReminder = findViewById(R.id.AddButton);
//        timePicker = findViewById(R.id.timePicker);
//        chooseFreqPicker = findViewById(R.id.spinner);
//
//        List<String> spinnerList = new ArrayList<>();
//        spinnerList.add("daily");
//        spinnerList.add("weekly");
//        spinnerList.add("monthly");
//
//        spinnerListAdapter = new ArrayAdapter<>(this, R.layout.simple_spinner, R.id.textForSpinner, spinnerList);
//        chooseFreqPicker.setAdapter(spinnerListAdapter);

//        addReminder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(AddReminderActivity.this, timePicker.getHour() + " : " +timePicker.getMinute(), Toast.LENGTH_LONG).show();
//                int hour = timePicker.getHour();
//                int minute = timePicker.getMinute();
//
//
//
//            }
//        });
        addReminder = findViewById(R.id.addReminderButton);
        addReminder.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        TimePicker timePicker = findViewById(R.id.timePicker);


        Intent intent =  new Intent(AddReminderActivity.this, AlarmService.class);
        intent.putExtra("notificationId", notificationID);


        PendingIntent alarmIntent = PendingIntent.getBroadcast(AddReminderActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);

        if(v.getId() == R.id.addReminderButton)
        {

            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            Calendar startTime = Calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, hour);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.SECOND, 0);
            long alarmStartTime = startTime.getTimeInMillis();

            alarm.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime, AlarmManager.INTERVAL_DAY, alarmIntent);

            Toast.makeText(AddReminderActivity.this, (timePicker.getCurrentHour().toString() + timePicker.getCurrentMinute().toString()), Toast.LENGTH_LONG).show();
        }
    }
}