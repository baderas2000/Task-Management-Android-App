package com.example.se2_team06.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "date_table")
public class Date {
    @PrimaryKey(autoGenerate = true)
    private int dateID;
    @ColumnInfo(name = "day")
    private int day;
    @ColumnInfo(name = "month")
    private int month;
    @ColumnInfo(name = "year")
    private int year;

    public Date(int day, int month, int year){
        if(!checkDMY(day, month, year)) {
            System.out.print("Date is invalid");
        }

        this.day = day;
        this.month = month;
        this.year = year;
    }

    private boolean checkDMY(int day, int month, int year){
        boolean result = true;
        int daysForMonth = monthDays(month);
        if(day <= 0 || day > daysForMonth) result = false;
        if(month <= 0 || month > 12) result = false;
        return result;
    }

    private int monthDays(int month){
        if(month == 2){
            return 28;
        }else if(month%2 == 0){
            return 30;
        }else{
            return 31;
        }
    }

    public boolean equals (Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Date date = (Date) o;
        return this.getDay() == date.getDay() &&
                this.getMonth() == date.getMonth() &&
                this.getYear() == date.getYear();

    }

    @Override
    public String toString () {
        StringBuffer buffer = new StringBuffer();
        int month = this.getMonth()+1;
        buffer.append(this.getDay()).append(".").append(month).append(".").append(this.getYear());
        return buffer.toString();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDateID() {
        return dateID;
    }

    public void setDateID(int dateID) {
        this.dateID = dateID;
    }
}
