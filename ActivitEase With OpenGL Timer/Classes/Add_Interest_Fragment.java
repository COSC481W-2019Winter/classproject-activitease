package com.example.activitease;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Add_Interest_Fragment extends Fragment {

    /**
     * Variables which are input from the add interest page/form.
     * Example: My interest is pitching practice, and I would like to practice four times a week in one hour sessions.
     *
     * interestName = "pitching practice";
     * periodFreq = 4;
     * activityLength = 60.0;
     * periodSpanStr = "week". This String is matched with a double from the getPeriodSpan method.
     */

    /*
    String interestName;
    int periodFreq;
    double activityLength;
    String periodSpanStr;

    double basePeriodSpan;

    int numNotifications;

    //Setter methods
    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public void setPeriodFreq(int periodFreq) {
        this.periodFreq = periodFreq;
    }

    public void setActivityLength(double activityLength) {
        this.activityLength = activityLength;
    }
    public void setPeriodSpanStr(String periodSpanStr) {
        this.periodSpanStr = periodSpanStr;
    }

    public void setNumNotifications(int numNotifications) {
        this.numNotifications = numNotifications;
    }

    //Getter methods
    public String getInterestName() { return interestName; }
    public int getPeriodFreq() { return periodFreq; }
    public double getActivityLength() { return activityLength; }

    public String getPeriodSpanStr() { return periodSpanStr; }
    public double getBasePeriodSpan() { return basePeriodSpan; }

    public int getNumNotifications() { return numNotifications; }



     //   This method takes an input string period (day, week, month, or year)
     //   and returns a corresponding double in minutes of that length.

    public double getPeriodSpan (String periodSpanStr)
    {
        switch (periodSpanStr)
        {
            case "day":
                return 1440;
            case "week":
                return 10080;
            case "month":
                return 43800;
            case "year":
                return 525960;
            default: {
                System.out.println("Error; periodSpanStr not recognized. Invalid input.");
                System.exit(0);
                return 0;
            }
        }
    }

    */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_interest_page, container, false);

        String[] periodSpanTypes =
                {"Day", "Week", "Month", "Year"};
        Spinner periodSpanSpinner = (Spinner) v.findViewById(R.id.periodSpanInput);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        periodSpanSpinner.setAdapter(adapter);

        return v;

    }
}
