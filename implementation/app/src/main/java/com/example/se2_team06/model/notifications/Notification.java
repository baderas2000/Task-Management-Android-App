package com.example.se2_team06.model.notifications;

public abstract class Notification {

    private String notificationHeaderText = "Task state changed";
    private String notificationText = "";

    abstract void sendNotification();

    protected void setNotificationHeaderText(String text) {
        this.notificationHeaderText = text;
    }

    protected void setNotificationText(String text) {
        this.notificationText = text;
    }

    public String getNotificationHeaderText() {
        return notificationHeaderText;
    }

    public String getNotificationText() {
        return notificationText;
    }
}
