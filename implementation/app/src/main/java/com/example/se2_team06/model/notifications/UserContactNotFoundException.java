package com.example.se2_team06.model.notifications;

public class UserContactNotFoundException extends RuntimeException {

    public UserContactNotFoundException() {
        super("User contact not found exception");
    }

    public UserContactNotFoundException(String message) {
        super(message);
    }
}
