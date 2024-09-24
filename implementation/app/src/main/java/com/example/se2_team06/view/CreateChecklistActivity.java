package com.example.se2_team06.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.se2_team06.R;
import com.example.se2_team06.model.Date;
import com.example.se2_team06.model.EColor;
import com.example.se2_team06.model.EPriority;
import com.example.se2_team06.model.EStatus;
import com.example.se2_team06.model.ETaskType;
import com.example.se2_team06.model.NoTitleException;
import com.example.se2_team06.model.Time;

import java.util.Calendar;

public class CreateChecklistActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_SUBCLASS =
            "com.example.se2_team06.view.EXTRA_SUBCLASS";
    public static final String EXTRA_TITLE =
            "com.example.se2_team06.view.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.example.se2_team06.view.EXTRA_DESCRIPTION";
    public static final String EXTRA_STATUS =
            "com.example.se2_team06.view.EXTRA_STATUS";
    public static final String EXTRA_COLOR =
            "com.example.se2_team06.view.EXTRA_COLOR";
    public static final String EXTRA_PRIORITY =
            "com.example.se2_team06.view.EXTRA_PRIORITY";
    public static final String EXTRA_LOCATION =
            "com.example.se2_team06.view.EXTRA_LOCATION";
    public static final String EXTRA_TASK_TYPE =
            "com.example.se2_team06.view.EXTRA_TASK_TYPE";
    public static final String EXTRA_DATE =
            "com.example.se2_team06.view.EXTRA_DATE";
    public static final String EXTRA_TIME =
            "com.example.se2_team06.view.EXTRA_TIME";

    EditText editTextTitle;
    EditText editTextDescription;
    private String subclass = "Checklist";
    private EStatus status = EStatus.ACTIVE;
    private EColor color;
    private EPriority priority;
    private ETaskType taskType;


    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    private Date deadline = new Date(day, month, year);


    int hour = calendar.get(Calendar.HOUR);
    int minute = calendar.get(Calendar.MINUTE);
    private Time time = new Time(hour, minute);

    private Button displayDate;
    private Button displayTime;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private Spinner spinnerColor;
    private Spinner spinnerPriority;
    private Spinner spinnerTaskType;

    private TextView textViewDate;
    private TextView textViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_checklist);

        editTextTitle = findViewById(R.id.edit_title);
        editTextDescription = findViewById(R.id.edit_description);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);

        spinnerColor = findViewById(R.id.spinnerColor);
        spinnerColor.setAdapter(new ArrayAdapter<EColor>(this, android.R.layout.simple_spinner_item, EColor.values()));

        spinnerPriority = findViewById(R.id.spinnerPriority);
        spinnerPriority.setAdapter(new ArrayAdapter<EPriority>(this, android.R.layout.simple_spinner_item, EPriority.values()));

        spinnerTaskType = findViewById(R.id.spinnerTaskType);
        spinnerTaskType.setAdapter(new ArrayAdapter<ETaskType>(this, android.R.layout.simple_spinner_item, ETaskType.values()));

        textViewDate.setText(deadline.toString());
        textViewTime.setText(new Time(hour, minute).toString());

        displayDate = findViewById(R.id.selectDate);
        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(
                        CreateChecklistActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        dateSetListener,
                        year, month, day);

                dialog.show();

            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pickedYear, int pickedMonth, int pickedDay) {
                day = pickedDay;
                month = pickedMonth;
                year = pickedYear;

                deadline = new Date(day, month, year);
                textViewDate.setText(deadline.toString());
            }
        };

        displayTime = findViewById(R.id.selectTime);
        displayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog dialog = new TimePickerDialog(
                        CreateChecklistActivity.this,
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

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
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

    private void saveTask() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        String textColor = spinnerColor.getSelectedItem().toString();
        String textPriority = spinnerPriority.getSelectedItem().toString();
        String textTaskType = spinnerTaskType.getSelectedItem().toString();

        color = EColor.valueOf(textColor);
        priority = EPriority.valueOf(textPriority);
        taskType = ETaskType.valueOf(textTaskType);

        try{
            checkTitle(title);
        }catch(
                NoTitleException e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();

        data.putExtra(EXTRA_SUBCLASS, subclass);
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_DESCRIPTION, description);
        data.putExtra(EXTRA_STATUS, status);
        data.putExtra(EXTRA_COLOR, color);
        data.putExtra(EXTRA_PRIORITY, priority);
        data.putExtra(EXTRA_TIME, time.toString());
        data.putExtra(EXTRA_TASK_TYPE, taskType);
        data.putExtra(EXTRA_DATE, deadline.toString());

        setResult(RESULT_OK, data);
        finish();
    }

    private boolean checkTitle(String title) throws NoTitleException{
        if (title.trim().isEmpty()) {
            throw new NoTitleException("Title must not be empty.");
        }
        return true;
    }
}
