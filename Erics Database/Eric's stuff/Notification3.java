package com.example.activitease;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Notification3 extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {

        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();
        int numOfInterest = interestList.size();

        String interestName = interestList.get(3).getInterestName();

        double[] notificationTimes = interestList.get(3).getNotifTimes();

        int hour, minute, second;

        Log.d(interestName, "----------------------------------------------------------------");
        Log.d(interestName, "----------------------------------------------------------------");
        Log.d(interestName, "----------------------------------------------------------------");
        Log.d(interestName, "----------------------------------------------------------------");
        for(int j = 0; j < interestList.get(3).getNumNotifications(); j++){
            ArrayList<Notification> _arrayListOfNotification = new ArrayList<Notification>();
            hour = (int) notificationTimes[j];
            minute = (int) (notificationTimes[j] * 60 % 60);
            second = (int) (notificationTimes[j] * 60 * 60 % 60);
            Log.d(interestName,String.valueOf(hour + ":" + minute + ":" + second));
        }

        hour = (int) notificationTimes[7];
        minute = (int) (notificationTimes[7] * 60 % 60);
        second = (int) (notificationTimes[7] * 60 * 60 % 60);
        Log.d(interestName,String.valueOf(hour + ":" + minute + ":" + second));

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.HOUR_OF_DAY, minute);
        calendar.set(Calendar.HOUR_OF_DAY, second);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        PendingIntent pendingIntent = PendingIntent.getActivity(context, 103, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        int icon = R.mipmap.ic_launcher;

        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                //.setSmallIcon(android.R.drawable.arrow_up_float)setSmallIcon(icon);
                .setSmallIcon(icon)
                .setContentTitle(interestName)
                .setContentText(String.valueOf(hour + ":" + minute + ":" + second))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
        notificationManager.notify(103, builder1.build());

    }
}
