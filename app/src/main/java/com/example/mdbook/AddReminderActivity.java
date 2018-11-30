package com.example.mdbook;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.provider.CalendarContract;
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
import java.util.concurrent.TimeUnit;

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
    private String time;
    private String frequency;
    private int timeInMilis;

    ArrayAdapter<String> spinnerListAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent notificationIntent = new Intent(this, Reminder.class);
//        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);



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

        timeInMilis = (((timePicker.getMinute()*60) + (timePicker.getHour()*3600) * 1000));


        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chooseFreqPicker.getSelectedItem().toString().equals("daily")) {
                    scheduleNotification(getNotification("Add photo in your records."), TimeUnit.DAYS.toMillis(1));
                } else if (chooseFreqPicker.getSelectedItem().toString().equals("weekly")) {
                    scheduleNotification(getNotification("Add photo in your records."), TimeUnit.DAYS.toMillis(7));
                }
                else{
                    scheduleNotification(getNotification("Add photo in your records."), TimeUnit.DAYS.toMillis(30));
                }


            }
        });


    }

    private void scheduleNotification(Notification notification, long delay) {

        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra(NotificationActivity.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationActivity.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Reminder");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_account_box_black_24dp);
        return builder.build();
    }

}

//    public void addReminder(int startYear, int startMonth, int startDay, int startHour, int startMinute, int endYear, int endMonth, int endDay, int endHour, int endMinuts){
//        Calendar beginTime = Calendar.getInstance();
//        beginTime.set(startYear, startMonth, startDay, startHour, startMinute);
//        long startMillis = beginTime.getTimeInMillis();
//
//        Calendar endTime = Calendar.getInstance();
//        endTime.set(endYear, endMonth, endDay, endHour, endMinuts);
//        long endMillis = endTime.getTimeInMillis();
//
//        String eventUriString = "content://com.android.calendar/events";
//        ContentValues eventValues = new ContentValues();
//
//        eventValues.put(CalendarContract.Events.CALENDAR_ID, 1);
//        eventValues.put(CalendarContract.Events.TITLE, "OCS");
//        eventValues.put(CalendarContract.Events.DESCRIPTION, "Clinic App");
//        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, "Nasik");
//        eventValues.put(CalendarContract.Events.DTSTART, startMillis);
//        eventValues.put(CalendarContract.Events.DTEND, endMillis);
//
//        //eventValues.put(Events.RRULE, "FREQ=DAILY;COUNT=2;UNTIL="+endMillis);
//        eventValues.put("eventStatus", 1);
//        eventValues.put("visibility", 3);
//        eventValues.put("transparency", 0);
//        eventValues.put(CalendarContract.Events.HAS_ALARM, 1);
//
//        Uri eventUri = getContentResolver().insert(Uri.parse(eventUriString), eventValues);
//        long eventID = Long.parseLong(eventUri.getLastPathSegment());
//
//        /***************** Event: Reminder(with alert) Adding reminder to event *******************/
//
//        String reminderUriString = "content://com.android.calendar/reminders";
//
//        ContentValues reminderValues = new ContentValues();
//
//        reminderValues.put("event_id", eventID);
//        reminderValues.put("minutes", 1);
//        reminderValues.put("method", 1);
//
//        Uri reminderUri = getContentResolver().insert(Uri.parse(reminderUriString), reminderValues);
//    }


