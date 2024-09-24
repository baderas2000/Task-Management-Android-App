package com.example.se2_team06.model.notifications;

import android.os.AsyncTask;
import android.util.Log;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.Task;

public class NotificationService {

    private NotificationFactory notificationFactory;

    public NotificationService(AppDatabase appDatabase) {
        this.notificationFactory =
                new NotificationFactory(
                        new UserContactRepository(appDatabase.userContactDao()),
                        new NotificationSettingsRepository(appDatabase.notificationSettingsDao()));
    }

    public void sendNotification(ActionType actionType, Task task) {

        AsyncTask.execute(() -> {
            Notification notification;
            try {
                notification = notificationFactory.createNotification(actionType, task);
            } catch (UnknownNotificationTypeException e) {
                Log.e("NotificationService", "Unknown notification type", e);
                return;
            } catch (NotificationDisabledException e) {
                Log.e("NotificationService", "Notification disabled", e);
                return;
            } catch (UserContactNotFoundException e) {
                Log.e("NotificationService", "User contact not found", e);
                return;
            }
            notification.sendNotification();
        });
    }
}
