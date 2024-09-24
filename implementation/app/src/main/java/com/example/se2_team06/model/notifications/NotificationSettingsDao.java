package com.example.se2_team06.model.notifications;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotificationSettingsDao {

    @Query(("SELECT * FROM notification_settings"))
    LiveData<List<NotificationSettings>> getAllNotificationSettings();

    @Query("SELECT * FROM notification_settings WHERE action_type LIKE :actionType")
    NotificationSettings getByActionType(ActionType actionType);

    @Insert
    void insert(NotificationSettings notificationSettings);

    @Update
    void update(NotificationSettings notificationSettings);

    @Delete
    void delete(NotificationSettings notificationSettings);
}
