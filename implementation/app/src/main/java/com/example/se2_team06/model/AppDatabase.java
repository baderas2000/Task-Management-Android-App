package com.example.se2_team06.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.se2_team06.model.notifications.NotificationSettings;
import com.example.se2_team06.model.notifications.NotificationSettingsDao;
import com.example.se2_team06.model.notifications.UserContact;
import com.example.se2_team06.model.notifications.UserContactDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Time.class, Date.class, Checklist.class, Appointment.class, Task.class, NotificationSettings.class, UserContact.class}, version = 16)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    //public abstract ChecklistDao checklistDao();

    public abstract NotificationSettingsDao notificationSettingsDao();

    public abstract UserContactDao userContactDao();

    private static volatile AppDatabase instance = null;
    private static final int numberOfThreads = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(numberOfThreads);

    public static AppDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "task_management_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

}
