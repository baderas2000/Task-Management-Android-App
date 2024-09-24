package com.example.se2_team06.model.notifications;

public class TaskUpdateNotification extends NotificationDecorator {

    public TaskUpdateNotification(Notification notification) {
        super(notification);
        notification.setNotificationHeaderText("Task Updated");
    }

    @Override
    public void sendNotification() {super.sendNotification();
    }
}
