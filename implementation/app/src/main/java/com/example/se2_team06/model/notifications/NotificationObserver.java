package com.example.se2_team06.model.notifications;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.Observer;
import com.example.se2_team06.model.Task;

public class NotificationObserver extends Observer {

    private NotificationService notificationService;

    public NotificationObserver(AppDatabase appDatabase) {
        notificationService = new NotificationService(appDatabase);
    }

    @Override
    public void update(ActionType actionType, Task task) {
        notificationService.sendNotification(actionType, task);
    }
}
