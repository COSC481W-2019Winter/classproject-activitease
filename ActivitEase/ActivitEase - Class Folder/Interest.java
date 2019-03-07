package com.example.activitease;

public class Interest {
    // Will be deleted and methods will move to user, see
    // architecture design

    boolean startTimer;
    boolean stopTimer;
    private String interestName;
    private int periodFreq;
    private int basePeriodSpan;
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
    public void loadInterest(String interestName, int periodFreq, int basePeriodSpan, int activityLength, int numNotifications)
    {
        this.interestName = interestName;
        this.periodFreq = periodFreq;
        this.basePeriodSpan = basePeriodSpan;
        this.activityLength = activityLength;
        this.numNotifications = numNotifications;
    }



}
