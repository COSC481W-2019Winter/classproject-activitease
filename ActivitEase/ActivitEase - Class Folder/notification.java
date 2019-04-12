package com.example.activitease;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class notification implements Runnable
{
    String name;
    Context context;
    public final String CHANNEL_ID = "Personal Notification";
    notification(String name, Context c)
    {
        this.name = name;
        this.context = c;
    }

    @Override
    public void run()
    {

        // Intent is triggered if the notification is selected
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

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
        notificationManager.notify(0, builder1.build());
    }
}