package com.example.activitease;

public class Interest {

    int id;
    String interestName;
    int periodFreq;
    int basePeriodSpan;
    int activityLength;
    int numNotifications;

    public Interest(String interestName, int periodFreq, int basePeriodSpan,
                    int activityLength, int numNotifications) {

        this.id = id;
        this.interestName = interestName;
        this.periodFreq = periodFreq;
        this.basePeriodSpan = basePeriodSpan;
        this.activityLength = activityLength;
        this.numNotifications = numNotifications;
    }

//    public Interest(int id, String string, String cursorString){
//        this.id = id;
//        this.name = name;
//        this.phone_number = phone_number;
//    }

    public Interest() {

    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public int getPeriodFreq() {
        return periodFreq;
    }

    public void setPeriodFreq(int activityAmount) {
        this.periodFreq = activityAmount;
    }

    public int getBasePeriodSpan() {
        return basePeriodSpan;
    }

    public void setBasePeriodSpan(int basePeriodSpan) {
        this.basePeriodSpan = basePeriodSpan;
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
