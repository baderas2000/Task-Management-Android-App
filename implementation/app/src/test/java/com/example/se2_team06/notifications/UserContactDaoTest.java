package com.example.se2_team06.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.notifications.UserContact;
import com.example.se2_team06.model.notifications.UserContactDao;
import com.example.se2_team06.model.notifications.UserContactType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class UserContactDaoTest {

    private AppDatabase appDatabase;
    private UserContactDao userContactDao;

    @Before
    public void setup() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase =
                Room.inMemoryDatabaseBuilder(targetContext, AppDatabase.class)
                        .allowMainThreadQueries().build();
        userContactDao = appDatabase.userContactDao();
    }

    @After
    public void cleanUp() {
        appDatabase.close();
    }

    @Test
    public void shouldInsertAndReturnEmailUserContact() {

        insertUserContactList();
        UserContact userContact = new UserContact(UserContactType.EMAIL, "example@mail.com");

        UserContact result = userContactDao.getByContactType(UserContactType.EMAIL);

        assertEquals(userContact.getContactType(), result.getContactType());
        assertEquals(userContact.getContactValue(), result.getContactValue());
    }

    @Test
    public void shouldInsertAndUpdate() {
        UserContactType userContactType = UserContactType.EMAIL;

        UserContact emailUserContact = new UserContact(userContactType, "example@mail.com");

        userContactDao.insert(emailUserContact);
        UserContact userContact = userContactDao.getByContactType(userContactType);

        assertEquals(userContact.getContactType(), userContact.getContactType());
        assertEquals(userContact.getContactValue(), userContact.getContactValue());

        userContact.setContactValue("new-value@example.com");
        userContactDao.update(userContact);

        userContact = userContactDao.getByContactType(userContactType);
        assertEquals(userContact.getContactValue(), "new-value@example.com");
    }

    @Test
    public void shouldInsertAndDelete() {
        UserContactType userContactType = UserContactType.EMAIL;

        UserContact emailUserContact = new UserContact(userContactType, "example@mail.com");

        userContactDao.insert(emailUserContact);
        UserContact userContact = userContactDao.getByContactType(userContactType);

        assertEquals(userContact.getContactType(), userContact.getContactType());
        assertEquals(userContact.getContactValue(), userContact.getContactValue());

        userContactDao.delete(userContact);

        userContact = userContactDao.getByContactType(userContactType);
        assertNull(userContact);
    }

    @Test
    public void shouldInsertAndReturnAllUserContacts() {

        insertUserContactList();

        userContactDao.getAllUserContacts().observeForever(
                userContacts -> {
                    assertEquals(userContacts.size(), 2);
                });
    }

    private void insertUserContactList() {
        userContactDao.insert(
                new UserContact(UserContactType.EMAIL, "example@mail.com"));
        userContactDao.insert(
                new UserContact(UserContactType.NAME, "John"));
    }
}
