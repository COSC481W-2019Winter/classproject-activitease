package com.example.activitease;

import android.app.Service;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.activitease.MainActivity.myDB;

public class notificationService extends Service
{

    @Override
    public void onCreate() {
        // Display a notification about us starting
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        Log.i("Local Service","Service Created");

    }

    public class LocalBinder extends Binder {
        notificationService getService() {
            return notificationService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int requestId) {
        Log.i("LocalService", "Received start id " + requestId + ": " + intent);
        int notifHour, notifMinute, notifSecond, currHour, currMin, currSec, delayHour, delayMin, delaySec;
        delayMin = 0;
        delayHour = 0;
        delaySec = 0;
        long totalDelaySeconds;
        Interest theInterest;
        List<Interest> interestList = myDB.myDao().getInterests();

        int n = 0;
        for (int i = 0; i < interestList.size(); i++) {

            String interestName = interestList.get(i).getInterestName();
            theInterest = myDB.myDao().loadInterestByName(interestName);
            double[] notificationTimes = theInterest.getNotifTimes(theInterest.getNumNotifications());
            int numOfNotification = theInterest.getNumNotifications();

            Log.d(interestName, "----------------------------------------------------------------");
            for (int j = 0; j < numOfNotification; j++) {

                //ArrayList<Notification> _arrayListOfNotification = new ArrayList<Notification>();

                notifHour = (int) notificationTimes[j];
                notifMinute = (int) (notificationTimes[j] * 60 % 60);
                notifSecond = (int) (notificationTimes[j] * 60 * 60 % 60);

                // Notification notification = new Notification(notifHour, minute, second);
                // _arrayListOfNotification.add(notification);
                Log.d(interestName, String.valueOf(notifHour + ":" + notifMinute + ":" + notifSecond));

                Calendar calendar = Calendar.getInstance();
                //Get notification time in notifHour, minute, second form
                calendar.set(Calendar.HOUR, notifHour);
                calendar.set(Calendar.MINUTE, notifMinute);
                calendar.set(Calendar.SECOND, notifSecond);

                //Get the current time in hour minute second form
                Calendar now = Calendar.getInstance();

                currHour = (int) now.get(Calendar.HOUR_OF_DAY);
                currMin = (int) now.get(Calendar.MINUTE);
                currSec = (int) now.get(Calendar.SECOND);

                int minsUntilNextHour = 0;
                int secsUntilNextMinute = 0;
                if(notifHour >= currHour)
                {
                    delayHour = (notifHour - currHour);  //Notification Hour - Current time. Ex. Notif = 10 curr = 8 delay = 10 - 8 = 2 hours until next notification

                }
                else if(notifHour < currHour)
                {
                    delayHour = (24 - currHour) + notifHour; //Hours till next day + till notification Ex. Curr = 18 Notif = 17 then (24 - 18) = 6 + notif = 23 hours till next notification
                }
                if(notifMinute >= currMin)
                {
                    delayMin = notifMinute - currMin;
                }
                else if(notifMinute < currMin)
                {
                    minsUntilNextHour = 60 - currMin;
                    delayHour = delayHour - 1;
                    delayMin = minsUntilNextHour + notifMinute;
                }
                if(notifSecond >= currSec)
                {
                    delaySec = notifSecond - currSec;
                }
                else if(notifMinute < currMin)
                {
                    secsUntilNextMinute = 60 - currMin;
                    delayMin =- 1;
                    delaySec = secsUntilNextMinute + notifSecond;
                }

                totalDelaySeconds = (delayHour * 60 * 60) + (delayMin * 60) + delaySec;

                //Intent intent = new Intent(getApplicationContext(), notification_reciever.class);
                Context context = getApplicationContext();
                notifThread notif = new notifThread();
                notif.notifThread(interestName, i + j, context);
                Thread thread = new Thread(notif);
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                Log.d("delay", interestName + " notification delay is " + totalDelaySeconds + " seconds.");
                scheduler.scheduleAtFixedRate(thread, totalDelaySeconds, 86400, TimeUnit.SECONDS);

            }
        }
        return START_STICKY;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onDestroy() {
        // Cancel the persistent notification.

    }
}

