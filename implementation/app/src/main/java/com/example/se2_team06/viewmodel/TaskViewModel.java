package com.example.se2_team06.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Task;
import com.example.se2_team06.model.TaskProxy;
import com.example.se2_team06.model.AppDatabase;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskProxy taskProxy;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskProxy = TaskProxy.getInstance(AppDatabase.getDatabase(application));
        allTasks = taskProxy.getAll();
    }

    public void insert(Task task){
        taskProxy.insert(task);
    }

    public void update(Task task){
        taskProxy.update(task);
    }

    public void delete(Task task){
        taskProxy.delete(task);
    }

    public void deleteAll(){
        taskProxy.deleteAll();
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskProxy.getAll();
    }

    public LiveData<List<Appointment>> getAppointments() {return taskProxy.getAppointments(); }
}
