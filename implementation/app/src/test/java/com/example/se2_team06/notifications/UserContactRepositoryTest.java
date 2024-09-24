package com.example.se2_team06.notifications;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.example.se2_team06.model.notifications.UserContact;
import com.example.se2_team06.model.notifications.UserContactDao;
import com.example.se2_team06.model.notifications.UserContactRepository;
import com.example.se2_team06.model.notifications.UserContactType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserContactRepositoryTest {

    @Mock
    private UserContactDao userContactDao;
    private UserContactRepository userContactRepository;

    @Before
    public void setup() {
        userContactRepository = new UserContactRepository(userContactDao);
    }

    @Test
    public void shouldReturnUserContactWhenFound() {
        UserContact userContact = new UserContact(UserContactType.EMAIL, "example@mail.com");
        userContact.setId(1);

        when(userContactRepository.getByContactType(UserContactType.EMAIL))
                .thenReturn(userContact);

        UserContact result = userContactRepository.getByContactType(UserContactType.EMAIL);

        assertEquals(userContact, result);
    }

    @Test
    public void shouldInsertWithoutError() {
        UserContact userContact = new UserContact(UserContactType.EMAIL, "example@mail.com");

        userContactRepository.insert(userContact);
    }

    @Test
    public void shouldUpdateWithoutError() {
        UserContact userContact = new UserContact(UserContactType.EMAIL, "example@mail.com");

        userContact.setId(1); // simulates that entry already exists in database

        userContactRepository.update(userContact);
    }
}
