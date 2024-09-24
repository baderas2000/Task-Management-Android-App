package com.example.se2_team06.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;

@Entity(tableName = "checklist_table")
public class Checklist extends Task {

    @Embedded
    public Date deadline = new Date(1,1,2022);

    @Embedded
    private Time time = new Time(0, 0);

    @ColumnInfo(name = "type")
    private ETaskType type = ETaskType.PRIVATE;

    public Checklist(String title, String description, EColor color, EStatus status, EPriority priority, String subclass, Date deadline, ETaskType type, Time time) {
        super(title, description, color, status, priority, subclass);
        this.type = type;
        this.deadline = deadline;
        this.time = time;
    }

    @Override
    public boolean equals (Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Checklist checklist = (Checklist) o;
        return super.equals(o) &&
                checklist.getType().equals(this.getType()) &&
                checklist.getDeadline().equals(this.getDeadline()) &&
                checklist.getTime().equals(this.getTime());
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(super.toString());
        buffer.append(this.getType().toString()).append("\n");
        buffer.append("DeadLine:\n").append(this.addIndent(this.getDeadline().toString())).append("\n");
        buffer.append("Time:\n").append(this.addIndent(this.getTime().toString())).append("\n");
        buffer.append(this.getTime().toString()).append("\n");
        return buffer.toString();
    }

    public Date getDeadline(){
        return this.deadline;
    }

    public void setDeadline(Date updatedDeadline){
        this.deadline = updatedDeadline;
    }

    public ETaskType getType(){
        return this.type;
    }

    public void setType(ETaskType updatedType){
        this.type = updatedType;
    }

    public Time getTime(){
        return this.time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
