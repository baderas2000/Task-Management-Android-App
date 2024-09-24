package com.example.se2_team06.model.notifications;

public class TaskCreationNotification extends NotificationDecorator {

    public TaskCreationNotification(Notification notification) {
        super(notification);
        notification.setNotificationHeaderText("Task Created");
    }

    @Override
    public void sendNotification() {
        super.sendNotification();
    }
}
