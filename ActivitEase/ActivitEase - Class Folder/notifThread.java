package com.example.activitease;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class notifThread implements Runnable
{
    String name;
    int requestId;
    Context context;
    public final String CHANNEL_ID = "Personal Notification";


    public void notifThread(String name, int requestId, Context context)
    {
        this.name = name;
        this.requestId = requestId;
        this.context = context;
    }

    @Override
    public void run() {

        // Intent is triggered if the notification is selected
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, requestId, intent, 0);

        NotificationManager mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int icon = R.mipmap.ic_launcher;

        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pIntent)
                //.setSmallIcon(android.R.drawable.arrow_up_float)setSmallIcon(icon);

                .setSmallIcon(icon)
                .setContentTitle(name)
                .setContentText("Don't forget to work on " + name + "!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);
        mNM.notify(requestId, builder1.build());
    }
}
