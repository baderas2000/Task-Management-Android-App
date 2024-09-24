package com.example.se2_team06.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.se2_team06.R;
import com.example.se2_team06.model.notifications.ActionType;
import com.example.se2_team06.model.notifications.NotificationSettings;
import com.example.se2_team06.model.notifications.NotificationType;
import com.example.se2_team06.viewmodel.NotificationSettingsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NotificationsSettingsActivity extends AppCompatActivity {

    private NotificationSettingsViewModel notificationSettingsViewModel;
    private List<NotificationSettings> notificationSettingsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationSettingsViewModel =
                new ViewModelProvider(this).get(NotificationSettingsViewModel.class);

        notificationSettingsViewModel.getAllNotificationSettings().observe(this, settings -> {
            if (settings.size() == 0) {
                // init with initial default data
                notificationSettingsList.addAll(Arrays.asList(
                        new NotificationSettings(false, ActionType.TASK_CREATED, NotificationType.POPUP, ""),
                        new NotificationSettings(false, ActionType.TASK_UPDATED, NotificationType.POPUP, ""),
                        new NotificationSettings(false, ActionType.TASK_DELETED, NotificationType.POPUP, ""),
                        new NotificationSettings(false, ActionType.UPCOMING_APPOINTMENT, NotificationType.POPUP, "")));
            } else {
                for (int i = 0; i < settings.size(); i++) {
                    notificationSettingsList.add(settings.get(i));
                }
            }
            showSavedNotificationSettings();
        });

        setContentView(R.layout.activity_notifications_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveSelectedNotificationSettings();

    }


    private void showSavedNotificationSettings() {
        CheckBox taskCreatedCheckBox = findViewById(R.id.checkBox_taskCreated);
        RadioButton taskCreatedCheckBoxPopUp = findViewById(R.id.radioButton_taskCreated_popUp);
        RadioButton taskCreatedCheckBoxEmail = findViewById(R.id.radioButton_taskCreated_email);
        EditText notificationContentOnTaskCreation = findViewById(R.id.editText_taskCreated_notificationContent);

        CheckBox taskUpdatedCheckBox = findViewById(R.id.checkBox_taskUpdated);
        RadioButton taskUpdatedCheckBoxPopUp = findViewById(R.id.radioButton_taskUpdated_popUp);
        RadioButton taskUpdatedCheckBoxEmail = findViewById(R.id.radioButton_taskUpdated_email);
        EditText notificationContentOnTaskUpdate = findViewById(R.id.editText_taskUpdated_notificationContent);

        CheckBox taskDeletedCheckBox = findViewById(R.id.checkBox_taskDeleted);
        RadioButton taskDeletedCheckBoxPopUp = findViewById(R.id.radioButton_taskDeleted_popUp);
        RadioButton taskDeletedCheckBoxEmail = findViewById(R.id.radioButton_taskDeleted_email);
        EditText notificationContentOnTaskDelete = findViewById(R.id.editText_taskDeleted_notificationContent);

        CheckBox appointmentCheckBox = findViewById(R.id.checkBox_appointment);
        RadioButton appointmentCheckBoxPopUp = findViewById(R.id.radioButton_appointment_popUp);
        RadioButton appointmentCheckBoxEmail = findViewById(R.id.radioButton_appointment_email);
        EditText notificationContentOnAppointment = findViewById(R.id.editText_appointment_notificationContent);

        for (NotificationSettings notificationSettings : notificationSettingsList) {
            switch (notificationSettings.getActionType()) {
                case TASK_CREATED:
                    taskCreatedCheckBox.setChecked(notificationSettings.isEnabled());
                    taskCreatedCheckBoxPopUp.setChecked(notificationSettings.getNotificationType().equals(NotificationType.POPUP));
                    taskCreatedCheckBoxEmail.setChecked(notificationSettings.getNotificationType().equals(NotificationType.EMAIL));
                    notificationContentOnTaskCreation.setText(notificationSettings.getNotificationContent());
                    break;
                case TASK_UPDATED:
                    taskUpdatedCheckBox.setChecked(notificationSettings.isEnabled());
                    taskUpdatedCheckBoxPopUp.setChecked(notificationSettings.getNotificationType().equals(NotificationType.POPUP));
                    taskUpdatedCheckBoxEmail.setChecked(notificationSettings.getNotificationType().equals(NotificationType.EMAIL));
                    notificationContentOnTaskUpdate.setText(notificationSettings.getNotificationContent());
                    break;
                case TASK_DELETED:
                    taskDeletedCheckBox.setChecked(notificationSettings.isEnabled());
                    taskDeletedCheckBoxPopUp.setChecked(notificationSettings.getNotificationType().equals(NotificationType.POPUP));
                    taskDeletedCheckBoxEmail.setChecked(notificationSettings.getNotificationType().equals(NotificationType.EMAIL));
                    notificationContentOnTaskDelete.setText(notificationSettings.getNotificationContent());
                    break;
                case UPCOMING_APPOINTMENT:
                    appointmentCheckBox.setChecked(notificationSettings.isEnabled());
                    appointmentCheckBoxPopUp.setChecked(notificationSettings.getNotificationType().equals(NotificationType.POPUP));
                    appointmentCheckBoxEmail.setChecked(notificationSettings.getNotificationType().equals(NotificationType.EMAIL));
                    notificationContentOnAppointment.setText(notificationSettings.getNotificationContent());
                    break;
            }
        }
    }

    private void saveSelectedNotificationSettings() {

        Button saveNotificationSettingsButton = findViewById(R.id.saveNotificationSettingsButton);
        saveNotificationSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationSettingsViewModel.updateNotificationSettings(getUpdatedNotificationSettingsFromView());
                Toast.makeText(NotificationsSettingsActivity.this, "Changes saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<NotificationSettings> getUpdatedNotificationSettingsFromView() {
        CheckBox taskCreatedCheckBox = findViewById(R.id.checkBox_taskCreated);
        RadioButton taskCreatedCheckBoxPopUp = findViewById(R.id.radioButton_taskCreated_popUp);
        EditText notificationContentOnTaskCreation = findViewById(R.id.editText_taskCreated_notificationContent);

        CheckBox taskUpdatedCheckBox = findViewById(R.id.checkBox_taskUpdated);
        RadioButton taskUpdatedCheckBoxPopUp = findViewById(R.id.radioButton_taskUpdated_popUp);
        EditText notificationContentOnTaskUpdate = findViewById(R.id.editText_taskUpdated_notificationContent);

        CheckBox taskDeletedCheckBox = findViewById(R.id.checkBox_taskDeleted);
        RadioButton taskDeletedCheckBoxPopUp = findViewById(R.id.radioButton_taskDeleted_popUp);
        EditText notificationContentOnTaskDelete = findViewById(R.id.editText_taskDeleted_notificationContent);

        CheckBox appointmentCheckBox = findViewById(R.id.checkBox_appointment);
        RadioButton appointmentCheckBoxPopUp = findViewById(R.id.radioButton_appointment_popUp);
        EditText notificationContentOnAppointment = findViewById(R.id.editText_appointment_notificationContent);

        for (int i = 0; i < notificationSettingsList.size(); i++) {

            switch (notificationSettingsList.get(i).getActionType()) {
                case TASK_CREATED:
                    notificationSettingsList.get(i).setEnabled(taskCreatedCheckBox.isChecked());
                    if (taskCreatedCheckBoxPopUp.isChecked())
                        notificationSettingsList.get(i).setNotificationType(NotificationType.POPUP);
                    else
                        notificationSettingsList.get(i).setNotificationType(NotificationType.EMAIL);

                    notificationSettingsList.get(i).setNotificationContent(notificationContentOnTaskCreation.getText().toString());
                    break;
                case TASK_UPDATED:
                    notificationSettingsList.get(i).setEnabled(taskUpdatedCheckBox.isChecked());
                    if (taskUpdatedCheckBoxPopUp.isChecked())
                        notificationSettingsList.get(i).setNotificationType(NotificationType.POPUP);
                    else
                        notificationSettingsList.get(i).setNotificationType(NotificationType.EMAIL);

                    notificationSettingsList.get(i).setNotificationContent(notificationContentOnTaskUpdate.getText().toString());
                    break;
                case TASK_DELETED:
                    notificationSettingsList.get(i).setEnabled(taskDeletedCheckBox.isChecked());
                    if (taskDeletedCheckBoxPopUp.isChecked())
                        notificationSettingsList.get(i).setNotificationType(NotificationType.POPUP);
                    else
                        notificationSettingsList.get(i).setNotificationType(NotificationType.EMAIL);

                    notificationSettingsList.get(i).setNotificationContent(notificationContentOnTaskDelete.getText().toString());
                    break;
                case UPCOMING_APPOINTMENT:
                    notificationSettingsList.get(i).setEnabled(appointmentCheckBox.isChecked());
                    if (appointmentCheckBoxPopUp.isChecked())
                        notificationSettingsList.get(i).setNotificationType(NotificationType.POPUP);
                    else
                        notificationSettingsList.get(i).setNotificationType(NotificationType.EMAIL);

                    notificationSettingsList.get(i).setNotificationContent(notificationContentOnAppointment.getText().toString());
                    break;

            }
        }

        return notificationSettingsList;
    }
}