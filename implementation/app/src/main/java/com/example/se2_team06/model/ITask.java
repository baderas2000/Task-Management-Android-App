package com.example.se2_team06.model;

import com.example.se2_team06.model.notifications.ActionType;

import java.util.ArrayList;
import java.util.List;

public interface ITask {
    List<Observer> observers = new ArrayList<>();

    public void insert(Task task);
    public void update(Task task);
    public void delete(Task task);

    void notifyAllObservers(ActionType actionType, Task task);
}
