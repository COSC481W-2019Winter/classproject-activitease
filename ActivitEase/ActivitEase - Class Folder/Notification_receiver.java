package com.example.activitease;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Arrays;
import java.util.List;

public class Notification_receiver extends BroadcastReceiver {

    public final String CHANNEL_ID = "Personal Notification";
    private final int NOTIFICATION_ID = 101;
    public String[] name;
    public int rN = (int) System.currentTimeMillis();




    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();

        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        name = new String[10];
        Arrays.fill(name, "");
        int ll= 0;

        int k = 0;
        //String firstInterestName =
        for(Interest intr : interestList) {
            if (intr != null) {
                name[k] = intr.getInterestName();




                k++;
            }
            else
                break;
        }


        PendingIntent pendingIntent1 = PendingIntent.getActivity(context, NOTIFICATION_ID, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);


        // Interest 1
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent1)
                //.setSmallIcon(android.R.drawable.arrow_up_float)setSmallIcon(icon);
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(name[0])
                .setContentText("Hey, you! It's time to get this done!" )
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (name[0] != "")
            notificationManager.notify((rN), builder.build());


    }


}


