package com.example.se2_team06.model.notifications;

import android.util.Pair;

import androidx.lifecycle.MutableLiveData;

public class PopUpNotificationMessageHandler {

    private static volatile PopUpNotificationMessageHandler INSTANCE = null;

    private static MutableLiveData<Pair<String, String>> popUpNotificationMessages = new MutableLiveData<>();

    private PopUpNotificationMessageHandler() {
    }

    public static PopUpNotificationMessageHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (PopUpNotificationMessageHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PopUpNotificationMessageHandler();
                }
            }
        }
        return INSTANCE;
    }

    public static MutableLiveData<Pair<String, String>> getPopUpNotificationMessages() {
        return popUpNotificationMessages;
    }

    public static void setPopUpNotificationMessages(Pair<String, String> popUpNotificationMessages) {
        PopUpNotificationMessageHandler.popUpNotificationMessages.postValue(popUpNotificationMessages);
    }
}
