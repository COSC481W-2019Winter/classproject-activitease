package com.example.activitease;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;



public class Custom_Notification_Fragment extends Fragment {
    private String iName;
    private int numNotifications;
    private TextView notiftex1,notiftex2,notiftex3,notiftex4,notiftex5,notiftex6,notiftex7,notiftex8,notiftex9,notiftex10;
    private TimePicker timePicker1, timePicker2, timePicker3, timePicker4, timePicker5, timePicker6, timePicker7, timePicker8, timePicker9, timePicker10;

    TextView interestName;
    Interest thisInterest = new Interest();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_notification_page, container, false);
        interestName = v.findViewById(R.id.interestName);

        interestName.setText(iName);

        //custom notif text views
        notiftex1 = v.findViewById(R.id.notif1);
        notiftex2 = v.findViewById(R.id.notif2);
        notiftex3 = v.findViewById(R.id.notif3);
        notiftex4 = v.findViewById(R.id.notif4);
        notiftex5 = v.findViewById(R.id.notif5);
        notiftex6 = v.findViewById(R.id.notif6);
        notiftex7 = v.findViewById(R.id.notif7);
        notiftex8 = v.findViewById(R.id.notif8);
        notiftex9 = v.findViewById(R.id.notif9);
        notiftex10 = v.findViewById(R.id.notif10);

        //custom notif time pickers
        timePicker1 = v.findViewById(R.id.timePicker1);
        timePicker2 = v.findViewById(R.id.timePicker2);
        timePicker3 = v.findViewById(R.id.timePicker3);
        timePicker4 = v.findViewById(R.id.timePicker4);
        timePicker5 = v.findViewById(R.id.timePicker5);
        timePicker6 = v.findViewById(R.id.timePicker6);
        timePicker7 = v.findViewById(R.id.timePicker7);
        timePicker8 = v.findViewById(R.id.timePicker8);
        timePicker9 = v.findViewById(R.id.timePicker9);
        timePicker10 = v.findViewById(R.id.timePicker10);
        timePicker1.setIs24HourView(true);
        timePicker2.setIs24HourView(true);
        timePicker3.setIs24HourView(true);
        timePicker4.setIs24HourView(true);
        timePicker5.setIs24HourView(true);
        timePicker6.setIs24HourView(true);
        timePicker7.setIs24HourView(true);
        timePicker8.setIs24HourView(true);
        timePicker9.setIs24HourView(true);
        timePicker10.setIs24HourView(true);








        //Sets visible custom notification fields
        switch(thisInterest.getNumNotifications())
        {
            case 0:
                //do nothing
            case 1:
                notiftex1.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                break;
            case 2:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                break;
            case 3:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);

                break;
            case 4:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                notiftex4.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);
                timePicker4.setEnabled(true);
                break;
            case 5:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                notiftex4.setVisibility(View.VISIBLE);
                notiftex5.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                timePicker5.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);
                timePicker4.setEnabled(true);
                timePicker5.setEnabled(true);

                break;
            case 6:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                notiftex4.setVisibility(View.VISIBLE);
                notiftex5.setVisibility(View.VISIBLE);
                notiftex6.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                timePicker5.setVisibility(View.VISIBLE);
                timePicker6.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);
                timePicker4.setEnabled(true);
                timePicker5.setEnabled(true);
                timePicker6.setEnabled(true);
                break;
            case 7:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                notiftex4.setVisibility(View.VISIBLE);
                notiftex5.setVisibility(View.VISIBLE);
                notiftex6.setVisibility(View.VISIBLE);
                notiftex7.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                timePicker5.setVisibility(View.VISIBLE);
                timePicker6.setVisibility(View.VISIBLE);
                timePicker7.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);
                timePicker4.setEnabled(true);
                timePicker5.setEnabled(true);
                timePicker6.setEnabled(true);
                timePicker7.setEnabled(true);

                break;
            case 8:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                notiftex4.setVisibility(View.VISIBLE);
                notiftex5.setVisibility(View.VISIBLE);
                notiftex6.setVisibility(View.VISIBLE);
                notiftex7.setVisibility(View.VISIBLE);
                notiftex8.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                timePicker5.setVisibility(View.VISIBLE);
                timePicker6.setVisibility(View.VISIBLE);
                timePicker7.setVisibility(View.VISIBLE);
                timePicker8.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);
                timePicker4.setEnabled(true);
                timePicker5.setEnabled(true);
                timePicker6.setEnabled(true);
                timePicker7.setEnabled(true);
                timePicker8.setEnabled(true);
                break;
            case 9:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                notiftex4.setVisibility(View.VISIBLE);
                notiftex5.setVisibility(View.VISIBLE);
                notiftex6.setVisibility(View.VISIBLE);
                notiftex7.setVisibility(View.VISIBLE);
                notiftex8.setVisibility(View.VISIBLE);
                notiftex9.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                timePicker5.setVisibility(View.VISIBLE);
                timePicker6.setVisibility(View.VISIBLE);
                timePicker7.setVisibility(View.VISIBLE);
                timePicker8.setVisibility(View.VISIBLE);
                timePicker9.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);
                timePicker4.setEnabled(true);
                timePicker5.setEnabled(true);
                timePicker6.setEnabled(true);
                timePicker7.setEnabled(true);
                timePicker8.setEnabled(true);
                timePicker9.setEnabled(true);
                break;
            case 10:
                notiftex1.setVisibility(View.VISIBLE);
                notiftex2.setVisibility(View.VISIBLE);
                notiftex3.setVisibility(View.VISIBLE);
                notiftex4.setVisibility(View.VISIBLE);
                notiftex5.setVisibility(View.VISIBLE);
                notiftex6.setVisibility(View.VISIBLE);
                notiftex7.setVisibility(View.VISIBLE);
                notiftex8.setVisibility(View.VISIBLE);
                notiftex9.setVisibility(View.VISIBLE);
                notiftex10.setVisibility(View.VISIBLE);
                timePicker1.setVisibility(View.VISIBLE);
                timePicker2.setVisibility(View.VISIBLE);
                timePicker3.setVisibility(View.VISIBLE);
                timePicker4.setVisibility(View.VISIBLE);
                timePicker5.setVisibility(View.VISIBLE);
                timePicker6.setVisibility(View.VISIBLE);
                timePicker7.setVisibility(View.VISIBLE);
                timePicker8.setVisibility(View.VISIBLE);
                timePicker9.setVisibility(View.VISIBLE);
                timePicker10.setVisibility(View.VISIBLE);
                timePicker1.setEnabled(true);
                timePicker2.setEnabled(true);
                timePicker3.setEnabled(true);
                timePicker4.setEnabled(true);
                timePicker5.setEnabled(true);
                timePicker6.setEnabled(true);
                timePicker7.setEnabled(true);
                timePicker8.setEnabled(true);
                timePicker9.setEnabled(true);
                timePicker10.setEnabled(true);
                break;
        }

        if(timePicker1.isEnabled()) {
            double notifTime = thisInterest.getNotifTime1();
            int hour = (int) notifTime;
            double minute = (notifTime - hour) * 60;
            timePicker1.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker1.setCurrentMinute((int) theMinute);
        }
        if(timePicker2.isEnabled()) {
            double notifTime2 = thisInterest.getNotifTime2();
            int hour = (int) notifTime2;
            double minute = (notifTime2 - hour) * 60;
            timePicker2.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker2.setCurrentMinute((int) theMinute);
        }

        if(timePicker3.isEnabled()) {
            double notifTime3 = thisInterest.getNotifTime3();
            int hour = (int) notifTime3;
            double minute = (notifTime3 - hour) * 60;
            timePicker3.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker3.setCurrentMinute((int) theMinute);
        }
        if(timePicker4.isEnabled()) {
            double notifTime4 = thisInterest.getNotifTime4();
            int hour = (int) notifTime4;
            double minute = (notifTime4 - hour) * 60;
            timePicker4.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker4.setCurrentMinute((int) theMinute);
        }
        if(timePicker5.isEnabled()) {
            double notifTime5 = thisInterest.getNotifTime5();
            int hour = (int) notifTime5;
            double minute = (notifTime5 - hour) * 60;
            timePicker5.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker5.setCurrentMinute((int) theMinute);
        }
        if(timePicker6.isEnabled()) {
            double notifTime6 = thisInterest.getNotifTime6();
            int hour = (int) notifTime6;
            double minute = (notifTime6 - hour) * 60;
            timePicker6.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker6.setCurrentMinute((int) theMinute);
        }
        if(timePicker7.isEnabled()) {
            double notifTime7 = thisInterest.getNotifTime7();
            int hour = (int) notifTime7;
            double minute = (notifTime7 - hour) * 60;
            timePicker7.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker7.setCurrentMinute((int) theMinute);
        }
        if(timePicker8.isEnabled()) {
            double notifTime8 = thisInterest.getNotifTime8();
            int hour = (int) notifTime8;
            double minute = (notifTime8 - hour) * 60;
            timePicker8.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker8.setCurrentMinute((int) theMinute);
        }
        if(timePicker9.isEnabled()) {
            double notifTime9 = thisInterest.getNotifTime9();
            int hour = (int) notifTime9;
            double minute = (notifTime9 - hour) * 60;
            timePicker9.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker9.setCurrentMinute((int) theMinute);
        }
        if(timePicker10.isEnabled()) {
            double notifTime10 = thisInterest.getNotifTime10();
            int hour = (int) notifTime10;
            double minute = (notifTime10 - hour) * 60;
            timePicker10.setCurrentHour(hour);
            long theMinute = Math.round(minute);
            timePicker10.setCurrentMinute((int) theMinute);
        }

        return v;
    }

    public void initializeInterest (String iName) {
        this.iName = iName;
        thisInterest = MainActivity.myDB.myDao().loadInterestByName(iName);
        this.numNotifications = thisInterest.getNumNotifications();


    }
}
