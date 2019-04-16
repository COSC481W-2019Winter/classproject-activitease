package com.example.activitease;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


/**
 * This class constructs the table for the MyDB class, and can serve as an interest object.
 */
@Entity(tableName = "interests")
public class Interest {
    // Will be deleted and methods will move to user, see
    // architecture design

    /*
      Example of an interest:
      I want to practice 'basketball'(1) for '40 minutes'(2), '5 times'(3) a 'week'(4).
      I would like to have '2 notifications'(5) for my interest.
       These variables are columns of the 'interests' table. I will explain their functions here.
          'interestName' is the name of an interest. (1)
          'activityLength' is how long an activity lasts (2).
          'periodFreq' the number of times the interest is repeated during a period (3).
          'basePeriodSpan' is how often an activity is done, converted to an integer.
          In the case of the example, the basePeriodSpan will be '7', because this integer
          represents 7 days. (4)
          'numNotifications' is the number of notifications set for an interest (5).
          'timeRemaining' is a variable for the timer to keep track of the time remaining for an interest.
          'numIterations' is a technical variable for the OpenGL timer.
          'streakCt' is the number of days that an interest has been continuously been fulfilled.
          'totalTimeSpent' is the total minutes spent on an interest over time.
          'streakCTBool' keeps track of if an interest's streak has been maintained in the past 24 hours.
          'streakCTBool' returns true if it has been maintained, and returns false if it hasn't.
   */
    // Will be deleted and methods will move to user, see
    // architecture design
    @PrimaryKey
    @NonNull
    private String interestName;

    /*
        The rest are columns of the 'interests' table.
     */
    @ColumnInfo()
    private int periodFreq;
    @ColumnInfo()
    private int basePeriodSpan;
    @ColumnInfo()
    private int activityLength;
    @ColumnInfo()
    private double timeRemaining;
    @ColumnInfo()
    private int numNotifications;
    @ColumnInfo()
    private int streakCt;
    @ColumnInfo()
    private int numIterations = 0;
    @ColumnInfo()
    private double totalTimeSpent;
    @ColumnInfo()
    private boolean streakCTBool;

    @ColumnInfo()
    private String currentDate;
    @ColumnInfo()
    private String lastDate;

    @ColumnInfo()
    private int periodRemaining;

    // Large number of notification times. This system may be altered later.
    @ColumnInfo()
    private double notifTime1;
    @ColumnInfo()
    private double notifTime2;
    @ColumnInfo()
    private double notifTime3;
    @ColumnInfo()
    private double notifTime4;
    @ColumnInfo()
    private double notifTime5;
    @ColumnInfo()
    private double notifTime6;
    @ColumnInfo()
    private double notifTime7;
    @ColumnInfo()
    private double notifTime8;
    @ColumnInfo()
    private double notifTime9;
    @ColumnInfo()
    private double notifTime10;


    @Ignore
    public Interest() {

    }

    /*
    Constructor for Interest objects, which can easily be added to the 'interests' table,
    and then into the database.
     */
    public Interest(String interestName, int periodFreq, int basePeriodSpan,
                         int activityLength, double timeRemaining, int numNotifications, int streakCt, int numIterations, double totalTimeSpent) {
        this.interestName = interestName;
        this.periodFreq = periodFreq;
        this.basePeriodSpan = basePeriodSpan;
        this.activityLength = activityLength;
        this.timeRemaining = timeRemaining;
        this.numNotifications = numNotifications;
        this.streakCt = streakCt;
        this.numIterations = numIterations;

        periodRemaining = basePeriodSpan;

        this.totalTimeSpent = totalTimeSpent;
    }

    public void setInterestName(String interestName)
    { this.interestName = interestName; }

    public void setPeriodFreq(int periodFreq)
    { this.periodFreq = periodFreq; }

    public void setBasePeriodSpan(int basePeriodSpan)
    {  this.basePeriodSpan = basePeriodSpan;  }

    public void setTimeRemaining(double timeRemaining)
    {
        this.timeRemaining = timeRemaining;
    }


    public void setActivityLength(int activityLength)
    { this.activityLength = activityLength; }

    public void setNumNotifications(int numNotifications)
    { this.numNotifications = numNotifications; }

    public void setStreakCt(int streakCt)
    { this.streakCt = streakCt;  }

    public void setStreakCTBool(Boolean streakCTBool) { this.streakCTBool = streakCTBool;}
    public void setCurrentDate(String currentDate) { this.currentDate = currentDate;}
    public void setLastDate(String lastDate) { this.lastDate = lastDate;}

    public void addTimeSpent(double time) {this.totalTimeSpent += time;}

    public void setNumIterations(int iterations)
    {
        this.numIterations = iterations;
    }

    public void setNotifTime1 (double notifTime) {notifTime1 = notifTime; }
    public void setNotifTime2 (double notifTime) {notifTime2= notifTime; }
    public void setNotifTime3 (double notifTime) {notifTime3 = notifTime; }
    public void setNotifTime4 (double notifTime) {notifTime4 = notifTime; }
    public void setNotifTime5 (double notifTime) {notifTime5 = notifTime; }
    public void setNotifTime6 (double notifTime) {notifTime6 = notifTime; }
    public void setNotifTime7 (double notifTime) {notifTime7 = notifTime; }
    public void setNotifTime8 (double notifTime) {notifTime8 = notifTime; }
    public void setNotifTime9 (double notifTime) {notifTime9 = notifTime; }
    public void setNotifTime10 (double notifTime) {notifTime10 = notifTime; }

    public void setPeriodRemaining(int periodRemaining) { this.periodRemaining = periodRemaining; }

    public void setNotifTimes(int numNotifications) {

        double[] notifTimes = new double[10];

        // The amount of time (in hours) that passes before another notification goes off.
        double timeBetweenNotif = (float) 1.30;
        double time = 9; //9AM is the earliest that notifications should go off.

        for (int i = 0; i < 10; i++) {
            notifTimes[i] = time + timeBetweenNotif;
            time += timeBetweenNotif;
            notifTimes[i] = (float) Math.round(notifTimes[i] * 10) / 10;
        }

        switch (numNotifications) {
            case 1:
                notifTime1 = notifTimes[4];
                break;
            case 2:
                notifTime1 = notifTimes[4];
                notifTime2 = notifTimes[7];
                break;
            case 3:
                notifTime1 = notifTimes[1];
                notifTime2 = notifTimes[5];
                notifTime3 = notifTimes[9];
                break;
            case 4:
                notifTime1 = notifTimes[1];
                notifTime2 = notifTimes[3];
                notifTime3 = notifTimes[6];
                notifTime4 = notifTimes[9];
                break;
            case 5:
                notifTime1 = notifTimes[0];
                notifTime2 = notifTimes[2];
                notifTime3 = notifTimes[4];
                notifTime4 = notifTimes[6];
                notifTime5 = notifTimes[8];
                break;
            case 6:
                notifTime1 = notifTimes[0];
                notifTime2 = notifTimes[2];
                notifTime3 = notifTimes[4];
                notifTime4 = notifTimes[6];
                notifTime5 = notifTimes[8];
                notifTime6 = notifTimes[9];
                break;
            case 7:
                notifTime1 = notifTimes[0];
                notifTime2 = notifTimes[1];
                notifTime3 = notifTimes[2];
                notifTime4 = notifTimes[4];
                notifTime5 = notifTimes[6];
                notifTime6 = notifTimes[8];
                notifTime7 = notifTimes[9];
                break;
            case 8:
                notifTime1 = notifTimes[0];
                notifTime2 = notifTimes[1];
                notifTime3 = notifTimes[2];
                notifTime4 = notifTimes[3];
                notifTime5 = notifTimes[4];
                notifTime6 = notifTimes[6];
                notifTime7 = notifTimes[8];
                notifTime8 = notifTimes[9];
                break;
            case 9:
                notifTime1 = notifTimes[0];
                notifTime2 = notifTimes[1];
                notifTime3 = notifTimes[2];
                notifTime4 = notifTimes[3];
                notifTime5 = notifTimes[4];
                notifTime6 = notifTimes[6];
                notifTime7 = notifTimes[7];
                notifTime8 = notifTimes[8];
                notifTime9 = notifTimes[9];
                break;
            case 10:
                notifTime1 = notifTimes[0];
                notifTime2 = notifTimes[1];
                notifTime3 = notifTimes[2];
                notifTime4 = notifTimes[3];
                notifTime5 = notifTimes[4];
                notifTime6 = notifTimes[5];
                notifTime7 = notifTimes[6];
                notifTime8 = notifTimes[7];
                notifTime9 = notifTimes[8];
                notifTime10 = notifTimes[9];
                break;
            default:
                break;
        }
    }

    public String getInterestName() { return this.interestName; }

    public int getPeriodFreq() { return periodFreq; }
    public int getBasePeriodSpan() { return basePeriodSpan; }
    public int getActivityLength() { return activityLength; }
    public int getNumNotifications() { return numNotifications; }
    public double getTimeRemaining() {return timeRemaining;}
    public int getStreakCt() {return streakCt; }
    public double getTotalTimeSpent() {return totalTimeSpent; }
    public String getLastDate() {return lastDate;}
    public boolean getStreakCTBool() { return streakCTBool;}
    public String getCurrentDate() { return currentDate;}

    public int getNumIterations() { return numIterations; }
    public double getNotifTime1() { return notifTime1; }
    public double getNotifTime2() { return notifTime2; }
    public double getNotifTime3() { return notifTime3; }
    public double getNotifTime4() { return notifTime4; }
    public double getNotifTime5() { return notifTime5; }
    public double getNotifTime6() { return notifTime6; }
    public double getNotifTime7() { return notifTime7; }
    public double getNotifTime8() { return notifTime8; }
    public double getNotifTime9() { return notifTime9; }
    public double getNotifTime10() { return notifTime10; }

    public int getPeriodRemaining() { return periodRemaining; }

    public double[] getNotifTimes(int numNotifications) {
        double[] notifTimes = new double[numNotifications];

        if(numNotifications > 0)
            notifTimes[0] = notifTime1;
        if (numNotifications > 1) {
            notifTimes[1] = notifTime2;
        } if (numNotifications > 2) {
            notifTimes[2] = notifTime3;
        } if (numNotifications > 3) {
            notifTimes[3] = notifTime4;
        } if (numNotifications > 4) {
            notifTimes[4] = notifTime5;
        } if (numNotifications > 5) {
            notifTimes[5] = notifTime6;
        } if (numNotifications > 6) {
            notifTimes[6] = notifTime7;
        } if (numNotifications > 7) {
            notifTimes[7] = notifTime8;
        } if (numNotifications > 8) {
            notifTimes[8] = notifTime9;
        } if (numNotifications > 9) {
            notifTimes[9] = notifTime10;
        }

        return notifTimes;
    }

    // static method presets the notification times.
  //  public static double[] presetNotifTimes() {

   // }
}
