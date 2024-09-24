package com.example.se2_team06.model.notifications;

import android.util.Log;

import com.example.se2_team06.model.AppDatabase;
import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.TaskDao;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledUpcomingAppointmentChecker {

    private TaskDao taskDao;
    private NotificationSettingsRepository notificationSettingsRepository;
    private NotificationService notificationService;

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public ScheduledUpcomingAppointmentChecker(AppDatabase appDatabase) {
        this.taskDao = appDatabase.taskDao();
        NotificationSettingsDao notificationSettingsDao = appDatabase.notificationSettingsDao();
        this.notificationSettingsRepository = new NotificationSettingsRepository(notificationSettingsDao);
        this.notificationService = new NotificationService(appDatabase);
    }

    public void checkIfNotificationShouldBeSent() {
        Runnable checker = new Runnable() {
            public void run() {

                NotificationSettings notificationSettings =
                        notificationSettingsRepository.getByActionType(ActionType.UPCOMING_APPOINTMENT);
                if (notificationSettings != null) {

                    if (notificationSettings.isEnabled()) {
                        List<Appointment> appointments = taskDao.getAllAppointmentTasks();
                        if (appointments != null && !appointments.isEmpty()) {
                            for (Appointment appointment : appointments) {
                                Date appointmentDate = appointment.getDate();

                                int day = appointmentDate.getDay();
                                int month = appointmentDate.getMonth();
                                int year = appointmentDate.getYear();

                                LocalDate appointmentLocalDate = LocalDate.of(year, month + 1, day);
                                LocalDate now = LocalDate.now();

                                // check if appointment is tomorrow
                                if (now.plusDays(1).equals(appointmentLocalDate)) {
                                    notificationService.sendNotification(ActionType.UPCOMING_APPOINTMENT, appointment);
                                }
                            }
                        }
                    }
                } else {
                    Log.e("ScheduledUpcomingAppointmentChecker",
                            "Notifications settings for upcoming appointment not found");
                }
            }
        };
        scheduler.scheduleAtFixedRate(checker, 30, 30, TimeUnit.SECONDS);
    }
}
