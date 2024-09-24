package com.example.se2_team06.model.notifications;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_settings")
public class NotificationSettings {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "is_enabled")
    private boolean isEnabled;

    @ColumnInfo(name = "action_type")
    private ActionType actionType;

    @ColumnInfo(name = "notification_type")
    private NotificationType notificationType;

    @ColumnInfo(name = "notification_content")
    private String notificationContent;

    public NotificationSettings() {
    }

    public NotificationSettings(boolean isEnabled, ActionType actionType, NotificationType notificationType, String notificationContent) {
        this.isEnabled = isEnabled;
        this.actionType = actionType;
        this.notificationType = notificationType;
        this.notificationContent = notificationContent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    @Override
    public String toString() {
        return "NotificationSettings{" +
                "id=" + id +
                ", isEnabled=" + isEnabled +
                ", actionType=" + actionType +
                ", notificationType=" + notificationType +
                ", notificationContent='" + notificationContent + '\'' +
                '}';
    }
}
