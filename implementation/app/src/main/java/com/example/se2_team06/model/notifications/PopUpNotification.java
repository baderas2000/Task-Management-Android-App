package com.example.se2_team06.model.notifications;

import android.util.Pair;

public class PopUpNotification extends Notification {

    @Override
    public void sendNotification() {
        PopUpNotificationMessageHandler
                .setPopUpNotificationMessages(
                        Pair.create(getNotificationHeaderText(), getNotificationText()));
    }
}
