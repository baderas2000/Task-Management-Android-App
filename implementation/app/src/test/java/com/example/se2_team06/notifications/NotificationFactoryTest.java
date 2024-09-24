package com.example.se2_team06.notifications;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import com.example.se2_team06.model.notifications.ActionType;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.notifications.Notification;
import com.example.se2_team06.model.notifications.NotificationDisabledException;
import com.example.se2_team06.model.notifications.NotificationFactory;
import com.example.se2_team06.model.notifications.NotificationSettings;
import com.example.se2_team06.model.notifications.NotificationSettingsRepository;
import com.example.se2_team06.model.notifications.NotificationType;
import com.example.se2_team06.model.Task;
import com.example.se2_team06.model.notifications.TaskCreationNotification;
import com.example.se2_team06.model.notifications.UnknownNotificationTypeException;
import com.example.se2_team06.model.notifications.UserContactNotFoundException;
import com.example.se2_team06.model.notifications.UserContactRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class NotificationFactoryTest {

    @Mock
    private UserContactRepository userContactRepository;

    @Mock
    private NotificationSettingsRepository notificationSettingsRepository;

    private NotificationFactory notificationFactory;

    @Before
    public void setup() {
        notificationFactory = new NotificationFactory(userContactRepository, notificationSettingsRepository);
    }

    @Test
    public void shouldThrowUnknownNotificationTypeExceptionIfNotificationSettingNotFound() {
        Task task = new Task("Doctor appointment", "Doctor",
                EColor.BLACK, EStatus.ACTIVE, EPriority.HIGH, "subclass");

        assertThrows(UnknownNotificationTypeException.class, () ->
                notificationFactory.createNotification(ActionType.TASK_CREATED, task));

    }

    @Test
    public void shouldThrowNotificationDisabledExceptionIfNotificationDisabled() {
        Task task = new Task("Doctor appointment", "Doctor",
                EColor.BLACK, EStatus.ACTIVE, EPriority.HIGH, "subclass");
        NotificationSettings taskCreated =
                new NotificationSettings(false, ActionType.TASK_CREATED,
                        NotificationType.POPUP, "Task created");

        when(notificationSettingsRepository.getByActionType(ActionType.TASK_CREATED))
                .thenReturn(taskCreated);

        assertThrows(NotificationDisabledException.class, () ->
                notificationFactory.createNotification(ActionType.TASK_CREATED, task));
    }

    @Test
    public void shouldThrowUserContactNotFoundExceptionIfEmailNotificationTypeAndNoContact() {
        Task task = new Task("Doctor appointment", "Doctor",
                EColor.BLACK, EStatus.ACTIVE, EPriority.HIGH, "subclass");
        NotificationSettings taskCreated =
                new NotificationSettings(true, ActionType.TASK_CREATED,
                        NotificationType.EMAIL, "Task created");

        when(notificationSettingsRepository.getByActionType(ActionType.TASK_CREATED))
                .thenReturn(taskCreated);

        assertThrows(UserContactNotFoundException.class, () ->
                notificationFactory.createNotification(ActionType.TASK_CREATED, task));
    }

    @Test
    public void shouldCreateNotificationIfNotificationEnabledWithNotificationHeader() {
        Task task = new Task("Doctor appointment", "Doctor",
                EColor.BLACK, EStatus.ACTIVE, EPriority.HIGH, "subclass");
        NotificationSettings taskCreated =
                new NotificationSettings(true, ActionType.TASK_CREATED,
                        NotificationType.POPUP, "Task was successfully created");

        when(notificationSettingsRepository.getByActionType(ActionType.TASK_CREATED))
                .thenReturn(taskCreated);

        Notification result = notificationFactory.createNotification(ActionType.TASK_CREATED, task);

        // assert Decorator pattern appeared
        assert (result instanceof TaskCreationNotification);

        assertEquals("Task \"Doctor appointment\" changed: Task was successfully created",
                result.getNotificationText());
        assertEquals("Task Created", result.getNotificationHeaderText());
    }
}
