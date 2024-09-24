package com.example.se2_team06.view;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_team06.R;
import com.example.se2_team06.model.Appointment;
import com.example.se2_team06.model.Checklist;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.ETaskType;
import com.example.se2_team06.model.MainActivity;
import com.example.se2_team06.model.Task;
import com.example.se2_team06.model.Time;

import java.util.Calendar;

public class EditChecklistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private TextView textViewDate;
    private TextView textViewTime;
    private Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private Date date = new Date(day, month, year);

    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    private Time time = new Time(hour, minute);

    private Button displayDate;
    private Button displayTime;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String subclass = "Update";
    private EStatus status = EStatus.ACTIVE;
    private String title;
    private String description;
    private EColor color;
    private EPriority priority;
    private ETaskType taskType;
    private int taskUID;

    private Checklist updatedTask;

    private Spinner spinnerColor;
    private Spinner spinnerPriority;
    private Spinner spinnerTaskType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_checklist);

        Intent intent = getIntent();
        taskUID = intent.getIntExtra("uid",0);
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        status = (EStatus) intent.getSerializableExtra("status");

        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_description);

        editTextTitle.setText(title);
        editTextDescription.setText(description);

        spinnerColor = findViewById(R.id.spinnerColor);
        spinnerColor.setAdapter(new ArrayAdapter<EColor>(this, android.R.layout.simple_spinner_item, EColor.values()));
        color = (EColor) intent.getSerializableExtra("color");
        spinnerColor.setSelection(color.ordinal());

        spinnerPriority = findViewById(R.id.spinnerPriority);
        spinnerPriority.setAdapter(new ArrayAdapter<EPriority>(this, android.R.layout.simple_spinner_item, EPriority.values()));
        priority = (EPriority) intent.getSerializableExtra("priority");
        spinnerPriority.setSelection(priority.ordinal());

        spinnerTaskType = findViewById(R.id.spinnerTaskType);
        spinnerTaskType.setAdapter(new ArrayAdapter<ETaskType>(this, android.R.layout.simple_spinner_item, ETaskType.values()));
        taskType = (ETaskType) intent.getSerializableExtra("taskType");
        spinnerTaskType.setSelection(taskType.ordinal());

        textViewDate = findViewById(R.id.textViewDate);
        date = parseDate(intent.getStringExtra("date"));
        textViewDate.setText(date.toString());

        displayDate = findViewById(R.id.selectDate);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        EditChecklistActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        dateSetListener,
                        date.getYear(), date.getMonth(), date.getDay());

                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pickedYear, int pickedMonth, int pickedDay) {
                day = pickedDay;
                month = pickedMonth;
                year = pickedYear;

                date = new Date(day, month, year);
                textViewDate.setText(date.toString());
            }
        };

        displayTime = findViewById(R.id.selectTime);
        displayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(
                        EditChecklistActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        timeSetListener,
                        hour, minute, true);

                dialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int pickedHour, int pickedMinute) {
                hour = pickedHour;
                minute = pickedMinute;

                time = new Time(hour, minute);
                textViewTime.setText(time.toString());
            }
        };

        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updatedTask = new Checklist(title, description, color, EStatus.PLANNED, priority, "Checklist", date, taskType, time);
                updatedTask.setUid(taskUID);
                deleteTask(updatedTask);
                finish();
            }
        });

        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
                updatedTask = new Checklist(title, description, color, EStatus.PLANNED, priority, "Checklist", date, taskType, time);
                updatedTask.setUid(taskUID);
                updateTask(updatedTask);
                finish();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void updateTask(Task task){
        MainActivity.taskViewModel.update(task);
    }

    private void deleteTask(Task task){
        MainActivity.taskViewModel.delete(task);
    }

    private Date parseDate(String text){
        String[] parts = text.split("\\.");

        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1])-1;
        int year = Integer.parseInt(parts[2]);

        return new Date(day, month, year);
    }

    private void fetchData(){
        title = editTextTitle.getText().toString();
        description = editTextDescription.getText().toString();

        String textColor = spinnerColor.getSelectedItem().toString();
        String textPriority = spinnerPriority.getSelectedItem().toString();
        String textTaskType = spinnerTaskType.getSelectedItem().toString();

        color = EColor.valueOf(textColor);
        priority = EPriority.valueOf(textPriority);
        taskType = ETaskType.valueOf(textTaskType);
    }
}
