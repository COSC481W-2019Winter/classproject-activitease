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
import java.util.List;

public class notification_reciever extends BroadcastReceiver {

    private int[][][] hour_minute_second;

    //ArrayList<Integer[]>[] al = new ArrayList[10];
    //List<int[]> myList = new ArrayList<int[]>();
    //ArrayList<Notification> arrayListOfNotification = new ArrayList<Notification>();
    private ArrayList<Notification> arrayListOfNotification = new ArrayList<Notification>();

    private double[] notificationTimes;

    public double[] getNotificationTimes(){
        return  notificationTimes;
    }

    public void onReceive(Context context, Intent intent) {

        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();
        int numOfInterest = interestList.size();

        String firstInterestName = interestList.get(0).getInterestName();
        int numOfNotification = interestList.get(0).getNumNotifications();

        for (int i = 0; i < numOfInterest; i++) {
            //ArrayList<Notification> arrayListOfNotification_ = setArrayListOfNotification(interestList.get(i));
            String interestName = interestList.get(i).getInterestName();
            double[] notificationTimes = interestList.get(i).getNotifTimes();

            Log.d(interestName, "----------------------------------------------------------------");
            for(int j = 0; j < interestList.get(i).getNumNotifications(); j++){
                ArrayList<Notification> _arrayListOfNotification = new ArrayList<Notification>();
                int hour = (int) notificationTimes[j];
                int minute = (int) (notificationTimes[j] * 60 % 60);
                int second = (int) (notificationTimes[j] * 60 * 60 % 60);

                //Notification notification = new Notification(hour, minute, second);

                //_arrayListOfNotification.add(notification);
                Log.d(interestName,String.valueOf(hour + ":" + minute + ":" + second));


                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent repeating_intent = new Intent(context, MainActivity.class);
                repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                PendingIntent pendingIntent = PendingIntent.getActivity(context, 100*i + j, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                int icon = R.mipmap.ic_launcher;

                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        //.setSmallIcon(android.R.drawable.arrow_up_float)setSmallIcon(icon);
                        .setSmallIcon(icon)
                        .setContentTitle(interestName)
                        .setContentText(hour + ":" + minute + ":" + second)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true);
                notificationManager.notify(100*i + j, builder1.build());
            }
        }

    }

}