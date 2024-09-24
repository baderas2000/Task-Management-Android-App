package com.example.se2_team06.model.notifications;

public class NotificationDisabledException extends RuntimeException {

    public NotificationDisabledException() {
        super("Notification disabled");
    }

    public NotificationDisabledException(String message) {
        super(message);
    }
}
