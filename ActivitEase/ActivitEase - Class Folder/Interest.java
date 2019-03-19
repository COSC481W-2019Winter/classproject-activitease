package com.example.activitease20;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "interests")
public class Interest {
    // Will be deleted and methods will move to user, see
    // architecture design

    @PrimaryKey
    private String interestName;

    @ColumnInfo()
    private int periodFreq;
    @ColumnInfo()
    private int basePeriodSpan;
    @ColumnInfo()
    private int activityLength;
    @ColumnInfo()
    private int numNotifications;
    @ColumnInfo()
    private int streakCt;

    public Interest() {

    }

    public Interest(String interestName, int periodFreq, int basePeriodSpan,
                         int activityLength, int numNotifications) {
        this.interestName = interestName;
        this.periodFreq = periodFreq;
        this.basePeriodSpan = basePeriodSpan;
        this.activityLength = activityLength;
        this.numNotifications = numNotifications;
    }

    public void loadInterest(String interestName, int periodFreq, int basePeriodSpan,
                             int activityLength, int numNotifications, int streakCt)
    {
        this.interestName = interestName;
        this.periodFreq = periodFreq;
        this.basePeriodSpan = basePeriodSpan;
        this.activityLength = activityLength;
        this.numNotifications = numNotifications;
        this.streakCt = streakCt;
    }

    public void setInterestName(String interestName)
    { this.interestName = interestName; }

    public void setPeriodFreq(int periodFreq)
    { this.periodFreq = periodFreq; }

    public void setBasePeriodSpan(int basePeriodSpan)
    {  this.basePeriodSpan = basePeriodSpan;  }

    public void setActivityLength(int activityLength)
    { this.activityLength = activityLength; }

    public void setNumNotifications(int numNotifications)
    { this.numNotifications = numNotifications; }

    public void setStreakCt(int streakCt)
    { this.streakCt = streakCt;  }

    public String getInterestName() { return this.interestName; }

    public int getPeriodFreq() { return periodFreq; }
    public int getBasePeriodSpan() { return basePeriodSpan; }
    public int getActivityLength() { return activityLength; }
    public int getNumNotifications() { return numNotifications; }
    public int getStreakCt() {return streakCt; }
}
