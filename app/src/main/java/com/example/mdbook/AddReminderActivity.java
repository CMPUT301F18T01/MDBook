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
 * Displays a timepicker, uses AlarmService class
 *
 * @see com.example.mdbook.Record
 * @see AlarmService
 *
 * @author Raj Kapadia
 * @author Vanessa Peng
 *
 * @version 0.0.1
 */

public class AddReminderActivity extends AppCompatActivity implements View.OnClickListener {


    private Button addReminder;
    private int notificationID = 1;
    private TimePicker timePicker;
    private AlarmManager alarm;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        addReminder = findViewById(R.id.addReminderButton);
        timePicker = findViewById(R.id.timePicker);
        addReminder.setOnClickListener(this);
    }

    /**
     * Takes care of clicking the addReminderButton.
     * @param v
     */
    @Override
    public void onClick(View v) {

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