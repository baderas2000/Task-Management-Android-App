package com.example.se2_team06.model.notifications;

public class TaskDeletionNotification extends NotificationDecorator {

    public TaskDeletionNotification(Notification notification) {
        super(notification);
        notification.setNotificationHeaderText("Task Deleted");
    }

    @Override
    public void sendNotification() {
        super.sendNotification();
    }
}
