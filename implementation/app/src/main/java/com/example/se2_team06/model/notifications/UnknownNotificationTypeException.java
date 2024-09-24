package com.example.se2_team06.model.notifications;

public class UnknownNotificationTypeException extends RuntimeException {

    public UnknownNotificationTypeException() {
        super("Unknown notification type");
    }

    public UnknownNotificationTypeException(String message) {
        super(message);
    }
}
