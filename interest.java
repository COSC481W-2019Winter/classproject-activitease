package com.example.activitease;

import android.view.View;
// Will be deleted and methods will move to user, see
// architecture design
public class interest {

    boolean startTimer;
    boolean stopTimer;
    private String interestName = "interest";
    private int periodFreq;
    private String basePeriodSpan;
    private int activityLength;
    private int numNotifications;
    private int updateTimer;

    public void setInterestName(String interestName)
    {
        this.interestName = interestName;
    }
    public String getInterestName()
    {
        return this.interestName;
    }
    public void setPeriodFreq(String pfreq){this.periodFreq = Integer.parseInt(pfreq); }
    public void setActivityLength(String actLength){this.activityLength = Integer.parseInt(actLength);}
    public void setBasePeriodSpan(String bps){this.basePeriodSpan = bps;}
    public void loadInterest(String interestName, int periodFreq, String basePeriodSpan, int activityLength, int numNotifications)
    {
        this.interestName = interestName;
        this.periodFreq = periodFreq;
        this.basePeriodSpan = basePeriodSpan;
        this.activityLength = activityLength;
        this.numNotifications = numNotifications;
    }



}
