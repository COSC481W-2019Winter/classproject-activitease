package com.example.activitease;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Arrays;
import java.util.List;

public class Notification_receiver8 extends BroadcastReceiver {

    public final String CHANNEL_ID = "Personal Notification";
    private final int NOTIFICATION_ID = 108;
    public final String CHANNEL_ID2 = "Personal Notification";




    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();

        Intent repeating_intent = new Intent(context, MainActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //int numOfInterest = interestList.size();

        //for (int i = 0; i < numOfInterest; i++) {
            //System.out.println(interestList.get(i).getInterestName());
       // }
        String[] name = new String[10];
        Arrays.fill(name, "");

        int k = 0;
        //String firstInterestName =
        for(Interest intr : interestList) {
            if (intr != null) {
                name[k] = intr.getInterestName();
                int activityLength = intr.getActivityLength();
                int periodFreq = intr.getPeriodFreq();
                int basePeriodSpan = intr.getBasePeriodSpan();
                int numNotifications = intr.getNumNotifications();

                double[] tempNotifTimes = intr.getNotifTimes();



                for (int i = 0; i < numNotifications; i++) {
                    // info += format("%.2f", tempNotifTimes[i]) + "   ";
                }
                k++;
            }
            else
                break;
        }



        PendingIntent pendingIntent8 = PendingIntent.getActivity(context, 108, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Interest 8

        NotificationCompat.Builder builder8 = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent8)
                //.setSmallIcon(android.R.drawable.arrow_up_float)setSmallIcon(icon);
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(name[7])
                .setContentText("Hey, you! It's time to get this done!")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        if (name[7] != "")
            notificationManager.notify((108), builder8.build());


    }

}
