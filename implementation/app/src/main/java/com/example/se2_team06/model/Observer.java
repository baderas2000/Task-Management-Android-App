package com.example.se2_team06.model;

import com.example.se2_team06.model.notifications.ActionType;

public abstract class Observer {
    public abstract void update(ActionType actionType, Task task);
}
