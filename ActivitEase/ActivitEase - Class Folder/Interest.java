package com.example.databasefucking;

public class Interest {



    String interestName;
    int activityAmount;
    String activityPeriod;
    int activityLength;
    int numNotifications;

    public Interest(String interestName, int activityAmount, String activityPeriod,
                    int activityLength, int numNotifications) {

        this.interestName = interestName;
        this.activityAmount = activityAmount;
        this.activityPeriod = activityPeriod;
        this.activityLength = activityLength;
        this.numNotifications = numNotifications;
    }
    
    
    public Interest() {

    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public int getActivityAmount() {
        return activityAmount;
    }

    public void setActivityAmount(int activityAmount) {
        this.activityAmount = activityAmount;
    }

    public String getActivityPeriod() {
        return activityPeriod;
    }

    public void setActivityPeriod(String activityPeriod) {
        this.activityPeriod = activityPeriod;
    }

    public int getActivityLength() {
        return activityLength;
    }

    public void setActivityLength(int activityLength) {
        this.activityLength = activityLength;
    }

    public int getNumNotifications() {
        return numNotifications;
    }

    public void setNumNotifications(int numNotifications) {
        this.numNotifications = numNotifications;
    }
}
