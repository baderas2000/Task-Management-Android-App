package com.example.se2_team06.view;

import java.util.regex.Pattern;

public class EmailValidator {

    private final static String EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private EmailValidator() {
    }

    private static volatile EmailValidator INSTANCE = null;

    public static EmailValidator getInstance() {
        if (INSTANCE == null) {
            synchronized (EmailValidator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmailValidator();
                }
            }
        }
        return INSTANCE;
    }

    public static boolean isValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return email != null &&
                !email.isEmpty() &&
                pattern.matcher(email).matches();
    }
}
