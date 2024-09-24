package com.example.se2_team06.model.notifications;

public class NotificationDecorator extends Notification {
    protected Notification notification;

    public NotificationDecorator(Notification notification) {
        this.notification = notification;
    }

    @Override
    public void sendNotification() {
        this.setNotificationText(this.notification.getNotificationText());
        this.notification.sendNotification();
    }

    @Override
    public String getNotificationHeaderText() {
        return notification.getNotificationHeaderText();
    }

    @Override
    public String getNotificationText() {
        return notification.getNotificationText();
    }
}
