package com.example.se2_team06.notifications;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.se2_team06.view.EmailValidator;

import org.junit.Test;

public class EmailValidatorTest {

    @Test
    public void shouldReturnTrueIfValidEmail() {

        assertTrue(EmailValidator.isValid("example@mail.com"));
    }

    @Test
    public void shouldReturnFalseIfInvalidEmail() {

        assertFalse(EmailValidator.isValid("invalid@email"));
        assertFalse(EmailValidator.isValid("invalid-email"));
        assertFalse(EmailValidator.isValid("invalidemail"));
        assertFalse(EmailValidator.isValid("invalid.email"));
        assertFalse(EmailValidator.isValid("a@a.a"));
    }

    @Test
    public void shouldReturnFalseIfNullOrEmptyEmail() {

        assertFalse(EmailValidator.isValid(null));
        assertFalse(EmailValidator.isValid(""));
    }
}
