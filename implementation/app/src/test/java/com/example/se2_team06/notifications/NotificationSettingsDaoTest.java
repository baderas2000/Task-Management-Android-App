package com.example.se2_team06.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.se2_team06.model.notifications.ActionType;
import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.notifications.NotificationSettings;
import com.example.se2_team06.model.notifications.NotificationSettingsDao;
import com.example.se2_team06.model.notifications.NotificationType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NotificationSettingsDaoTest {

    private AppDatabase appDatabase;
    private NotificationSettingsDao notificationSettingsDao;

    @Before
    public void setup() {
        Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase =
                Room.inMemoryDatabaseBuilder(targetContext, AppDatabase.class)
                        .allowMainThreadQueries().build();
        notificationSettingsDao = appDatabase.notificationSettingsDao();
    }

    @After
    public void cleanUp() {
        appDatabase.close();
    }

    @Test
    public void shouldInsertAndReturnNotificationsSettingsForTaskCreatedAction() {

        insertNotificationSettingsList();
        NotificationSettings expected =
                new NotificationSettings(true, ActionType.TASK_CREATED,
                        NotificationType.POPUP, "Task was successfully created");

        NotificationSettings result = notificationSettingsDao.getByActionType(ActionType.TASK_CREATED);

        assertEquals(expected.getNotificationType(), result.getNotificationType());
        assertEquals(expected.getNotificationContent(), result.getNotificationContent());
        assertEquals(expected.getActionType(), result.getActionType());
        assertEquals(expected.isEnabled(), result.isEnabled());
    }

    @Test
    public void shouldInsertAndUpdate() {
        ActionType actionType = ActionType.TASK_CREATED;

        NotificationSettings taskWasSuccessfullyCreated =
                new NotificationSettings(true, actionType,
                        NotificationType.POPUP, "Task was successfully created");

        notificationSettingsDao.insert(taskWasSuccessfullyCreated);
        NotificationSettings notificationSettings = notificationSettingsDao.getByActionType(actionType);

        assertEquals(taskWasSuccessfullyCreated.getNotificationType(), notificationSettings.getNotificationType());
        assertEquals(taskWasSuccessfullyCreated.getNotificationContent(), notificationSettings.getNotificationContent());
        assertEquals(taskWasSuccessfullyCreated.getActionType(), notificationSettings.getActionType());
        assertEquals(taskWasSuccessfullyCreated.isEnabled(), notificationSettings.isEnabled());

        notificationSettings.setEnabled(false);
        notificationSettingsDao.update(notificationSettings);

        notificationSettings = notificationSettingsDao.getByActionType(actionType);
        assertFalse(notificationSettings.isEnabled());
    }

    @Test
    public void shouldInsertAndDelete() {
        ActionType actionType = ActionType.TASK_CREATED;

        NotificationSettings taskWasSuccessfullyCreated =
                new NotificationSettings(true, actionType,
                        NotificationType.POPUP, "Task was successfully created");

        notificationSettingsDao.insert(taskWasSuccessfullyCreated);
        NotificationSettings notificationSettings = notificationSettingsDao.getByActionType(actionType);

        assertEquals(taskWasSuccessfullyCreated.getNotificationType(), notificationSettings.getNotificationType());
        assertEquals(taskWasSuccessfullyCreated.getNotificationContent(), notificationSettings.getNotificationContent());
        assertEquals(taskWasSuccessfullyCreated.getActionType(), notificationSettings.getActionType());
        assertEquals(taskWasSuccessfullyCreated.isEnabled(), notificationSettings.isEnabled());

        notificationSettingsDao.delete(notificationSettings);

        notificationSettings = notificationSettingsDao.getByActionType(actionType);
        assertNull(notificationSettings);
    }

    @Test
    public void shouldInsertAndReturnNotificationsSettingsForAllActionTypes() {

        insertNotificationSettingsList();

        notificationSettingsDao.getAllNotificationSettings().observeForever(
                notificationSettingsList -> {
                    assertEquals(notificationSettingsList.size(), 4);
                });
    }

    private void insertNotificationSettingsList() {
        notificationSettingsDao.insert(
                new NotificationSettings(true, ActionType.TASK_CREATED,
                        NotificationType.POPUP, "Task was successfully created"));
        notificationSettingsDao.insert(
                new NotificationSettings(true, ActionType.TASK_UPDATED,
                        NotificationType.POPUP, "Task was successfully updated"));
        notificationSettingsDao.insert(
                new NotificationSettings(true, ActionType.TASK_DELETED,
                        NotificationType.POPUP, "Task was successfully deleted"));
        notificationSettingsDao.insert(
                new NotificationSettings(true, ActionType.UPCOMING_APPOINTMENT,
                        NotificationType.POPUP, "Upcoming appointment"));
    }
}
