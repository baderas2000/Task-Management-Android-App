package com.example.se2_team06.model;

import androidx.room.*;

@Entity(tableName = "appointment_table")
public class Appointment extends Task {

    @ColumnInfo(name = "location")
    String location = "";


    @Embedded
    Date date = new Date(2,11,2022);

    @ColumnInfo(name = "type")
    ETaskType type = ETaskType.PRIVATE;

    public Appointment(String title, String description, EColor color, EStatus status, EPriority priority, String subclass,String location, Date date, ETaskType type) { // Time time
        super(title, description, color, status, priority, subclass);
        this.location = location;
        this.date = date;
        this.type = type;
    }

    @Override
    public boolean equals (Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        return super.equals(o) &&
                appointment.getType().equals(this.getType()) &&
                appointment.getDate().equals(this.getDate()) &&
                appointment.getLocation().equals(this.getLocation());
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(super.toString());
        buffer.append("Location: ").append(this.getLocation()).append("\n");
        buffer.append("Date:\n").append(this.addIndent(this.getDate().toString())).append("\n");
        buffer.append("Type: ").append(this.getType()).append("\n");
        return buffer.toString();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ETaskType getType() {
        return type;
    }

    public void setType(ETaskType type) {
        this.type = type;
    }

}
