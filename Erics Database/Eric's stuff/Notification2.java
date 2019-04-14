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

public class Notification2 extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {

        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();
        int numOfInterest = interestList.size();

        for (int i = 0; i < numOfInterest; i++) {
            //ArrayList<Notification> arrayListOfNotification_ = setArrayListOfNotification(interestList.get(i));
            String interestName = interestList.get(i).getInterestName();
            double[] notificationTimes = interestList.get(i).getNotifTimes();
            int numOfNotification = interestList.get(i).getNumNotifications();

            Log.d(interestName, "**********************************************************************");
            Log.d(interestName, String.valueOf(numOfNotification));
            for (int j = 0; j < numOfNotification; j++) {

                int hour = (int) notificationTimes[j];
                int minute = (int) (notificationTimes[j] * 60 % 60);
                int second = (int) (notificationTimes[j] * 60 * 60 % 60);

                Log.d(interestName, String.valueOf(hour + ":" + minute + ":" + second));
            }
        }

        String interestName = interestList.get(7).getInterestName();

        double[] notificationTimes = interestList.get(7).getNotifTimes();

        int hour, minute, second;

        Log.d(interestName, "----------------------------------------------------------------");
        Log.d(interestName, "----------------------------------------------------------------");

        for(int j = 0; j < interestList.get(7).getNumNotifications(); j++){
            ArrayList<Notification> _arrayListOfNotification = new ArrayList<Notification>();
            hour = (int) notificationTimes[j];
            minute = (int) (notificationTimes[j] * 60 % 60);
            second = (int) (notificationTimes[j] * 60 * 60 % 60);
            Log.d(interestName,String.valueOf(hour + ":" + minute + ":" + second));
        }


        hour = (int) notificationTimes[4];
        minute = (int) (notificationTimes[4] * 60 % 60);
        second = (int) (notificationTimes[4] * 60 * 60 % 60);



        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.HOUR_OF_DAY, minute);
        calendar.set(Calendar.HOUR_OF_DAY, second);

        NotificationManager notificationManager1 = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent1 = new Intent(context, MainActivity.class);
        repeating_intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, 101, repeating_intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        int icon1 = R.mipmap.ic_launcher;

        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent1)
                //.setSmallIcon(android.R.drawable.arrow_up_float)setSmallIcon(icon);
                .setSmallIcon(icon1)
                .setContentTitle(interestName)
                .setContentText(String.valueOf(hour + ":" + minute + ":" + second))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
        notificationManager1.notify(101, builder2.build());

    }

}
