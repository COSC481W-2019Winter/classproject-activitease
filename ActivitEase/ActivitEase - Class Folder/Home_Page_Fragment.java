package com.example.activitease;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import static java.lang.String.*;


public class Home_Page_Fragment extends Fragment {
    TextView populateInterests;

    private Button interest1;
    private Button interest2;
    private Button interest3;
    private Button interest4;
    private Button interest5;
    private Button interest6;
    private Button interest7;
    private Button interest8;
    private Button interest9;
    private Button interest10;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        populateInterests = view.findViewById(R.id.testDBPopulate);


        TextView populateInterests = view.findViewById(R.id.testDBPopulate);
        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();

        String info = "Interest Name:   Activity Length:    Period Frequency:   base p. span:   notifications: times for notifs: \n";


        for(Interest intr : interestList) {
            if (intr != null) {
                String interestName = intr.getInterestName();
                int activityLength = intr.getActivityLength();
                int periodFreq = intr.getPeriodFreq();
                int basePeriodSpan = intr.getBasePeriodSpan();
                int numNotifications = intr.getNumNotifications();

                double[] tempNotifTimes = intr.getNotifTimes();

                info += interestName + "    " + activityLength + "      " + periodFreq + "    " +
                        basePeriodSpan + "   " + numNotifications + "      ";

                for (int i = 0; i < numNotifications; i++) {
                    info += format("%.2f", tempNotifTimes[i]) + "   ";
                }
                info += "\n";
            }
            else
                break;
        }

        info += "number of interests: " + MainActivity.getInterestTableSz() + "\n";

        populateInterests.setText(info);

        return view;
    }

    public void addNewInterest() {

    }
}
