package com.example.activiteasev5;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.List;

public class Notification_receiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();
        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        /*int numOfInterest = interestList.size();

        for (int i = 0; i < numOfInterest; i++) {
            System.out.println(interestList.get(i).getInterestName());
        }

        String firstInterestName = interestList.get(0).getInterestName();
*/
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        int icon = R.mipmap.ic_launcher;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                //.setSmallIcon(android.R.drawable.arrow_up_float)setSmallIcon(icon);
                .setSmallIcon(icon)
                //.setContentTitle(firstInterestName)
                .setContentText("Hey, you! It's time to get this done!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }

}

