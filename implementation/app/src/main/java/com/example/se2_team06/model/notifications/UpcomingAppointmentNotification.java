package com.example.se2_team06.model.notifications;

public class UpcomingAppointmentNotification extends NotificationDecorator {

    public UpcomingAppointmentNotification(Notification notification) {
        super(notification);
        notification.setNotificationHeaderText("Upcoming Appointment");
    }

    @Override
    public void sendNotification() {
        super.sendNotification();
    }
}
