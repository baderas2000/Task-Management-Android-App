package com.example.se2_team06.model.notifications;

import androidx.lifecycle.LiveData;

import com.example.se2_team06.model.AppDatabase;

import java.util.List;

public class NotificationSettingsRepository {

    private NotificationSettingsDao notificationSettingsDao;
    private LiveData<List<NotificationSettings>> allNotificationSettings;

    public NotificationSettingsRepository(NotificationSettingsDao notificationSettingsDao) {
        this.notificationSettingsDao = notificationSettingsDao;
        allNotificationSettings = notificationSettingsDao.getAllNotificationSettings();
    }

    public LiveData<List<NotificationSettings>> getAllNotificationSettings() {
        return allNotificationSettings;
    }

    public void insert(NotificationSettings notificationSettings) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationSettingsDao.insert(notificationSettings);
        });
    }

    public void update(NotificationSettings notificationSettings) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            notificationSettingsDao.update(notificationSettings);
        });
    }

    public void updateNotificationSettingsList(List<NotificationSettings> notificationSettingsList) {
        for (int i = 0; i < notificationSettingsList.size(); i++) {
            NotificationSettings notificationSettings = notificationSettingsList.get(i);
            if (notificationSettings.getId() == null) {
                insert(notificationSettings);
            } else {
                update(notificationSettings);
            }
        }
    }

    public NotificationSettings getByActionType(ActionType actionType) {
        return notificationSettingsDao.getByActionType(actionType);
    }
}
