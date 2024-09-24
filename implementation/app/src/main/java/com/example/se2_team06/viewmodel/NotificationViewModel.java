package com.example.se2_team06.viewmodel;

import android.app.Application;
import android.util.Pair;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.notifications.PopUpNotificationMessageHandler;
import com.example.se2_team06.model.notifications.ScheduledUpcomingAppointmentChecker;

public class NotificationViewModel extends AndroidViewModel {

    private PopUpNotificationMessageHandler popUpNotificationMessageHandler;
    private ScheduledUpcomingAppointmentChecker scheduledUpcomingAppointmentChecker;

    private final LiveData<Pair<String, String>> popUpNotificationMessage;

    public NotificationViewModel(Application application) {
        super(application);
        popUpNotificationMessageHandler = PopUpNotificationMessageHandler.getInstance();
        popUpNotificationMessage = PopUpNotificationMessageHandler.getPopUpNotificationMessages();
        scheduledUpcomingAppointmentChecker = new ScheduledUpcomingAppointmentChecker(AppDatabase.getDatabase(application));
        scheduledUpcomingAppointmentChecker.checkIfNotificationShouldBeSent();
    }

    public LiveData<Pair<String, String>> getPopUpNotificationMessage() {
        return popUpNotificationMessage;
    }
}
