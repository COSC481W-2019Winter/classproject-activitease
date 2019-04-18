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

import static java.lang.String.format;


public class Home_Page_Fragment extends Fragment {
    TextView populateInterests;
    private Button[] interestBtns = new Button[10];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        populateInterests = view.findViewById(R.id.testDBPopulate);

        TextView populateInterests = view.findViewById(R.id.testDBPopulate);
        List<Interest> interestList = MainActivity.myDB.myDao().getInterests();

        // CODE FOR TEMP TABLE VIEW HERE
        String info = "Interest Name:   Activity Length:    Period Frequency:   base p. span:   notifications: times for notifs: \n";


        for(Interest intr : interestList) {
            if (intr != null) {
                String interestName = intr.getInterestName();
                int activityLength = intr.getActivityLength();
                int periodFreq = intr.getPeriodFreq();
                int basePeriodSpan = intr.getBasePeriodSpan();
                int numNotifications = intr.getNumNotifications();

                double[] tempNotifTimes = intr.getNotifTimes(intr.getNumNotifications());

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

        info += "number of interests: " + getInterestTableSz() + "\n";

        populateInterests.setText(info);

        //displayButtons(interestList);
        // CODE FOR BUTTON POPULATION BEGINS HERE

        // links the interest button array with all of the buttons on the content_main page.
        interestBtns[0] = view.findViewById(R.id.activity1);
        interestBtns[1] = view.findViewById(R.id.activity2);
        interestBtns[2] = view.findViewById(R.id.activity3);
        interestBtns[3] = view.findViewById(R.id.activity4);
        interestBtns[4] = view.findViewById(R.id.activity5);
        interestBtns[5] = view.findViewById(R.id.activity6);
        interestBtns[6] = view.findViewById(R.id.activity7);
        interestBtns[7] = view.findViewById(R.id.activity8);
        interestBtns[8] = view.findViewById(R.id.activity9);
        interestBtns[9] = view.findViewById(R.id.activity10);

        // Finds the current size of the interest table.
        int sz = interestList.size();

            /*
                Makes all currently used interest buttons visible, and sets their text to the name
                of the corresponding interest.
             */
        for (int i = 0; i < sz; i++) {
            interestBtns[i].setVisibility(View.VISIBLE);

            String periodSpan = "";

            if (interestList.get(i).getBasePeriodSpan() == 1)
                periodSpan = "day";
            else if (interestList.get(i).getBasePeriodSpan() == 7)
                periodSpan = "week";
            else if (interestList.get(i).getBasePeriodSpan() == 30)
                periodSpan = "month";
            else
                periodSpan = "year";
            // Getting minutes for time remaining
            int mRemaining = (int) interestList.get(i).getTimeRemaining();
            // Getting seconds for time remaining
            double sRemaining = (interestList.get(i).getTimeRemaining() - (int) interestList.get(i).getTimeRemaining())*60;

            String timeSpentString = MainActivity.convertToUnits(interestList.get(i));

            String buttonText = interestList.get(i).getInterestName() + "\n" +
                    interestList.get(i).getStreakCt() + " day streak \n" +
                    interestList.get(i).getPeriodFreq() + " time(s) " +
                    interestList.get(i).getActivityLength() + " minute(s) a " +
                    periodSpan + "\n Time remaining: "  + mRemaining + ":" +
                    String.format("%.0f", sRemaining) + timeSpentString;

            interestBtns[i].setText(buttonText);
        }
        // CODE FOR BUTTON POPULATION ENDS HERE
        //Sets all interests currentDates to today.
        String today = MainActivity.getCurrentDate();
        for(int i = 0; i < sz; i++){
            String interestName = interestList.get(i).getInterestName();
            Interest interest = MainActivity.myDB.myDao().loadInterestByName(interestName);
            interest.setCurrentDate(today);
            MainActivity.myDB.myDao().updateInterest(interest);
        }

        return view;
    }

    public static int getInterestTableSz() {
        return MainActivity.myDB.myDao().getInterests().size();
    }
}
