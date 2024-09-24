package com.example.se2_team06.model.notifications;

import com.example.se2_team06.model.Task;

public class NotificationFactory {

    private NotificationSettingsRepository notificationSettingsRepository;
    private UserContactRepository userContactRepository;

    public NotificationFactory(
            UserContactRepository userContactRepository,
            NotificationSettingsRepository notificationSettingsRepository) {

        this.userContactRepository = userContactRepository;
        this.notificationSettingsRepository = notificationSettingsRepository;
    }

    public Notification createNotification(ActionType actionType, Task task) {

        NotificationSettings notificationSettings =
                notificationSettingsRepository.getByActionType(actionType);

        if (notificationSettings == null)
            throw new UnknownNotificationTypeException("Notification type cannot be null");

        if (!notificationSettings.isEnabled()) {
            throw new NotificationDisabledException();
        }

        Notification notification;
        switch (notificationSettings.getNotificationType()) {
            case POPUP:
                notification = new PopUpNotification();
                break;
            case EMAIL:
                UserContact userContact = userContactRepository.getByContactType(UserContactType.EMAIL);

                String emailReceiver;
                if (userContact != null) {
                    emailReceiver = userContact.getContactValue();
                    notification = new EmailNotification(emailReceiver);
                } else {
                    throw new UserContactNotFoundException();
                }
                break;
            default:
                throw new UnknownNotificationTypeException();
        }

        String notificationMessage =
                "Task \"" + task.getTitle() + "\" changed: " + notificationSettings.getNotificationContent();
        notification.setNotificationText(notificationMessage);

        switch (actionType) {
            case TASK_CREATED:
                notification = new TaskCreationNotification(notification);
                break;
            case TASK_UPDATED:
                notification = new TaskUpdateNotification(notification);
                break;
            case TASK_DELETED:
                notification = new TaskDeletionNotification(notification);
                break;
            case UPCOMING_APPOINTMENT:
                notification = new UpcomingAppointmentNotification(notification);
                break;
        }

        return notification;
    }
}
