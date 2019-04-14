package com.example.activitease;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
    }


    public void popNotification(List<Interest> interestList) {

        for (int i = 0; i < interestList.size(); i++) {

            String interestName = interestList.get(i).getInterestName();
            double[] notificationTimes = interestList.get(i).getNotifTimes();
            int numOfNotification = interestList.get(i).getNumNotifications();

            Log.d(interestName, "----------------------------------------------------------------");
            for (int j = 0; j < numOfNotification; j++) {

                ArrayList<com.example.activitease.Notification> _arrayListOfNotification = new ArrayList<com.example.activitease.Notification>();

                int hour = (int) notificationTimes[j];
                int minute = (int) (notificationTimes[j] * 60 % 60);
                int second = (int) (notificationTimes[j] * 60 * 60 % 60);

                Log.d(interestName, String.valueOf(hour + ":" + minute + ":" + second));

                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.HOUR_OF_DAY, minute);
                calendar.set(Calendar.HOUR_OF_DAY, second);

                Intent intent = new Intent(getApplicationContext(), Notification_receiver.class);//=================================
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100 * i + j, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            }
        }
    }

}
