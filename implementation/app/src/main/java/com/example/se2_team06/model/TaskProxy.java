package com.example.se2_team06.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.se2_team06.model.notifications.ActionType;
import com.example.se2_team06.model.notifications.NotificationObserver;

import java.util.ArrayList;
import java.util.List;

public class TaskProxy implements ITask {
    private AppDatabase database;
    private static TaskProxy instance;
    private TaskDao taskDao;

    public static TaskProxy getInstance(final AppDatabase database){
        if (instance == null) {
            synchronized (TaskProxy.class) {
                if (instance == null) {
                    instance = new TaskProxy(database);
                }
            }
        }
        return instance;
    }

    private TaskProxy(AppDatabase db){
        this.database = db;
        taskDao = database.taskDao();
        observers.add(new NotificationObserver(db));
    }

    // https://developer.android.com/reference/android/arch/lifecycle/MediatorLiveData
    public LiveData<List<Task>> getAll() {
        MediatorLiveData<List<Task>> result = new MediatorLiveData<>();
        result.setValue(new ArrayList<>());
        List<Task> allTasksList = new ArrayList<>();

        LiveData<List<Appointment>> appointments = taskDao.getAllAppointments();
        LiveData<List<Checklist>> checklists = taskDao.getAllChecklists();

        result.addSource(appointments, tasks -> {
            allTasksList.clear();
            allTasksList.addAll(tasks);
            if(checklists.getValue() != null)
                allTasksList.addAll(checklists.getValue());
            result.setValue(allTasksList);

        });

        result.addSource(checklists, tasks -> {
            allTasksList.clear();
            if(appointments.getValue() != null)
                allTasksList.addAll(appointments.getValue());
            allTasksList.addAll(tasks);
            result.setValue(allTasksList);
        });

        return result;
    }

    public LiveData<List<Appointment>> getAppointments(){ return taskDao.getAllAppointments();}

    @Override
    public void insert(Task task) {
        if (task instanceof Appointment) {
            Appointment appointment = (Appointment) task;
            AppDatabase.databaseWriteExecutor.execute(() -> {
                taskDao.insertAppointment(appointment);
                Log.d(TAG, "TaskProxy insert() returned: " + "appointment saved");
            });
        }
        if(task instanceof Checklist) {
            Checklist checklist = (Checklist) task;
            AppDatabase.databaseWriteExecutor.execute(() -> {
                taskDao.insertChecklist(checklist);
                Log.d(TAG, "TaskProxy insert() returned: " + "checklist saved");
            });
        }

        notifyAllObservers(ActionType.TASK_CREATED, task);
    }

    @Override
    public void update(Task task) {
        if(task.getSubclass().equals("Appointment")){
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Log.d(TAG, "update() TaskProxy returned: " + "class is instance of Appointment.");
                taskDao.updateAppointment((Appointment) task);
            });
        }else if(task.getSubclass().equals("Checklist")){
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Log.d(TAG, "update() TaskProxy returned: " + "class is instance of Checklist.");
                taskDao.updateChecklist((Checklist) task);
            });
        }else{
            Log.d(TAG, "update() TaskProxy returned: " + "class no instance of anything known.");
        }
        notifyAllObservers(ActionType.TASK_UPDATED, task);
    }

    @Override
    public void delete(Task task) {
        if(task.getSubclass().equals("Appointment")){
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Log.d(TAG, "delete() TaskProxy returned: " + "class is instance of Appointment.");
                taskDao.deleteAppointment((Appointment) task);
            });
        }else if(task.getSubclass().equals("Checklist")){
            AppDatabase.databaseWriteExecutor.execute(() -> {
                Log.d(TAG, "delete() TaskProxy returned: " + "class is instance of Checklist.");
                taskDao.deleteChecklist((Checklist) task);
            });
        }else{
            Log.d(TAG, "delete() TaskProxy returned: " + "class no instance of anything known.");
        }
        notifyAllObservers(ActionType.TASK_DELETED, task);
    }

    public void deleteAll() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Log.d(TAG, "delete() TaskProxy returned: " + "class is instance of Checklist.");
            taskDao.deleteAllFromChecklist();
            taskDao.deleteAllFromAppointment();
        });
    }

    public List<Appointment> getAllAppointmentTasks(){
        return taskDao.getAllAppointmentTasks();
    }

    public List<Checklist> getAllChecklistTasks(){
        return taskDao.getAllChecklistTasks();
    }

    @Override
    public void notifyAllObservers(ActionType actionType, Task task) {
        observers.forEach(observer -> observer.update(actionType, task));
    }


}
