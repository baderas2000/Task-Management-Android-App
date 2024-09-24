package com.example.se2_team06.model;

import java.lang.Override;

import androidx.annotation.NonNull;
import androidx.room.*;

import com.example.se2_team06.model.notifications.ActionType;

@Entity(tableName = "task_table")
public class Task implements ITask {

    //automatically assigns next uid
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "titel")
    String title = "title";

    @ColumnInfo(name = "description")
    String description = new String("description");

    @ColumnInfo(name = "color")
    EColor color = EColor.BLACK;

    @ColumnInfo(name = "status")
    EStatus status = EStatus.PLANNED;

    @ColumnInfo(name = "priority")
    EPriority priority = EPriority.LOW;

    @ColumnInfo(name = "subclass")
    String subclass = "Appointment";

    @Ignore
    private boolean isChecked = false;

    public Task(String title, String description, EColor color, EStatus status, EPriority priority, String subclass){
        this.title = title;
        this.description = description;
        this.color = color;
        this.status = status;
        this.priority = priority;
        this.subclass = subclass;
    }

    @Override
    public void insert(Task task) {

    }

    @Override
    public void update(Task Task) {

    }

    @Override
    public void delete(Task Task) {

    }

    @Override
    public void notifyAllObservers(ActionType actionType, Task task) {
    }

    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        Task task = (Task) o;

        return task.getTitle().equals(this.getTitle()) &&
                task.getDescription().equals(this.getDescription()) &&
                task.getColor().equals(this.getColor()) &&
                task.getStatus().equals(this.getStatus()) &&
                task.getPriority().equals(this.getPriority());
    }

    @NonNull
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Title: ").append(this.getTitle()).append("\n");
        buffer.append("Description: ").append(this.getDescription()).append("\n");
        buffer.append("Color: ").append(this.getColor().toString()).append("\n");
        buffer.append("Status: ").append(this.getStatus().toString()).append("\n");
        buffer.append("Priority: ").append(this.getPriority().toString()).append("\n");
        return buffer.toString();
    }

    public String addIndent (String string) {
        StringBuffer buffer = new StringBuffer();
        String stringFormatted = string.replaceAll("\\n", "\n   ");
        buffer.append("   ").append(stringFormatted);
        return buffer.toString();
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EColor getColor() {
        return color;
    }

    public void setColor(EColor color) {
        this.color = color;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public EPriority getPriority() {
        return priority;
    }

    public void setPriority(EPriority priority) {
        this.priority = priority;
    }

    public boolean isChecked(){ return this.isChecked; }

    public void setChecked(boolean update){ this.isChecked = update; }

    public String getSubclass(){ return subclass; }

    public void setSubclass(String subclass){ this.subclass = subclass; }
}
