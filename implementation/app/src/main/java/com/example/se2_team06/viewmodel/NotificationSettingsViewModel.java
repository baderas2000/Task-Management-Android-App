package com.example.se2_team06.viewmodel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.notifications.NotificationSettings;
import com.example.se2_team06.model.notifications.NotificationSettingsDao;
import com.example.se2_team06.model.notifications.NotificationSettingsRepository;

import java.util.List;

public class NotificationSettingsViewModel extends AndroidViewModel {

    private NotificationSettingsRepository notificationSettingsRepository;

    private final LiveData<List<NotificationSettings>> allNotificationSettings;

    public NotificationSettingsViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        NotificationSettingsDao notificationSettingsDao = database.notificationSettingsDao();
        notificationSettingsRepository = new NotificationSettingsRepository(notificationSettingsDao);
        allNotificationSettings = notificationSettingsRepository.getAllNotificationSettings();
    }

    public LiveData<List<NotificationSettings>> getAllNotificationSettings() {
        return allNotificationSettings;
    }

    public void insert(NotificationSettings notificationSettings) {
        notificationSettingsRepository.insert(notificationSettings);
    }

    public void update(NotificationSettings notificationSettings) {
        notificationSettingsRepository.update(notificationSettings);
    }

    public void updateNotificationSettings(List<NotificationSettings> notificationSettingsList) {
        notificationSettingsRepository.updateNotificationSettingsList(notificationSettingsList);
    }
}
