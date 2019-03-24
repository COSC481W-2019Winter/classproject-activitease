package com.example.activitease;

public class Interest {
    // Will be deleted and methods will move to user, see
    // architecture design

    static  boolean timerRunning = true;
    private String interestName;
    private String periodFreq;
    private int activityLength;
    private int activityAmount;
    private int numNotifications;
    private String numNotificationSpan;


    public Interest(String interestName, String periodFreq, int activityLength, int activityAmount, int numNotifications, String numNotificationSpan) {
        this.interestName = interestName;
        this.periodFreq = periodFreq;
        this.activityLength = activityLength;
        this.activityAmount = activityAmount;
        this.numNotifications = numNotifications;
        this.numNotificationSpan = numNotificationSpan;
    }

    public Interest() {

    }
    public String getInterestName()
    {
        return this.interestName;
    }

    public int getActivityLength()
    {
        return activityLength;
    }
    public String getPeriodFreq()
    {
        return periodFreq;
    }
    public int getActivityAmount()
    {
        return activityAmount;
    }
    public int getNumNotifications()
    {
        return numNotifications;
    }
    public String getNumNotificationSpan()
    {
        return numNotificationSpan;
    }
    public void setInterestName(String interestName)
    {
        this.interestName = interestName;
    }
    public void setActivityLength(int activityLength)
    {
        this.activityLength = activityLength;
    }
    public void setPeriodFreq(String periodFreq)
    {
        this.periodFreq = periodFreq;
    }
    public void setActivityAmount(int activityAmount)
    {
        this.activityAmount = activityAmount;
    }
    public void setNumNotifications(int numNotifications)
    {
        this.numNotifications = numNotifications;
    }
    public void setNumNotificationSpan(String numNotificationSpan)
    {
        this.numNotificationSpan = numNotificationSpan;
    }

    public static boolean getTimerRunning()
    {
        return timerRunning;
    }
}
