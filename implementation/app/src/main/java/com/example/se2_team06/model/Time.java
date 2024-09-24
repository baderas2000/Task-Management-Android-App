package com.example.se2_team06.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Time {
    @PrimaryKey(autoGenerate = true)
    private int timeID;
    @ColumnInfo(name = "hour")
    private int hour;
    @ColumnInfo(name = "minute")
    private int minute;


    public Time(int hour, int minute){
        if(!checkHM(hour, minute)){
            System.out.print("Time is invalid");
        }

        this.hour = hour;
        this.minute = minute;
    }


    private boolean checkHM(int hour, int minute){
        boolean result = true;
        if(hour < 0 || hour > 24) result = false;
        if(minute < 0 || minute > 60) result = false;
        return result;
    }

    public boolean equals (Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Time time = (Time) o;
        return this.getMinute() == time.getMinute() &&
                this.getHour() == time.getHour();

    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.getHour()).append(":").append(this.getMinute());
        return buffer.toString();
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }
}
