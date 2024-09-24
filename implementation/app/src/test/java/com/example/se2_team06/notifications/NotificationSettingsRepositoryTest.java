package com.example.se2_team06.notifications;

import static java.util.Arrays.asList;

import com.example.se2_team06.model.notifications.ActionType;
import com.example.se2_team06.model.notifications.NotificationSettings;
import com.example.se2_team06.model.notifications.NotificationSettingsDao;
import com.example.se2_team06.model.notifications.NotificationSettingsRepository;
import com.example.se2_team06.model.notifications.NotificationType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class NotificationSettingsRepositoryTest {

    @Mock
    private NotificationSettingsDao notificationSettingsDao;
    private NotificationSettingsRepository notificationSettingsRepository;

    @Before
    public void setup() {
        notificationSettingsRepository = new NotificationSettingsRepository(notificationSettingsDao);
    }

    @Test
    public void shouldInsertWithoutError() {
        NotificationSettings notificationSettings =
                new NotificationSettings(true, ActionType.TASK_CREATED,
                        NotificationType.POPUP, "Task was successfully created");

        notificationSettingsRepository.insert(notificationSettings);
    }

    @Test
    public void shouldUpdateWithoutError() {
        NotificationSettings notificationSettings =
                new NotificationSettings(true, ActionType.TASK_CREATED,
                        NotificationType.POPUP, "Task was successfully created");
        notificationSettings.setId(1); // simulates that entry already exists in database

        notificationSettingsRepository.update(notificationSettings);
    }

    @Test
    public void shouldInsertListOfNotificationsListWithoutError() {
        NotificationSettings taskWasSuccessfullyCreated =
                new NotificationSettings(true, ActionType.TASK_CREATED,
                        NotificationType.POPUP, "Task was successfully created");
        NotificationSettings taskWasSuccessfullyUpdated =
                new NotificationSettings(true, ActionType.TASK_UPDATED,
                        NotificationType.POPUP, "Task was successfully updated");
        NotificationSettings taskWasSuccessfullyDeleted =
                new NotificationSettings(true, ActionType.TASK_DELETED,
                        NotificationType.POPUP, "Task was successfully deleted");
        NotificationSettings upcomingAppointment =
                new NotificationSettings(true, ActionType.UPCOMING_APPOINTMENT,
                        NotificationType.POPUP, "Upcoming appointment");

        List<NotificationSettings> notificationSettingsList =
                asList(taskWasSuccessfullyCreated, taskWasSuccessfullyUpdated,
                        taskWasSuccessfullyDeleted, upcomingAppointment);

        notificationSettingsRepository.updateNotificationSettingsList(notificationSettingsList);
    }
}
